package club.plus1.ec_online.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import club.plus1.ec_online.view.InvoiceDetailsActivity;

public class InvoiceItemViewModel {

    public InvoiceViewModel parent;

    public ObservableInt position;
    public ObservableInt number;
    public ObservableField<String> date;
    public ObservableDouble sum;
    public ObservableField<String> status;
    public ObservableInt waybill;

    public InvoiceItemViewModel() {
        position = new ObservableInt();
        number = new ObservableInt();
        date = new ObservableField<>();
        sum = new ObservableDouble();
        status = new ObservableField<>();
        waybill = new ObservableInt();
        parent = InvoiceViewModel.getInstance();
    }

    public void onDetails(Context context) {
        Intent intent = new Intent(context, InvoiceDetailsActivity.class);
        context.startActivity(intent);
    }

    // TODO: Отправлять запрос к серверу на скачивание документа
    public void onInvoice(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ec-electric.ru/order/example.xls"));
        context.startActivity(intent);
    }

}
