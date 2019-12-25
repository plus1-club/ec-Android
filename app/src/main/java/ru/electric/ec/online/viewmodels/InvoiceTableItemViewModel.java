package ru.electric.ec.online.viewmodels;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import ru.electric.ec.online.models.Detail;
import ru.electric.ec.online.server.ServerResponse;
import ru.electric.ec.online.views.InvoiceDetailsActivity;

public class InvoiceTableItemViewModel {

    public InvoiceTableViewModel parent;
    public ObservableInt position;
    public ObservableList<Detail> details;

    public ObservableInt number;
    public ObservableField<String> date;
    public ObservableDouble sum;
    public ObservableField<String> status;
    public ObservableInt waybill;

    public ObservableBoolean showWaybill;
    public ObservableBoolean showInvoiceButton;

    public InvoiceTableItemViewModel() {
        position = new ObservableInt();
        number = new ObservableInt();
        date = new ObservableField<>();
        sum = new ObservableDouble();
        status = new ObservableField<>();
        waybill = new ObservableInt();
        parent = InvoiceTableViewModel.getInstance();
        details = new ObservableArrayList<>();
        showWaybill = new ObservableBoolean();
        showInvoiceButton = new ObservableBoolean();
    }

    public void onDetails(Context context) {
        Intent intent = new Intent(context, InvoiceDetailsActivity.class);
        intent.putExtra("number", number.get());
        intent.putExtra("date", date.get());
        intent.putExtra("sum", sum.get());
        intent.putExtra("status", status.get());
        intent.putExtra("waybill", waybill.get());
        intent.putExtra("position", position.get());
        context.startActivity(intent);
    }

    public void onInvoice(Context context) {
        ServerResponse.print(context, number.get());
    }
}
