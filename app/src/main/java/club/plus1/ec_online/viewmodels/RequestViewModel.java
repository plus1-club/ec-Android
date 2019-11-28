package club.plus1.ec_online.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import club.plus1.ec_online.App;
import club.plus1.ec_online.R;
import club.plus1.ec_online.domains.Request;
import club.plus1.ec_online.models.ServerResponse;
import club.plus1.ec_online.viewadapters.RequestsBasketAdapter;
import club.plus1.ec_online.viewadapters.RequestsSearchAdapter;
import club.plus1.ec_online.views.RequestActivity;
import club.plus1.ec_online.views.RequestsBasketActivity;
import club.plus1.ec_online.views.RequestsSearchActivity;

public class RequestViewModel {

    public ObservableField<String> product;
    public ObservableInt count;
    public ObservableInt productColumn;
    public ObservableInt countColumn;
    public ObservableBoolean isFullSearch;
    public ObservableDouble total;
    public ObservableField<String> comment;

    public ObservableField<String> title;
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

    public void toBasket(Context context){
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
        notifyBasket();
    }

    public void onClear(Context context){
        App.model.basket.clear();
        ServerResponse.deleteBasket(context);
        Intent intent = new Intent(context, RequestsBasketActivity.class);
        intent.putExtra("title", context.getString(R.string.text_basket));
        context.startActivity(intent);
        notifyBasket();
    }

    public void addToBasket(Context context){
        Intent intent = new Intent(context, RequestActivity.class);
        intent.putExtra("title", context.getString(R.string.text_request));
        context.startActivity(intent);
    }

    public void onIssue(Context context){
        ServerResponse.order(context, comment.get());
    }

    void notifySearch(){
        Objects.requireNonNull((RequestsSearchAdapter) searchAdapter.get()).notifyDataSetChanged();
    }

    void notifyBasket(){
        Objects.requireNonNull((RequestsBasketAdapter) basketAdapter.get()).notifyDataSetChanged();
    }
}
