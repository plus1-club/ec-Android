package club.plus1.ec_online.viewmodel;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import club.plus1.ec_online.model.Detail;

public class InvoiceDetailsViewModel {

    public ObservableInt number;
    public ObservableField<String> date;
    public ObservableDouble sum;
    public ObservableField<String> status;
    public ObservableInt waybill;

    public ObservableField<String> title;
    public ObservableList<Detail> details;

    private static InvoiceDetailsViewModel mInstance;    // Ссылка для биндинга с View

    private InvoiceDetailsViewModel() {
        number = new ObservableInt();
        date = new ObservableField<>();
        sum = new ObservableDouble();
        status = new ObservableField<>();
        waybill = new ObservableInt();
        title = new ObservableField<>();
        details = new ObservableArrayList<>();
    }

    // Получение единственного экземпляра класса
    public static InvoiceDetailsViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new InvoiceDetailsViewModel();
        }
        return mInstance;
    }
}
