package ru.electric.ec.online.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.widget.Toast;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import java.util.ArrayList;
import java.util.List;

import ru.electric.ec.online.App;
import ru.electric.ec.online.R;
import ru.electric.ec.online.domains.Request;
import ru.electric.ec.online.models.ServerResponse;
import ru.electric.ec.online.views.RequestActivity;
import ru.electric.ec.online.views.RequestsBasketActivity;
import ru.electric.ec.online.views.RequestsSearchActivity;

public class RequestViewModel {

    public ObservableField<String> product;
    public ObservableInt count;
    public ObservableInt productColumn;
    public ObservableInt countColumn;
    public ObservableBoolean isFullSearch;
    public ObservableDouble total;
    public ObservableField<String> comment;

    private ObservableField<String> title;
    public ObservableList<Request> requests;
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

        title = new ObservableField<>();
        requests = new ObservableArrayList<>();
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

    // TODO: Реализовать выбор файла из папки
    public void onBrowse(Context context) {
        Toast.makeText(context, "Выбор файла - в разработке", Toast.LENGTH_LONG).show();
    }

    public void onNext(Context context){
        Intent intent = new Intent(context, RequestsSearchActivity.class);
        intent.putExtra("title", title.get());
        context.startActivity(intent);
    }

    // TODO: Подключаться к серверу и скачивать остатки
    public void linkStock(Context context){
        Toast.makeText(context, "Скачивание остатков - в разработке", Toast.LENGTH_LONG).show();
    }

    public void linkSample(Context context){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ec-electric.ru/order/example.xls"));
        context.startActivity(intent);
    }

    public void toBasket(final Context context){
        List<Request> added = new ArrayList<>();
        for (Request request: requests) {
            if (request.check){
                added.add(request);
            }
        }
        App.model.basket.addAll(added);
        ServerResponse.postBasket(context, added);

        Intent intent = new Intent(context, RequestsBasketActivity.class);
        intent.putExtra("title", context.getString(R.string.text_basket));
        context.startActivity(intent);
    }

    public void onClear(final Context context){
        App.model.basket.clear();
        ServerResponse.deleteBasket(context);
        ((RequestsBasketActivity)context).refreshBasket();
        total.set(0);
    }

    public void addToBasket(Context context){
        Intent intent = new Intent(context, RequestActivity.class);
        intent.putExtra("title", context.getString(R.string.text_request));
        context.startActivity(intent);
    }

    public void onIssue(final Context context){
        ServerResponse.order(context, comment.get());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                App.model.basket.clear();
                ServerResponse.deleteBasket(context);
                ((RequestsBasketActivity)context).refreshBasket();
                total.set(0);
            }
        }, 500);
    }
}
