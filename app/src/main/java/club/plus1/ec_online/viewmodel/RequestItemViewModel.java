package club.plus1.ec_online.viewmodel;

import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import club.plus1.ec_online.R;
import club.plus1.ec_online.model.App;
import club.plus1.ec_online.model.Request;

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

    public RequestItemViewModel() {
        position = new ObservableInt();
        product = new ObservableField<>();
        count = new ObservableInt();
        stockCount = new ObservableInt();
        price = new ObservableDouble();
        sum = new ObservableDouble();
        status = new ObservableField<>();
        color = new ObservableInt();
        parent = RequestViewModel.getInstance();
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int newCount = s.toString().isEmpty() ? 0 : Integer.parseInt(s.toString());
        this.count.set(newCount);
        this.sum.set(newCount * price.get());
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
        Request request = new Request(product.get(), count.get(), stockCount.get(), price.get());
        parent.requests.set(position.get(), request);
        double total = 0;
        for (Request item : parent.requests) {
            total = total + item.sum;
        }
        parent.total.set(total);
    }
}
