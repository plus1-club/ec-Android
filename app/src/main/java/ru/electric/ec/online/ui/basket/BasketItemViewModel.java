package ru.electric.ec.online.ui.basket;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.common.Service;
import ru.electric.ec.online.models.Basket;
import ru.electric.ec.online.server.ServerRouter;

public class BasketItemViewModel {

    public BasketViewModel parent;

    public ObservableInt position;
    public ObservableField<String> product;
    public ObservableField<String> requestProduct;
    public ObservableInt count;
    public ObservableInt stockCount;
    public ObservableInt multiplicity;
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
        parent = BasketViewModel.getInstance();
    }

    public void onTextClick(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Количество");

        @SuppressLint("InflateParams")
        View viewInflated = LayoutInflater.from(context).inflate(R.layout.edit_text, null, false);

        final EditText input = viewInflated.findViewById(R.id.input);
        input.setText(String.valueOf(count.get()));
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            dialog.dismiss();
            String text = input.getText().toString();
            int newCount;
            try {
                newCount = text.isEmpty() ? 0 : Integer.parseInt(text);
            } catch (Exception e) {
                newCount = 0;
            }
            if (newCount != count.get() && newCount != 0)
            {
                needUpdate.set(true);
                if ((multiplicity.get() > 0) && (newCount % multiplicity.get() > 0)){
                    count.set(newCount + (multiplicity.get() - (newCount % multiplicity.get())));
                } else {
                    count.set(newCount);
                }
                sum.set(count.get() * price.get());
                Basket request = parent.basket.get(position.get());
                request.needUpdate = needUpdate.get();
                request.requestCount = count.get();
                parent.basket.set(position.get(), request);
                Objects.requireNonNull(parent.adapter.get()).notifyItemChanged(position.get());
                Objects.requireNonNull(parent.adapter.get()).notifyItemChanged(position.get());
            }
        });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());
        builder.show();
    }

    public void onFlagClick(View view) {
        this.check.set(((CheckBox) view).isChecked());
        Basket request = new Basket(product.get(), count.get(), stockCount.get(),
                multiplicity.get(), unit.get(), price.get(),
                check.get(), needUpdate.get(),
                status.get(), colorName.get(), color);
        parent.basket.set(position.get(), request);
        updateStatus();
    }

    public void onDeleteClick(View view) {
        final Context context = view.getContext();
        String deletedProduct = product.get();
        List<Basket> deleted = new ArrayList<>();
        for (Basket item: parent.basket) {
            if (item.product.equals(deletedProduct)){
                deleted.add(item);
            }
        }
        for (Basket item: deleted){
            parent.basket.remove(item);
        }
        ServerRouter.putBasket(context, parent);
        ServerRouter.getBasket(context, parent);
    }

    public void onUpdateStatus(Context context){
        if(needUpdate.get()){
            ServerRouter.putBasket(context, parent);
            ServerRouter.getBasket(context, parent);
            needUpdate.set(false);
            updateStatus();
        }
    }

    void updateStatus() {
        Basket request = parent.basket.get(position.get());
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
        for (Basket item : parent.basket) {
            if(item.check) {
                total = total + item.requestCount * item.price;
            }
        }
        parent.total.set(total);
    }
}
