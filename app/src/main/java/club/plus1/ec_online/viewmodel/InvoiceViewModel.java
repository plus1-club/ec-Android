package club.plus1.ec_online.viewmodel;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import club.plus1.ec_online.model.Invoice;

public class InvoiceViewModel {

    public ObservableList<Invoice> invoices;

    private static InvoiceViewModel mInstance;    // Ссылка для биндинга с View

    private InvoiceViewModel() {
        invoices = new ObservableArrayList<>();
    }

    // Получение единственного экземпляра класса
    public static InvoiceViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new InvoiceViewModel();
        }
        return mInstance;
    }


}
