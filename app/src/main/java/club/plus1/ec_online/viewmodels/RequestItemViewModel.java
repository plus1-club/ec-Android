package club.plus1.ec_online.viewmodels;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import java.util.Objects;

import club.plus1.ec_online.App;
import club.plus1.ec_online.R;
import club.plus1.ec_online.domains.Request;
import club.plus1.ec_online.models.ServerResponse;
import club.plus1.ec_online.viewadapters.RequestsBasketAdapter;

public class RequestItemViewModel {

    public RequestViewModel parent;

    public ObservableInt position;
    public ObservableField<String> product;
    public ObservableInt count;
    public ObservableInt stockCount;
    public ObservableDouble price;
    public ObservableDouble sum;
    public ObservableField<String> status;
    public ObservableInt color;
    public ObservableBoolean check;

    public RequestItemViewModel() {
        position = new ObservableInt();
        product = new ObservableField<>();
        count = new ObservableInt();
        stockCount = new ObservableInt();
        price = new ObservableDouble();
        sum = new ObservableDouble();
        status = new ObservableField<>();
        color = new ObservableInt();
        check = new ObservableBoolean();
        parent = RequestViewModel.getInstance();
    }

    public void onTextChanged(Context context, CharSequence s, int start, int before, int count) {
        int newCount = s.toString().isEmpty() ? 0 : Integer.parseInt(s.toString());
        this.count.set(newCount);
        this.sum.set(newCount * price.get());
        Request request = new Request(product.get(), newCount, stockCount.get(), price.get(), check.get());
        parent.requests.set(position.get(), request);
        updateStatus();
     }

    public void onFlagClick(View view) {
        this.check.set(((CheckBox) view).isChecked());
        Request request = new Request(product.get(), count.get(), stockCount.get(), price.get(), check.get());
        parent.requests.set(position.get(), request);
        updateStatus();
    }

    public void onDeleteClick(View view) {
        String deletedProduct = product.get();
        for (Request item: parent.requests) {
            if (item.product.equals(deletedProduct)){
                parent.requests.remove(item);
            }
        }
        ServerResponse.putBasket(view.getContext(), parent.requests);
        RequestsBasketAdapter basketAdapter = (RequestsBasketAdapter) parent.adapter.get();
        Objects.requireNonNull(basketAdapter).notifyDataSetChanged();
        ServerResponse.getBasket(view.getContext());
        updateStatus();
    }

    public void updateStatus() {
        if (stockCount.get() == 0) {
            status.set(App.getContext().getString(R.string.text_status_red));
            color.set(R.color.red);
        } else if (stockCount.get() >= count.get()) {
            status.set(App.getContext().getString(R.string.text_status_green));
            color.set(R.color.green);
        } else {
            status.set(App.getContext().getString(R.string.text_status_yellow, stockCount.get(), count.get()));
            color.set(R.color.yellow);
        }
        double total = 0;
        for (Request item : parent.requests) {
            if(item.check) {
                total = total + item.requestCount * item.price;
            }
        }
        parent.total.set(total);
    }
}
