package ru.electric.ec.online.ui.request;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.core.app.ActivityCompat;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.common.App;
import ru.electric.ec.online.common.Service;
import ru.electric.ec.online.databinding.BasketBinding;
import ru.electric.ec.online.databinding.SearchBinding;
import ru.electric.ec.online.models.Info;
import ru.electric.ec.online.models.Request;
import ru.electric.ec.online.router.RouterData;
import ru.electric.ec.online.router.RouterServer;
import ru.electric.ec.online.router.RouterView;
import ru.electric.ec.online.server.ServerData;
import ru.electric.ec.online.ui.basket.BasketActivity;
import ru.electric.ec.online.ui.basket.BasketViewAdapter;
import ru.electric.ec.online.ui.files.FilesActivity;
import ru.electric.ec.online.ui.info.InfoActivity;
import ru.electric.ec.online.ui.search.SearchActivity;
import ru.electric.ec.online.ui.search.SearchViewAdapter;

public class RequestViewModel {

    public ObservableField<String> product;
    public ObservableInt count;
    public ObservableInt productColumn;
    public ObservableInt countColumn;
    public ObservableBoolean isFullSearch;
    public ObservableDouble total;
    public ObservableField<String> comment;
    private ObservableInt orderNumber;
    public ObservableField<String> excel;
    public ObservableBoolean isExcel;

    public ObservableField<String> title;
    public ObservableList<Request> search;
    public ObservableList<Request> basket;
    public ObservableField<SearchViewAdapter> searchAdapter;
    public ObservableField<BasketViewAdapter> basketAdapter;
    public ObservableField<SearchBinding> searchBinding;
    public ObservableField<BasketBinding> basketBinding;

    private static RequestViewModel mInstance;    // Ссылка для биндинга с View

    private RequestViewModel() {
        product = new ObservableField<>();
        count = new ObservableInt();
        count.set(1);
        productColumn = new ObservableInt();
        productColumn.set(1);
        countColumn = new ObservableInt();
        countColumn.set(2);
        isFullSearch = new ObservableBoolean();
        total = new ObservableDouble();
        comment = new ObservableField<>();
        orderNumber = new ObservableInt();
        excel = new ObservableField<>();
        isExcel = new ObservableBoolean();

        isFullSearch.set(true);

        title = new ObservableField<>();
        search = new ObservableArrayList<>();
        basket = new ObservableArrayList<>();
        searchAdapter = new ObservableField<>();
        basketAdapter = new ObservableField<>();
        searchBinding = new ObservableField<>();
        basketBinding = new ObservableField<>();
    }

