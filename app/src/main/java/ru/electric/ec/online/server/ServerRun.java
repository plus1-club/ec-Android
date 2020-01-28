package ru.electric.ec.online.server;

import android.content.Context;
import android.content.Intent;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Response;
import ru.electric.ec.online.App;
import ru.electric.ec.online.R;
import ru.electric.ec.online.Service;
import ru.electric.ec.online.models.Detail;
import ru.electric.ec.online.models.Invoice;
import ru.electric.ec.online.models.Request;
import ru.electric.ec.online.models.ServerData;
import ru.electric.ec.online.ui.enter.EnterActivity;
import ru.electric.ec.online.ui.info.InfoViewModel;
import ru.electric.ec.online.ui.invoice.InvoiceDetailsAdapter;
import ru.electric.ec.online.ui.invoice.InvoiceTableAdapter;
import ru.electric.ec.online.ui.menu.MenuActivity;
import ru.electric.ec.online.ui.pdf.PdfActivity;

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

    void getEnter(Context context, Response<ServerData> response, final int number){
        if (isSuccess(response)) {
            Object data = Objects.requireNonNull(response.body()).data;
            App.getModel().token = (String) ((LinkedTreeMap) data).get("user_token");
            Intent intent = new Intent(context, MenuActivity.class);
            context.startActivity(intent);
        } else {
            InfoViewModel.log(context, false, true,
                Service.getStr(R.string.text_response_error,
                    Objects.requireNonNull(response.body()).error, response.body().message));
        }
    }

    void getExit(Context context, Response<ServerData> response, final int number){
        Intent intent = new Intent(context, EnterActivity.class);
        context.startActivity(intent);
    }

    void getByCode(Context context, Response<ServerData> response, final int number){
        App.getModel().request.search.clear();
        if (isSuccess(response)) {
            List<?> data = (List<?>) Objects.requireNonNull(response.body()).data;
            for (Object element : data) {
                @SuppressWarnings("unchecked")
                Map<String, String> el = (LinkedTreeMap<String, String>) element;
                Request request = new Request(
                        el.get("product"),
                        Service.getInt(el.get("requestCount")),
                        Service.getInt(el.get("stockCount")),
                        Service.getInt(el.get("multiplicity")),
                        el.get("unit"),
                        true);
                if (request.requestCount % request.multiplicity > 0){
                    request.requestCount += request.multiplicity - (request.requestCount % request.multiplicity);
                }
                App.getModel().request.search.add(request);
            }
        }
    }

    void getBasket(Context context, Response<ServerData> response, final int number){
        App.getModel().request.basket.clear();
        if (isSuccess(response)) {
            List<?> data = (List<?>) Objects.requireNonNull(response.body()).data;
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
        ServerResponse.getBasket(context);
    }

    void invoiceList(Context context, Response<ServerData> response, final int number){
        App.getModel().invoice.invoices.clear();
        if (isSuccess(response)) {
            List<?> data = (List<?>) Objects.requireNonNull(response.body()).data;
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
            InvoiceTableAdapter adapter = new InvoiceTableAdapter();
            adapter.updateAdapter(App.getModel().invoice, context);
        }
    }

    void invoiceDetails(Context context, Response<ServerData> response, int number){
        if (isSuccess(response)) {
            Invoice invoice = null;
            for (Invoice item: App.getModel().invoice.invoices) {
                if(item.number == number){
                    invoice = item;
                }
            }
            if (invoice == null) return;

            List<?> data = (List<?>) Objects.requireNonNull(response.body()).data;
            invoice.details.clear();
            for (Object element : data) {
                @SuppressWarnings("unchecked")
                Map<String, String> el = (LinkedTreeMap<String, String>) element;
                Detail detail = new Detail(
                        el.get("product"),
                        Service.getInt(el.get("count")),
                        Service.getDouble(el.get("price")),
                        Service.getDouble(el.get("sum")),
                        el.get("available"),
                        el.get("delivery"));
                invoice.details.add(detail);
            }
            InvoiceDetailsAdapter adapter = new InvoiceDetailsAdapter();
            adapter.updateAdapter(invoice, context);
        }
    }

    // TODO: Подключаться к серверу и скачивать счет
    void getPrint(Context context, final Response<ServerData> response, final int number) {
        if (isSuccess(response)) {
            LinkedTreeMap data = (LinkedTreeMap) Objects.requireNonNull(response.body()).data;
            String link = (String) data.get("file");
            Intent intent = new Intent(context, PdfActivity.class);
            intent.putExtra("title", Service.getStr(R.string.text_invoice_short, number));
            intent.putExtra("number", number);
            intent.putExtra("link", link);
            context.startActivity(intent);
        }
    }

    private boolean isSuccess(final Response<ServerData> response){
        return (response.body() != null && response.body().success && response.body().error.isEmpty());
    }
}

