package ru.electric.ec.online.viewmodels;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import java.util.ArrayList;
import java.util.List;

import ru.electric.ec.online.App;
import ru.electric.ec.online.R;
import ru.electric.ec.online.domains.Count;
import ru.electric.ec.online.domains.Request;
import ru.electric.ec.online.models.ServerResponse;
import ru.electric.ec.online.models.Service;
import ru.electric.ec.online.views.BasketActivity;

public class BasketItemViewModel {

    public RequestViewModel parent;

    public ObservableInt position;
    public ObservableField<String> product;
    public ObservableInt count;
    public ObservableInt stockCount;
    public ObservableInt multiplicity;
    public ObservableField<String> unit;
    public ObservableDouble price;
    public ObservableDouble sum;
    public ObservableField<String> status;
    public ObservableInt color;
    public ObservableBoolean check;

    public BasketItemViewModel() {
        position = new ObservableInt();
        product = new ObservableField<>();
        count = new ObservableInt();
        stockCount = new ObservableInt();
        multiplicity = new ObservableInt();
        unit = new ObservableField<>();
        price = new ObservableDouble();
        sum = new ObservableDouble();
        status = new ObservableField<>();
        color = new ObservableInt();
        check = new ObservableBoolean();
        parent = RequestViewModel.getInstance();
    }

    public void onTextChanged(Context context, CharSequence s, int start, int before, int count) {
        int newCount = s.toString().isEmpty() ? 0 : Integer.parseInt(s.toString());
        if (newCount != this.count.get() && newCount != 0)
        {
            if (newCount % multiplicity.get() > 0){
                this.count.set(newCount + (multiplicity.get() - (newCount % multiplicity.get())));
                Toast.makeText(context,
                        context.getString(R.string.text_multiplicity, multiplicity.get()),
                        Toast.LENGTH_LONG).show();

            } else {
                this.count.set(newCount);
            }
            this.sum.set(newCount * price.get());
            Request request = new Request(product.get(), this.count.get(), stockCount.get(),
                    multiplicity.get(), unit.get(), price.get(), check.get());
            parent.requests.set(position.get(), request);
            App.model.basket.clear();
            App.model.basket.addAll(parent.requests);

            ServerResponse.putBasket(context, parent.requests);
            ((BasketActivity)context).refreshBasket();
            updateStatus();
        }
     }

    public void onFlagClick(View view) {
        this.check.set(((CheckBox) view).isChecked());
        Request request = new Request(product.get(), count.get(), stockCount.get(),
                multiplicity.get(), unit.get(), price.get(), check.get());
        parent.requests.set(position.get(), request);
        updateStatus();
    }

    public void onDeleteClick(View view) {
        final Context context = view.getContext();
        String deletedProduct = product.get();
        List<Request> deleted = new ArrayList<>();
        for (Request item: parent.requests) {
            if (item.product.equals(deletedProduct)){
                deleted.add(item);
            }
        }
        for (Request item: deleted){
            parent.requests.remove(item);
        }
        ServerResponse.putBasket(view.getContext(), parent.requests);
        ((BasketActivity)context).refreshBasket();
        updateStatus();
    }

    public void updateStatus() {
        Count countStatus = Service.status(count.get(), stockCount.get());
        status.set(countStatus.status);
        color.set(countStatus.color);

        double total = 0;
        for (Request item : parent.requests) {
            if(item.check) {
                total = total + item.requestCount * item.price;
            }
        }
        parent.total.set(total);
    }
}
