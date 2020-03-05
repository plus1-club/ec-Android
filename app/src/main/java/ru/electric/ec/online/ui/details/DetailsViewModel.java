package ru.electric.ec.online.ui.details;

import android.content.Context;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import ru.electric.ec.online.models.Detail;
import ru.electric.ec.online.router.RouterServer;

public class DetailsViewModel {

    public ObservableInt number;
    public ObservableField<String> date;
    public ObservableDouble sum;
    public ObservableField<String> status;
    public ObservableInt waybill;

    public ObservableField<String> title;
    ObservableList<Detail> details;

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
    }

    // Получение единственного экземпляра класса
    public static DetailsViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new DetailsViewModel();
        }
        return mInstance;
    }

    public void onInvoice(Context context) {
        RouterServer.print((DetailsActivity) context, number.get());
    }
}
