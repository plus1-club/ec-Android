package ru.electric.ec.online.ui.request;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import java.util.ArrayList;
import java.util.List;

import ru.electric.ec.online.R;
import ru.electric.ec.online.Service;
import ru.electric.ec.online.models.Request;
import ru.electric.ec.online.server.ServerResponse;
import ru.electric.ec.online.ui.basket.BasketActivity;
import ru.electric.ec.online.ui.files.FilesActivity;
import ru.electric.ec.online.ui.info.InfoActivity;
import ru.electric.ec.online.ui.info.InfoViewModel;
import ru.electric.ec.online.ui.search.SearchActivity;

public class RequestViewModel {

    public ObservableField<String> product;
    public ObservableInt count;
    public ObservableInt productColumn;
    public ObservableInt countColumn;
    public ObservableBoolean isFullSearch;
    public ObservableDouble total;
    public ObservableField<String> comment;
    public ObservableInt orderNumber;

    private ObservableField<String> title;
    public ObservableList<Request> search;
    public ObservableList<Request> basket;
    public ObservableField<Object> searchAdapter;
    public ObservableField<Object> basketAdapter;

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
        isFullSearch.set(true);

        title = new ObservableField<>();
        search = new ObservableArrayList<>();
        basket = new ObservableArrayList<>();
        searchAdapter = new ObservableField<>();
        basketAdapter = new ObservableField<>();
    }

    // Получение единственного экземпляра класса
    public static RequestViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new RequestViewModel();
        }
        return mInstance;
    }

    public void onBrowse(Context context) {
        Intent intent = new Intent(context, FilesActivity.class);
        intent.putExtra("title", "Выбор файла");
        context.startActivity(intent);
    }

    public void onNext(Context context){
        title.set(((Activity)context).getTitle().toString());
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra("title", (title.get()));
        context.startActivity(intent);
    }

    // TODO: Подключаться к серверу и скачивать остатки
    public void linkStock(Context context){
        InfoViewModel.log(context, false, true,
                Service.getStr(R.string.text_in_develop_download_remains));
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
            ServerResponse.postBasket(context, added);
            total.set(0);
            comment.set("");

            new Handler().postDelayed(() -> {
                Intent intent = new Intent(context, BasketActivity.class);
                intent.putExtra("title", context.getString(R.string.text_basket));
                context.startActivity(intent);
            }, 3000);
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
        ServerResponse.deleteBasket(context);
        ServerResponse.getBasket(context);
        ((BasketActivity)context).refreshBasket();
        total.set(0);
    }

    public void addToBasket(Context context){
        Intent intent = new Intent(context, RequestActivity.class);
        intent.putExtra("title", context.getString(R.string.text_request));
        context.startActivity(intent);
    }

    public void onIssue(final Context context){
        ServerResponse.order(context, comment.get());

        new Handler().postDelayed(() -> {
            basket.clear();
            ServerResponse.deleteBasket(context);
            ServerResponse.getBasket(context);
            ((BasketActivity)context).refreshBasket();
            total.set(0);
            comment.set("");
        }, 3000);
    }
}
