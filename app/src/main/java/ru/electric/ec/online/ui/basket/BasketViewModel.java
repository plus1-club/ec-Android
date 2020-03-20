package ru.electric.ec.online.ui.basket;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.common.App;
import ru.electric.ec.online.common.Service;
import ru.electric.ec.online.data.DataRouter;
import ru.electric.ec.online.databinding.BasketBinding;
import ru.electric.ec.online.models.Basket;
import ru.electric.ec.online.models.Info;
import ru.electric.ec.online.server.ServerData;
import ru.electric.ec.online.server.ServerRouter;
import ru.electric.ec.online.ui.ViewRouter;
import ru.electric.ec.online.ui.request.RequestActivity;

public class BasketViewModel {

    public ObservableDouble total;
    public ObservableField<String> comment;
    private ObservableInt orderNumber;

    public ObservableField<String> title;
    public ObservableList<Basket> basket;
    public ObservableField<BasketViewAdapter> adapter;
    public ObservableField<BasketBinding> binding;

    private static BasketViewModel mInstance;    // Ссылка для биндинга с View

    private BasketViewModel() {
        total = new ObservableDouble();
        comment = new ObservableField<>();
        orderNumber = new ObservableInt();

        title = new ObservableField<>();
        basket = new ObservableArrayList<>();
        adapter = new ObservableField<>();
        binding = new ObservableField<>();
    }

    // Получение единственного экземпляра класса
    public static BasketViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new BasketViewModel();
        }
        return mInstance;
    }

    public void selectNoneBasket(Context context){
        Objects.requireNonNull(binding.get()).swiperefresh.setRefreshing(true);
        for (Basket item: basket){
            item.check = false;
        }
        total.set(0);
        Objects.requireNonNull(binding.get()).list.setAdapter(adapter.get());
        Objects.requireNonNull(binding.get()).swiperefresh.setRefreshing(false);
    }

    public void selectAllBasket(Context context){
        Objects.requireNonNull(binding.get()).swiperefresh.setRefreshing(true);
        for (Basket item: basket){
            item.check = true;
        }
        total.set(0);
        for (Basket item : basket) {
            if(item.check) {
                total.set(total.get() + item.requestCount * item.price);
            }
        }
        Objects.requireNonNull(binding.get()).list.setAdapter(adapter.get());
        Objects.requireNonNull(binding.get()).swiperefresh.setRefreshing(false);
    }

    public void onClear(final Context context){
        basket.clear();
        ServerRouter.deleteBasket(context, this);
        ServerRouter.getBasket(context, this);
        total.set(0);
    }

    public void addToBasket(Context context){
        Intent intent = new Intent(context, RequestActivity.class);
        intent.putExtra("title", context.getString(R.string.text_request));
        context.startActivity(intent);
    }

    public void onIssue(final Context context){
        ServerRouter.order(context, this, comment.get());
    }

    public void basketOk(Context context, ServerData body) {
        App.getModel().basket.basket.clear();
        if (ServerRouter.isSuccess(body)) {
            List<?> data = (List<?>) body.data;
            for (Object element : data) {
                @SuppressWarnings("unchecked")
                Map<String, String> el = (LinkedTreeMap<String, String>) element;
                Basket request = new Basket(
                        el.get("product"),
                        Service.getInt(el.get("requestCount")),
                        Service.getInt(el.get("stockCount")),
                        Service.getInt(el.get("multiplicity")),
                        el.get("unit"),
                        Service.getDouble(el.get("price")),
                        true,
                        false,
                        "",
                        "",
                        0);
                if ((request.multiplicity > 0) && (request.requestCount % request.multiplicity > 0)) {
                    request.requestCount += request.multiplicity - (request.requestCount % request.multiplicity);
                }
                App.getModel().basket.basket.add(request);
            }
        }
        adapter.set(new BasketViewAdapter());
        Objects.requireNonNull(adapter.get()).setItems(basket);
        Objects.requireNonNull(binding.get()).list.setAdapter(adapter.get());
        total.set(0);
        for (Basket item : basket) {
            if(item.check) {
                total.set(total.get() + item.requestCount * item.price);
            }
        }
        Objects.requireNonNull(binding.get()).swiperefresh.setRefreshing(false);
    }

    public void postBasketOk(Context context, ServerData body) {
        if (ServerRouter.isSuccess(body)) {
            ServerRouter.getBasket(context, this);
        }
        Intent intent = new Intent(context, BasketActivity.class);
        intent.putExtra("title", context.getString(R.string.text_basket));
        context.startActivity(intent);
    }

    public void updateBasketOk(Context context, ServerData body) {
        if (ServerRouter.isSuccess(body)) {
            ServerRouter.getBasket(context, this);
        }
    }

    public void orderOk(Context context, ServerData body) {
        if (ServerRouter.isSuccess(body)) {
            LinkedTreeMap data = (LinkedTreeMap) body.data;
            int orderNumber = Service.getInt((String)data.get("number"));
            App.getModel().basket.orderNumber.set(orderNumber);

            String message = Service.getStr(R.string.text_order_processed, orderNumber);
            Info info = new Info(false, true, message, "BasketActivity");
            info.title = context.getString(R.string.text_basket);
            DataRouter.saveInfo(info);
            ViewRouter.openInfo(context, info);
        } else {
            ViewRouter.onUnsuccessful(context, body);
        }
        basket.clear();
        ServerRouter.deleteBasket(context, this);
        ServerRouter.getBasket(context, this);
        total.set(0);
        comment.set("");
    }

    public void basketError(Context context, Throwable throwable) {
        ViewRouter.onError(context, throwable);
    }
}
