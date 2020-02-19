package ru.electric.ec.online.ui.invoice;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import ru.electric.ec.online.models.Invoice;

public class InvoiceViewModel {

    public ObservableList<Invoice> invoices;
    public ObservableField<String> title;

    private static InvoiceViewModel mInstance;    // Ссылка для биндинга с View

    private InvoiceViewModel() {
        invoices = new ObservableArrayList<>();
        title = new ObservableField<>();
    }

    // Получение единственного экземпляра класса
    public static InvoiceViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new InvoiceViewModel();
        }
        return mInstance;
    }
}