    // Получение единственного экземпляра класса
    public static RequestViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new RequestViewModel();
        }
        return mInstance;
    }

    public void onBrowse(Context context) {
        final int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)context, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        } else {
            Intent intent = new Intent(context, FilesActivity.class);
            intent.putExtra("title", (title.get()));
            context.startActivity(intent);
        }
    }

    public void onNext(Context context){
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra("title", (title.get()));
        context.startActivity(intent);
    }

    // TODO: Подключаться к серверу и скачивать остатки
    public void linkStock(Context context){
        RouterView.openInfo(context, new Info(false, true,
                Service.getStr(R.string.text_in_develop_download_remains)));
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.ec-electric.ru/order/example.xls"));
        context.startActivity(intent);
    }

    // TODO: Подключаться к серверу и cкачать пример файла
    public void linkSample(Context context){
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.ec-electric.ru/order/example.xls"));
        context.startActivity(intent);
    }

    public void toBasket(final Context context){
        List<Request> added = new ArrayList<>();
        int count = 0;
        for (Request request: search) {
            if (request.check){
                count ++;
            }
        }
        if (count > 0) {
            for (Request request: search) {
                if (request.check){
                    added.add(request);
                }
            }
            basket.addAll(added);
            RouterServer.postBasket(context, this, added);
            total.set(0);
            comment.set("");
        } else {
            Intent intent = new Intent(context, InfoActivity.class);
            intent.putExtra("title", title.get());
            intent.putExtra("info", context.getString(R.string.text_product_not_select));
            intent.putExtra("activityName", "SearchActivity");
            context.startActivity(intent);
        }
    }

    public void onClear(final Context context){
        basket.clear();
        RouterServer.deleteBasket(context, this);
        RouterServer.getBasket(context, this);
        total.set(0);
    }

    public void addToBasket(Context context){
        Intent intent = new Intent(context, RequestActivity.class);
        intent.putExtra("title", context.getString(R.string.text_request));
        context.startActivity(intent);
    }

    public void onIssue(final Context context){
        RouterServer.order(context, this, comment.get());
    }

    public void searchOk(Context context, ServerData body) {
        App.getModel().request.search.clear();
        if (RouterServer.isSuccess(body)) {
            List<?> data = (List<?>) body.data;
            for (Object element : data) {
                @SuppressWarnings("unchecked")
                Map<String, String> el = (LinkedTreeMap<String, String>) element;
                Request request = new Request(
                        el.get("product"),
                        Service.getInt(el.get("requestCount")),
                        Service.getInt(el.get("stockCount")),
                        Service.getInt(el.get("multiplicity")),
                        el.get("unit"),
                        false);
                if (request.requestCount % request.multiplicity > 0) {
                    request.requestCount += request.multiplicity - (request.requestCount % request.multiplicity);
                }
                App.getModel().request.search.add(request);
            }
        } else {
            RouterView.onUnsuccessful(context, body, "RequestActivity");
        }
        searchAdapter.set(new SearchViewAdapter());
        if (search.size() > 0){
            Objects.requireNonNull(searchAdapter.get()).setItems(search);
            Objects.requireNonNull(searchBinding.get()).list.setAdapter(searchAdapter.get());
            Objects.requireNonNull(searchBinding.get()).swiperefresh.setRefreshing(false);
        } else {
            String message = context.getString(R.string.text_product_not_found);
            Info info = new Info(false, true, message, "RequestActivity");
            RouterView.openInfo(context, info);
        }
    }

    public void searchError(Context context, Throwable throwable) {
        RouterView.onError(context, throwable, "RequestActivity");
    }

    public void basketOk(Context context, ServerData body) {
        App.getModel().request.basket.clear();
        if (RouterServer.isSuccess(body)) {
            List<?> data = (List<?>) body.data;
            for (Object element : data) {
                @SuppressWarnings("unchecked")
                Map<String, String> el = (LinkedTreeMap<String, String>) element;
                Request request = new Request(
                        el.get("product"),
                        Service.getInt(el.get("requestCount")),
                        Service.getInt(el.get("stockCount")),
                        Service.getInt(el.get("multiplicity")),
                        el.get("unit"),
                        Service.getDouble(el.get("price")),
                        true);
                if (request.requestCount % request.multiplicity > 0) {
                    request.requestCount += request.multiplicity - (request.requestCount % request.multiplicity);
                }
                App.getModel().request.basket.add(request);
            }
        }
        basketAdapter.set(new BasketViewAdapter());
        Objects.requireNonNull(basketAdapter.get()).setItems(basket);
        Objects.requireNonNull(basketBinding.get()).list.setAdapter(basketAdapter.get());
        total.set(0);
        basketAdapter.set(basketAdapter.get());
        for (Request item : basket) {
            if(item.check) {
                total.set(total.get() + item.requestCount * item.price);
            }
        }
        Objects.requireNonNull(basketBinding.get()).swiperefresh.setRefreshing(false);
    }

    public void postBasketOk(Context context, ServerData body) {
        if (RouterServer.isSuccess(body)) {
            RouterServer.getBasket(context, this);
        }
        Intent intent = new Intent(context, BasketActivity.class);
        intent.putExtra("title", context.getString(R.string.text_basket));
        context.startActivity(intent);
    }

    public void updateBasketOk(Context context, ServerData body) {
        if (RouterServer.isSuccess(body)) {
            RouterServer.getBasket(context, this);
        }
    }

    public void orderOk(Context context, ServerData body) {
        if (RouterServer.isSuccess(body)) {
            LinkedTreeMap data = (LinkedTreeMap) body.data;
            int orderNumber = Service.getInt((String)data.get("number"));
            App.getModel().request.orderNumber.set(orderNumber);

            String message = Service.getStr(R.string.text_order_processed, orderNumber);
            Info info = new Info(false, true, message, "BasketActivity");
            info.title = context.getString(R.string.text_basket);
            RouterData.saveInfo(info);
            RouterView.openInfo(context, info);
        } else {
            RouterView.onUnsuccessful(context, body);
        }
        basket.clear();
        RouterServer.deleteBasket(context, this);
        RouterServer.getBasket(context, this);
        total.set(0);
        comment.set("");
    }

    public void basketError(Context context, Throwable throwable) {
        RouterView.onError(context, throwable);
    }
}
