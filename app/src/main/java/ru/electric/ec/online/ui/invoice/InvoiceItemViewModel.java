package ru.electric.ec.online.ui.invoice;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import ru.electric.ec.online.models.Detail;
import ru.electric.ec.online.router.RouterServer;
import ru.electric.ec.online.ui.details.DetailsActivity;

public class InvoiceItemViewModel {

    public InvoiceViewModel parent;
    public ObservableInt position;
    ObservableList<Detail> details;

    public ObservableInt number;
    public ObservableField<String> date;
    public ObservableDouble sum;
    public ObservableField<String> status;
    public ObservableInt waybill;

    public ObservableBoolean showWaybill;
    public ObservableBoolean showInvoiceButton;

    InvoiceItemViewModel() {
        position = new ObservableInt();
        number = new ObservableInt();
        date = new ObservableField<>();
        sum = new ObservableDouble();
        status = new ObservableField<>();
        waybill = new ObservableInt();
        parent = InvoiceViewModel.getInstance();
        details = new ObservableArrayList<>();
        showWaybill = new ObservableBoolean();
        showInvoiceButton = new ObservableBoolean();
    }

    public void onDetails(Context context) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("number", number.get());
        intent.putExtra("date", date.get());
        intent.putExtra("sum", sum.get());
        intent.putExtra("status", status.get());
        intent.putExtra("waybill", waybill.get());
        intent.putExtra("position", position.get());
        context.startActivity(intent);
    }

    public void onInvoice(Context context) {
        RouterServer.print((DetailsActivity) context, number.get());
    }
}
