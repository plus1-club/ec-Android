package ru.electric.ec.online.server;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Response;
import ru.electric.ec.online.App;
import ru.electric.ec.online.R;
import ru.electric.ec.online.models.Detail;
import ru.electric.ec.online.models.Invoice;
import ru.electric.ec.online.models.Request;
import ru.electric.ec.online.models.ServerData;
import ru.electric.ec.online.models.Service;
import ru.electric.ec.online.viewadapters.InvoiceDetailsAdapter;
import ru.electric.ec.online.viewadapters.InvoiceTableAdapter;
import ru.electric.ec.online.viewmodels.InfoViewModel;
import ru.electric.ec.online.views.EnterActivity;
import ru.electric.ec.online.views.InvoiceDetailsActivity;
import ru.electric.ec.online.views.InvoiceTableActivity;
import ru.electric.ec.online.views.MenuActivity;

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
        if (Objects.requireNonNull(response.body()).success) {
            Object data = response.body().data;
            App.model.token = (String) ((LinkedTreeMap) data).get("user_token");
            Intent intent = new Intent(context, MenuActivity.class);
            context.startActivity(intent);
        } else {
            InfoViewModel.log(context, false, true,
                Service.getStr(R.string.text_response_error,
                    response.body().error, response.body().message));
        }
    }

    void getExit(Context context, Response<ServerData> response, final int number){
        Intent intent = new Intent(context, EnterActivity.class);
        context.startActivity(intent);
    }

    void getByCode(Context context, Response<ServerData> response, final int number){
        App.model.request.search.clear();
        if (response.body() != null && response.body().error.isEmpty()) {
            List<?> data = (List<?>) response.body().data;
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
                App.model.request.search.add(request);
            }
        }
    }

    void getBasket(Context context, Response<ServerData> response, final int number){
        App.model.request.basket.clear();
        if (response.body() != null && response.body().error.isEmpty()) {
            List<?> data = (List<?>) response.body().data;
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
                App.model.request.basket.add(request);
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
        App.model.invoice.invoices.clear();
        if (response.body() != null && response.body().error.isEmpty()) {
            List<?> data = (List<?>) response.body().data;
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
                App.model.invoice.invoices.add(invoice);
            }
            InvoiceTableAdapter adapter = new InvoiceTableAdapter();
            adapter.setItems(App.model.invoice.invoices);
            ((InvoiceTableActivity)context).binding.list.setAdapter(adapter);
        }
    }

    void invoiceDetails(Context context, Response<ServerData> response, int number){
        if (response.body() != null && response.body().error.isEmpty()) {
            Invoice invoice = null;
            for (Invoice item:App.model.invoice.invoices) {
                if(item.number == number){
                    invoice = item;
                }
            }
            if (invoice == null) return;

            List<?> data = (List<?>) response.body().data;
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
            adapter.setItems(invoice.details);
            ((InvoiceDetailsActivity)context).binding.list.setAdapter(adapter);
        }
    }

    // TODO: Подключаться к серверу и скачивать счет
    void getPrint(Context context, final Response<ServerData> response, final int number) {
        if (response.body() != null && response.body().error.isEmpty()) {
            InfoViewModel.log(context, false, true,
                    Service.getStr(R.string.test_in_develop_download_invoice));
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.ec-electric.ru/order/example.xls"));
            context.startActivity(intent);
        }
    }
}

