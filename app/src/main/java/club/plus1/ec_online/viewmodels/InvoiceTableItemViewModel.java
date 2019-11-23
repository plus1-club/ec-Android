package club.plus1.ec_online.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import club.plus1.ec_online.R;
import club.plus1.ec_online.domains.Detail;

public class InvoiceTableItemViewModel {

    public InvoiceTableViewModel parent;
    public ObservableInt position;
    public ObservableList<Detail> details;

    public ObservableInt number;
    public ObservableField<String> date;
    public ObservableDouble sum;
    public ObservableField<String> status;
    public ObservableInt waybill;

    public InvoiceTableItemViewModel() {
        position = new ObservableInt();
        number = new ObservableInt();
        date = new ObservableField<>();
        sum = new ObservableDouble();
        status = new ObservableField<>();
        waybill = new ObservableInt();
        parent = InvoiceTableViewModel.getInstance();
        details = new ObservableArrayList<>();
    }

    public void onDetails(Context context) {

        /*
        intent.putExtra("number", number.get());
        intent.putExtra("date", date.get());
        intent.putExtra("sum", sum.get());
        intent.putExtra("status", status.get());
        intent.putExtra("waybill", waybill.get());
        intent.putExtra("position", position.get());
        */

        int title;
        if (context.getString(R.string.status_unconfirmed).equals(status.get())) {
            title = R.string.text_item_unconfirmed;
        } else if (context.getString(R.string.status_reserved).equals(status.get())) {
            title = R.string.text_item_reserved;
        } else if (context.getString(R.string.status_ordered).equals(status.get())) {
            title = R.string.text_item_ordered;
        } else if (context.getString(R.string.status_canceled).equals(status.get())) {
            title = R.string.text_item_canceled;
        } else if (context.getString(R.string.status_overdie).equals(status.get())) {
            title = R.string.text_item_overdie;
        } else if (context.getString(R.string.status_shipped).equals(status.get())) {
            title = R.string.text_item_shipped;
        } else {
            title = R.string.text_item_invoice;
        }
        MenuViewModel.getInstance().ShowFragment(context, title, "InvoiceDetails");

    }

    // TODO: Отправлять запрос к серверу на скачивание документа
    public void onInvoice(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ec-electric.ru/order/example.xls"));
        context.startActivity(intent);
    }

}
