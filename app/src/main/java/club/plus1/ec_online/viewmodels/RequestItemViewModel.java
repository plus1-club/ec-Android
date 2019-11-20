package club.plus1.ec_online.viewmodels;

import android.view.View;
import android.widget.CheckBox;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import club.plus1.ec_online.App;
import club.plus1.ec_online.R;
import club.plus1.ec_online.Server;
import club.plus1.ec_online.domains.Request;
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

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int newCount = s.toString().isEmpty() ? 0 : Integer.parseInt(s.toString());
        this.count.set(newCount);
        this.sum.set(newCount * price.get());
        updateStatus();
    }

    public void onFlagClick(View view) {
        this.check.set(((CheckBox) view).isChecked());
        updateStatus();
    }

    public void onDeleteClick(View view) {
        Request request = new Request(product.get(), count.get(), stockCount.get(), price.get(), check.get());
        List<Request> deleted = new ArrayList<>();
        deleted.add(request);
        Server.deleteBasket(view.getContext(), deleted);
        RequestsBasketAdapter basketAdapter = (RequestsBasketAdapter) parent.adapter.get();
        Objects.requireNonNull(basketAdapter).notifyDataSetChanged();
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
        Request request = new Request(product.get(), count.get(), stockCount.get(), price.get(), check.get());
        parent.requests.set(position.get(), request);
        double total = 0;
        for (Request item : parent.requests) {
            if(item.check) {
                total = total + item.sum;
            }
        }
        parent.total.set(total);
    }
}
