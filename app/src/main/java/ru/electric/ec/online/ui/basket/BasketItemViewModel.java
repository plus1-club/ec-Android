package ru.electric.ec.online.ui.basket;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import java.util.ArrayList;
import java.util.List;

import ru.electric.ec.online.R;
import ru.electric.ec.online.common.Service;
import ru.electric.ec.online.models.Count;
import ru.electric.ec.online.models.Request;
import ru.electric.ec.online.server.ServerResponse;
import ru.electric.ec.online.ui.info.InfoViewModel;
import ru.electric.ec.online.ui.request.RequestViewModel;

public class BasketItemViewModel {

    private static BasketItemViewModel mInstance;
    public RequestViewModel parent;

    public ObservableInt position;
    public ObservableField<String> product;
    public ObservableInt count;
    public ObservableInt stockCount;
    ObservableInt multiplicity;
    public ObservableField<String> unit;
    public ObservableDouble price;
    public ObservableDouble sum;
    public ObservableField<String> status;
    public ObservableInt color;
    public ObservableBoolean check;
    private ObservableBoolean needUpdate;

    private BasketItemViewModel() {
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
        needUpdate = new ObservableBoolean();
        needUpdate.set(false);
        parent = RequestViewModel.getInstance();
    }

    // Получение единственного экземпляра класса
    public static BasketItemViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new BasketItemViewModel();
        }
        return mInstance;
    }

    public void onTextChanged(Context context, CharSequence s, int start, int before, int charCount) {
        int newCount = s.toString().isEmpty() ? 0 : Integer.parseInt(s.toString());
        if (newCount != count.get() && newCount != 0)
        {
            needUpdate.set(true);
            if (newCount % multiplicity.get() > 0){
                count.set(newCount + (multiplicity.get() - (newCount % multiplicity.get())));
                InfoViewModel.log(context, false, true,
                        Service.getStr(R.string.text_multiplicity, multiplicity.get()));
            } else {
                count.set(newCount);
            }
            sum.set(newCount * price.get());
            Request request = new Request(product.get(), count.get(), stockCount.get(),
                    multiplicity.get(), unit.get(), price.get(), check.get());
            parent.basket.set(position.get(), request);

            if (!needUpdate.get()){
                ((BasketActivity)context).refreshBasket();
            }
            updateStatus();
        }
     }

    public void onFlagClick(View view) {
        this.check.set(((CheckBox) view).isChecked());
        Request request = new Request(product.get(), count.get(), stockCount.get(),
                multiplicity.get(), unit.get(), price.get(), check.get());
        parent.basket.set(position.get(), request);
        updateStatus();
    }

    public void onDeleteClick(View view) {
        final Context context = view.getContext();
        String deletedProduct = product.get();
        List<Request> deleted = new ArrayList<>();
        for (Request item: parent.basket) {
            if (item.product.equals(deletedProduct)){
                deleted.add(item);
            }
        }
        for (Request item: deleted){
            parent.basket.remove(item);
        }
        ServerResponse.putBasket(view.getContext(), parent.basket);
        ServerResponse.getBasket(context);
        ((BasketActivity)context).refreshBasket();
        updateStatus();
    }

    public void onUpdateStatus(Context context){
        ServerResponse.putBasket(context, parent.basket);
        ServerResponse.getBasket(context);
        ((BasketActivity)context).refreshBasket();
        needUpdate.set(false);
        updateStatus();
    }

    void updateStatus() {
        Count countStatus = Service.status(count.get(), stockCount.get(), needUpdate.get());
        status.set(countStatus.status);
        color.set(countStatus.color);

        double total = 0;
        for (Request item : parent.basket) {
            if(item.check) {
                total = total + item.requestCount * item.price;
            }
        }
        parent.total.set(total);
    }
}
