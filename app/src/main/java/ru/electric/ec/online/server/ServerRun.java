package ru.electric.ec.online.server;

import android.content.Context;
import android.content.Intent;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Response;
import ru.electric.ec.online.R;
import ru.electric.ec.online.common.App;
import ru.electric.ec.online.common.Service;
import ru.electric.ec.online.models.Detail;
import ru.electric.ec.online.models.Info;
import ru.electric.ec.online.models.Invoice;
import ru.electric.ec.online.models.Request;
import ru.electric.ec.online.router.RouterData;
import ru.electric.ec.online.router.RouterView;
import ru.electric.ec.online.ui.bill.BillActivity;
import ru.electric.ec.online.ui.details.DetailsViewAdapter;
import ru.electric.ec.online.ui.info.InfoActivity;
import ru.electric.ec.online.ui.invoice.InvoiceViewAdapter;

public class ServerRun {

    private static ServerRun mInstance;

    private ServerRun() {
    }

    // Получение единственного экземпляра класса
    public static ServerRun getInstance() {
        if (mInstance == null) {
            mInstance = new ServerRun();
        }
        return mInstance;
    }

    public void getEnter(Context context, Response<ServerData> response, final int number){
        ServerData body = Objects.requireNonNull(response.body());
        if (isSuccess(response)) {
            RouterData.setToken(body);
            RouterView.openMenu(context);
        } else {
            String message = Service.getStr(R.string.text_response_error, body.error, body.message);
            Info info = new Info(false, true, message, "EnterActivity");
            RouterData.saveInfo(info);
            RouterView.openInfo(context, info);
        }
    }

    void getExit(Context context, Response<ServerData> response, final int number){
        RouterView.openEnter(context);
    }

    void getByCode(Context context, Response<ServerData> response, final int number){
        ServerData body = Objects.requireNonNull(response.body());
        App.getModel().request.search.clear();
        if (isSuccess(response)) {
            List<?> data = (List<?>) body.data;
            for (Object element : data) {
                @SuppressWarnings("unchecked")
                Map<String, String> el = (LinkedTreeMap<String, String>) element;
                Request request = new Request(
                        el.get("product"),
                        Service.getInt(el.get("requestCount")),
                        Service.getInt(el.get("stockCount")),
                        Service.getInt(el.get("multiplicity")),
                        el.get("unit"),
                        false);
                if (request.requestCount % request.multiplicity > 0){
                    request.requestCount += request.multiplicity - (request.requestCount % request.multiplicity);
                }
                App.getModel().request.search.add(request);
            }
        } else {
            String message = Service.getStr(R.string.text_response_error, body.error, body.message);
            Info info = new Info(false, true, message, "RequestActivity");
            RouterData.saveInfo(info);
            RouterView.openInfo(context, info);
        }
    }

    void getFromExcel(Context context, Response<ServerData> response, final int number){
        ServerData body = Objects.requireNonNull(response.body());
        App.getModel().request.search.clear();
        if (isSuccess(response)) {
            List<?> data = (List<?>) body.data;
            for (Object element : data) {
                @SuppressWarnings("unchecked")
                Map<String, String> el = (LinkedTreeMap<String, String>) element;
                Request request = new Request(
                        el.get("product"),
                        Service.getInt(el.get("requestCount")),
                        Service.getInt(el.get("stockCount")),
                        Service.getInt(el.get("multiplicity")),
                        el.get("unit"),
                        false);
                if (request.requestCount % request.multiplicity > 0){
                    request.requestCount += request.multiplicity - (request.requestCount % request.multiplicity);
                }
                App.getModel().request.search.add(request);
            }
        } else {
            String message = Service.getStr(R.string.text_response_error, body.error, body.message);
            Info info = new Info(false, true, message, "RequestActivity");
            RouterData.saveInfo(info);
            RouterView.openInfo(context, info);
        }
    }

    void getBasket(Context context, Response<ServerData> response, final int number){
        ServerData body = Objects.requireNonNull(response.body());
        App.getModel().request.basket.clear();
        if (isSuccess(response)) {
            List<?> data = (List<?>) body.data;
            for (Object element : data) {
                @SuppressWarnings("unchecked")
                Map<String, String> el = (LinkedTreeMap<String, String>) element;
                Request request = new Request(
                        el.get("product"),
                        Service.getInt(el.get("requestCount")),
                        Service.getInt(el.get("stockCount")),
                        Service.getInt(el.get("multiplicity")),
                        el.get("unit"),
                        Service.getDouble(el.get("price")),
                        true);
                if (request.requestCount % request.multiplicity > 0){
                    request.requestCount += request.multiplicity - (request.requestCount % request.multiplicity);
                }
                App.getModel().request.basket.add(request);
            }
        }
    }

