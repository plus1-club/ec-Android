package ru.electric.ec.online.ui.details;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import ru.electric.ec.online.common.App;
import ru.electric.ec.online.common.Service;
import ru.electric.ec.online.databinding.DetailsBinding;
import ru.electric.ec.online.models.Detail;
import ru.electric.ec.online.models.Invoice;
import ru.electric.ec.online.server.ServerData;
import ru.electric.ec.online.server.ServerRouter;
import ru.electric.ec.online.ui.ViewRouter;
import ru.electric.ec.online.ui.bill.BillViewModel;

public class DetailsViewModel {

    public ObservableInt number;
    public ObservableField<String> date;
    public ObservableDouble sum;
    public ObservableField<String> status;
    public ObservableInt waybill;

    public ObservableField<String> title;
    public ObservableList<Detail> details;
    public ObservableField<DetailsViewAdapter> adapter;
    public ObservableField<DetailsBinding> binding;

    public ObservableBoolean showInvoiceButton;

    private static DetailsViewModel mInstance;    // Ссылка для биндинга с View

    private DetailsViewModel() {
        number = new ObservableInt();
        date = new ObservableField<>();
        sum = new ObservableDouble();
        status = new ObservableField<>();
        waybill = new ObservableInt();
        title = new ObservableField<>();
        details = new ObservableArrayList<>();
        showInvoiceButton = new ObservableBoolean();
        adapter = new ObservableField<>();
        binding = new ObservableField<>();
    }

    // Получение единственного экземпляра класса
    public static DetailsViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new DetailsViewModel();
        }
        return mInstance;
    }

    public void onInvoice(Context context) {
        final int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        } else {
            Objects.requireNonNull(binding.get()).swiperefresh.setRefreshing(true);
            ServerRouter.print(context, BillViewModel.getInstance(), number.get());
        }
    }

    public void detailOk(Context context, ServerData body, int number) {
        if (ServerRouter.isSuccess(body)) {
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
            adapter.set(new DetailsViewAdapter());
            Objects.requireNonNull(adapter.get()).updateAdapter(invoice, context);
        } else {
            ViewRouter.onUnsuccessful(context, body);
        }
        Objects.requireNonNull(binding.get()).swiperefresh.setRefreshing(false);
    }

    public void detailError(Context context, Throwable throwable) {
        ViewRouter.onError(context, throwable);
    }
}
