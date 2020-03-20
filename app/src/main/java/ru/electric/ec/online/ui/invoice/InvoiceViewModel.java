package ru.electric.ec.online.ui.invoice;

import android.content.Context;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.common.App;
import ru.electric.ec.online.common.Service;
import ru.electric.ec.online.databinding.InvoiceBinding;
import ru.electric.ec.online.models.Invoice;
import ru.electric.ec.online.server.ServerData;
import ru.electric.ec.online.server.ServerRouter;
import ru.electric.ec.online.ui.ViewRouter;

public class InvoiceViewModel {

    public ObservableList<Invoice> invoices;
    public ObservableField<String> title;
    public ObservableField<InvoiceViewAdapter> adapter;
    public ObservableField<InvoiceBinding> binding;

    private static InvoiceViewModel mInstance;    // Ссылка для биндинга с View

    private InvoiceViewModel() {
        invoices = new ObservableArrayList<>();
        title = new ObservableField<>();
        adapter = new ObservableField<>();
        binding = new ObservableField<>();
    }

    // Получение единственного экземпляра класса
    public static InvoiceViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new InvoiceViewModel();
        }
        return mInstance;
    }

    public void invoiceOk(Context context, ServerData body) {
        App.getModel().invoice.invoices.clear();
        if (ServerRouter.isSuccess(body)) {
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
            ViewRouter.onUnsuccessful(context, body);
        }
        adapter.set(new InvoiceViewAdapter());
        Objects.requireNonNull(adapter.get()).setItems(invoices);
        Objects.requireNonNull(binding.get()).list.setAdapter(adapter.get());
        Objects.requireNonNull(binding.get()).swiperefresh.setRefreshing(false);
    }

    public void invoiceError(Context context, Throwable throwable) {
        ViewRouter.onError(context, throwable);
    }

}
