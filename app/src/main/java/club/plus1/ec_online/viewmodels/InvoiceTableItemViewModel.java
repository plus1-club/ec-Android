package club.plus1.ec_online.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import java.util.Objects;

import club.plus1.ec_online.R;
import club.plus1.ec_online.domains.Detail;
import club.plus1.ec_online.views.InvoiceDetailsActivity;

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

        if (Objects.equals(status.get(), context.getString(R.string.status_unconfirmed))) {
            intent.putExtra("title", context.getString(R.string.text_item_unconfirmed));
        } else if (Objects.equals(status.get(), context.getString(R.string.status_reserved))) {
            intent.putExtra("title", context.getString(R.string.text_item_reserved));
        } else if (Objects.equals(status.get(), context.getString(R.string.status_ordered))) {
            intent.putExtra("title", context.getString(R.string.text_item_ordered));
        } else if (Objects.equals(status.get(), context.getString(R.string.status_canceled))) {
            intent.putExtra("title", context.getString(R.string.text_item_canceled));
        } else if (Objects.equals(status.get(), context.getString(R.string.status_overdie))) {
            intent.putExtra("title", context.getString(R.string.text_item_overdie));
        } else if (Objects.equals(status.get(), context.getString(R.string.status_shipped))) {
            intent.putExtra("title", context.getString(R.string.text_item_shipped));
        } else {
            intent.putExtra("title", context.getString(R.string.text_item_invoice));
        }

        context.startActivity(intent);
    }

    // TODO: Отправлять запрос к серверу на скачивание документа
    public void onInvoice(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ec-electric.ru/order/example.xls"));
        context.startActivity(intent);
    }

}
