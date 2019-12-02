package club.plus1.ec_online.viewmodels;

import android.content.Context;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import club.plus1.ec_online.domains.Detail;
import club.plus1.ec_online.models.ServerResponse;

public class InvoiceDetailsViewModel {

    public ObservableInt number;
    public ObservableField<String> date;
    public ObservableDouble sum;
    public ObservableField<String> status;
    public ObservableInt waybill;

    public ObservableField<String> title;
    public ObservableList<Detail> details;

    public ObservableBoolean showInvoiceButton;

    private static InvoiceDetailsViewModel mInstance;    // Ссылка для биндинга с View

    private InvoiceDetailsViewModel() {
        number = new ObservableInt();
        date = new ObservableField<>();
        sum = new ObservableDouble();
        status = new ObservableField<>();
        waybill = new ObservableInt();
        title = new ObservableField<>();
        details = new ObservableArrayList<>();
        showInvoiceButton = new ObservableBoolean();
    }

    // Получение единственного экземпляра класса
    public static InvoiceDetailsViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new InvoiceDetailsViewModel();
        }
        return mInstance;
    }

    public void onInvoice(Context context) {
        ServerResponse.print(context, number.get());
    }
}
