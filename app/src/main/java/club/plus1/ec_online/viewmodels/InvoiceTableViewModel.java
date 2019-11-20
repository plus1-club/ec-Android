package club.plus1.ec_online.viewmodels;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import club.plus1.ec_online.domains.Invoice;

public class InvoiceTableViewModel {

    public ObservableList<Invoice> invoices;
    public ObservableField<String> title;

    private static InvoiceTableViewModel mInstance;    // Ссылка для биндинга с View

    private InvoiceTableViewModel() {
        invoices = new ObservableArrayList<>();
        title = new ObservableField<>();
    }

    // Получение единственного экземпляра класса
    public static InvoiceTableViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new InvoiceTableViewModel();
        }
        return mInstance;
    }
}