    void postBasket(Context context, Response<ServerData> response, final int number){
        ServerResponse.getBasket(context);
    }

    void putBasket(Context context, Response<ServerData> response, final int number){
        ServerResponse.getBasket(context);
    }

    void deleteBasket(Context context, Response<ServerData> response, final int number){
        ServerResponse.getBasket(context);
    }

    void order(Context context, Response<ServerData> response, final int number){
        ServerData body = Objects.requireNonNull(response.body());
        if (isSuccess(response)) {
            LinkedTreeMap data = (LinkedTreeMap) body.data;
            int orderNumber = Service.getInt((String)data.get("number"));
            App.getModel().request.orderNumber.set(orderNumber);
            Intent intent = new Intent(context, InfoActivity.class);
            intent.putExtra("title", context.getString(R.string.text_basket));
            intent.putExtra("info", context.getString(R.string.text_order_processed, orderNumber));
            intent.putExtra("activityName", "BasketActivity");
            context.startActivity(intent);
        } else {
            String message = Service.getStr(R.string.text_response_error, body.error, body.message);
            Info info = new Info(false, true, message, "BasketActivity");
            RouterData.saveInfo(info);
            RouterView.openInfo(context, info);
        }
        ServerResponse.getBasket(context);
    }

    void invoiceList(Context context, Response<ServerData> response, final int number){
        ServerData body = Objects.requireNonNull(response.body());
        App.getModel().invoice.invoices.clear();
        if (isSuccess(response)) {
            List<?> data = (List<?>) body.data;
            for (Object element : data) {
                @SuppressWarnings("unchecked")
                Map<String, String> el = (LinkedTreeMap<String, String>) element;
                Invoice invoice;
                if (Service.isEqual(el.get("status"),context.getString(R.string.status_shipped))) {
                    invoice = new Invoice(
                            Service.getInt(el.get("number")),
                            el.get("date"),
                            el.get("status"),
                            Service.getInt(el.get("waybill")));

                } else {
                    invoice = new Invoice(
                            Service.getInt(el.get("number")),
                            el.get("date"),
                            Service.getDouble(el.get("sum")),
                            el.get("status"));
                }
                App.getModel().invoice.invoices.add(invoice);
            }
            InvoiceViewAdapter adapter = new InvoiceViewAdapter();
            adapter.updateAdapter(App.getModel().invoice, context);
        } else {
            String message = Service.getStr(R.string.text_response_error, body.error, body.message);
            Info info = new Info(false, true, message, "InvoiceActivity");
            RouterData.saveInfo(info);
            RouterView.openInfo(context, info);
        }
    }

    void invoiceDetails(Context context, Response<ServerData> response, int number){
        ServerData body = Objects.requireNonNull(response.body());
        if (isSuccess(response)) {
            Invoice invoice = null;
            for (Invoice item: App.getModel().invoice.invoices) {
                if(item.number == number){
                    invoice = item;
                }
            }
            if (invoice == null) return;

            List<?> data = (List<?>) body.data;
            invoice.details.clear();
            for (Object element : data) {
                @SuppressWarnings("unchecked")
                Map<String, String> el = (LinkedTreeMap<String, String>) element;
                Detail detail = new Detail(
                        el.get("product"),
                        Service.getInt(el.get("count")),
                        el.get("unit"),
                        Service.getDouble(el.get("price")),
                        Service.getDouble(el.get("sum")),
                        el.get("available"),
                        el.get("delivery"));
                invoice.details.add(detail);
            }
            DetailsViewAdapter adapter = new DetailsViewAdapter();
            adapter.updateAdapter(invoice, context);
        } else {
            String message = Service.getStr(R.string.text_response_error, body.error, body.message);
            Info info = new Info(false, true, message, "DetailActivity");
            RouterData.saveInfo(info);
            RouterView.openInfo(context, info);
        }
    }

    // TODO: Подключаться к серверу и скачивать счет
    void getPrint(Context context, final Response<ServerData> response, final int number) {
        ServerData body = Objects.requireNonNull(response.body());
        if (isSuccess(response)) {
            LinkedTreeMap data = (LinkedTreeMap) body.data;
            String link = (String) data.get("file");
            Intent intent = new Intent(context, BillActivity.class);
            intent.putExtra("title", Service.getStr(R.string.text_invoice_short, number));
            intent.putExtra("number", number);
            intent.putExtra("link", link);
            context.startActivity(intent);
        } else {
            String message = Service.getStr(R.string.text_response_error, body.error, body.message);
            Info info = new Info(false, true, message, "DetailActivity");
            RouterData.saveInfo(info);
            RouterView.openInfo(context, info);
        }
    }

    boolean isSuccess(final Response<ServerData> response){
        if (response.body() != null)
        {
            return response.body().success && response.body().error.isEmpty();
        } else {
            return false;
        }
    }
}

