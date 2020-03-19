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
import java.util.Objects;

import ru.electric.ec.online.common.Service;
import ru.electric.ec.online.models.Request;
import ru.electric.ec.online.router.RouterServer;
import ru.electric.ec.online.ui.request.RequestViewModel;

public class BasketItemViewModel {

    public RequestViewModel parent;

    public ObservableInt position;
    public ObservableField<String> product;
    public ObservableField<String> requestProduct;
    public ObservableInt count;
    public ObservableInt stockCount;
    ObservableInt multiplicity;
    public ObservableField<String> unit;
    public ObservableDouble price;
    public ObservableDouble sum;
    public ObservableBoolean check;
    private ObservableBoolean needUpdate;
    public ObservableField<String> status;
    public ObservableField<String> colorName;
    public int color;

    BasketItemViewModel() {
        position = new ObservableInt();
        product = new ObservableField<>();
        requestProduct = new ObservableField<>();
        count = new ObservableInt();
        stockCount = new ObservableInt();
        multiplicity = new ObservableInt();
        unit = new ObservableField<>();
        price = new ObservableDouble();
        sum = new ObservableDouble();
        check = new ObservableBoolean();
        status = new ObservableField<>();
        color = 0;
        colorName = new ObservableField<>();
        needUpdate = new ObservableBoolean();
        needUpdate.set(false);
        parent = RequestViewModel.getInstance();
    }

    public void onTextChanged(Context context, CharSequence s, int start, int before, int charCount) {
        int newCount;
        try {
            newCount = s.toString().isEmpty() ? 0 : Integer.parseInt(s.toString());
        } catch (Exception e) {
            newCount = 0;
        }
        if (newCount != count.get() && newCount != 0)
        {
            needUpdate.set(true);
            if ((multiplicity.get() > 0) && (newCount % multiplicity.get() > 0)){
                count.set(newCount + (multiplicity.get() - (newCount % multiplicity.get())));
                //RouterView.openInfo(context, new Info(false, true,
                // Service.getStr(R.string.text_multiplicity, multiplicity.get())));
            } else {
                count.set(newCount);
            }
            sum.set(count.get() * price.get());
            Request request = new Request(product.get(), requestProduct.get(), count.get(), stockCount.get(),
                    multiplicity.get(), unit.get(), price.get(),
                    check.get(), needUpdate.get(),0, 0,
                    status.get(), colorName.get(), color);
            updateStatus();
            parent.basket.set(position.get(), request);

            Objects.requireNonNull(parent.basketAdapter.get()).notifyItemChanged(position.get());
        }
     }

    public void onFlagClick(View view) {
        this.check.set(((CheckBox) view).isChecked());
        Request request = new Request(product.get(), requestProduct.get(), count.get(), stockCount.get(),
                multiplicity.get(), unit.get(), price.get(),
                check.get(), needUpdate.get(),0, 0,
                status.get(), colorName.get(), color);
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
        RouterServer.putBasket(context, parent);
        RouterServer.getBasket(context, parent);
        updateStatus();
    }

    public void onUpdateStatus(Context context){
        RouterServer.putBasket(context, parent);
        RouterServer.getBasket(context, parent);
        needUpdate.set(false);
        updateStatus();
    }

    void updateStatus() {

         Request request = new Request(product.get(), requestProduct.get(),
                count.get(), stockCount.get(),
                multiplicity.get(), unit.get(), price.get(),
                check.get(), needUpdate.get(), 0, 0,
                status.get(), colorName.get(), color);
        Service.status(request);
        product.set(request.product);
        requestProduct.set(request.requestProduct);
        count.set(request.requestCount);
        stockCount.set(request.stockCount);
        multiplicity.set(request.multiplicity);
        unit.set(request.unit);
        price.set(request.price);
        check.set(request.check);
        needUpdate.set(request.needUpdate);
        status.set(request.status);
        colorName.set(request.colorName);
        color = request.color;

        double total = 0;
        for (Request item : parent.basket) {
            if(item.check) {
                total = total + item.requestCount * item.price;
            }
        }
        parent.total.set(total);
    }
}
