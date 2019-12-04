package ru.electric.ec.online.viewmodels;

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
import ru.electric.ec.online.domains.Detail;
import ru.electric.ec.online.domains.Invoice;
import ru.electric.ec.online.domains.Request;
import ru.electric.ec.online.models.ServerData;
import ru.electric.ec.online.models.ServerResponse;
import ru.electric.ec.online.models.Service;
import ru.electric.ec.online.viewadapters.InvoiceDetailsAdapter;
import ru.electric.ec.online.viewadapters.InvoiceTableAdapter;
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

    public void getEnter(Context context, Response<ServerData> response){
        if (Objects.requireNonNull(response.body()).isSuccess()) {
            Object data = response.body().getData();
            App.model.token = (String) ((LinkedTreeMap) data).get("user_token");
            Intent intent = new Intent(context, MenuActivity.class);
            context.startActivity(intent);
        } else {
            String message = " (" + response.body().getError() + ")"
                    + response.body().getMessage();
            App.log(context, false, true, message);
        }
    }

    public void getExit(Context context, Response<ServerData> response){
        Intent intent = new Intent(context, EnterActivity.class);
        context.startActivity(intent);
    }

    public void getByCode(Context context, Response<ServerData> response){
        App.model.search.clear();
        if (response.body() != null && response.body().getError().isEmpty()) {
            List<?> data = (List<?>) response.body().getData();
            for (Object element : data) {
                @SuppressWarnings("unchecked")
                Map<String, String> el = (LinkedTreeMap<String, String>) element;
                Request request = new Request(
                        el.get("product"),
                        Service.getInt(el.get("requestCount")),
                        Service.getInt(el.get("stockCount")),
                        true);
                App.model.search.add(request);
            }
        }
    }

    public void getBasket(Context context, Response<ServerData> response){
        App.model.basket.clear();
        if (response.body() != null && response.body().getError().isEmpty()) {
            List<?> data = (List<?>) response.body().getData();
            for (Object element : data) {
                @SuppressWarnings("unchecked")
                Map<String, String> el = (LinkedTreeMap<String, String>) element;
                Request request = new Request(
                        el.get("product"),
                        Service.getInt(el.get("requestCount")),
                        Service.getInt(el.get("stockCount")),
                        Service.getDouble(el.get("price")),
                        true);
                App.model.basket.add(request);
            }
        }
     }

    public void putBasket(Context context, Response<ServerData> response){
        ServerResponse.getBasket(context);
    }

    public void deleteBasket(Context context, Response<ServerData> response){
        ServerResponse.getBasket(context);
    }

    public void order(Context context, Response<ServerData> response){
        ServerResponse.getBasket(context);
    }

    public void invoiceList(Context context, Response<ServerData> response){
        App.model.invoices.clear();
        if (response.body() != null && response.body().getError().isEmpty()) {
            List<?> data = (List<?>) response.body().getData();
            for (Object element : data) {
                @SuppressWarnings("unchecked")
                Map<String, String> el = (LinkedTreeMap<String, String>) element;
                Invoice invoice = new Invoice(
                        Service.getInt(el.get("number")),
                        el.get("date"),
                        el.get("status"),
                        Service.isEqual(el.get("status"),context.getString(R.string.status_shipped))
                            ? Service.getInt(el.get("waybill")) : Service.getDouble((el.get("sum"))));
                App.model.invoices.add(invoice);
            }
            InvoiceTableAdapter adapter = new InvoiceTableAdapter();
            adapter.setItems(App.model.invoices);
            ((InvoiceTableActivity)context).binding.list.setAdapter(adapter);
        }
    }

    public void invoiceDetails(Context context, Response<ServerData> response, int number){
        if (response.body() != null && response.body().getError().isEmpty()) {
            Invoice invoice = null;
            for (Invoice item:App.model.invoices) {
                if(item.number == number){
                    invoice = item;
                }
            }
            if (invoice == null) return;

            List<?> data = (List<?>) response.body().getData();
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

    public void getPrint(Context context, final Response<ServerData> response, final int number) {
        if (response.body() != null && response.body().getError().isEmpty()) {
            Map<String, String> data = (Map<String, String>) response.body().getData();
            final String srcUrl = data.get("file");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(srcUrl));
            context.startActivity(intent);

            /*
            try {
                Map<String, String> data = (LinkedTreeMap<String, String>) response.body().getData();
                final String srcUrl = data.get("file");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(srcUrl));
                context.startActivity(intent);
            } catch (Exception e){
                App.log(context, true, false, e.getMessage());
            }
            */
            //final String destPath = Environment.getExternalStorageDirectory() + "/Download/" + number + ".pdf";
            //FileLoader longTask = FileLoader.getInstance(context, destPath, srcUrl); // Создаем экземпляр
            //longTask.execute();
        }
    }
}

