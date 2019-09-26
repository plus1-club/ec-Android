package club.plus1.ec_online.viewmodel;

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

import club.plus1.ec_online.model.Request;
import club.plus1.ec_online.view.RequestsBasketActivity;
import club.plus1.ec_online.view.RequestsTableActivity;

public class RequestViewModel {

    public ObservableField<String> product;
    public ObservableInt count;
    public ObservableInt productColumn;
    public ObservableInt countColumn;
    public ObservableBoolean isFullSearch;
    public ObservableDouble total;

    public ObservableList<Request> requests;

    private static RequestViewModel mInstance;    // Ссылка для биндинга с View

    private RequestViewModel() {
        product = new ObservableField<>();
        count = new ObservableInt();
        productColumn = new ObservableInt();
        countColumn = new ObservableInt();
        isFullSearch = new ObservableBoolean();
        total = new ObservableDouble();

        requests = new ObservableArrayList<>();
    }

    // Получение единственного экземпляра класса
    public static RequestViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new RequestViewModel();
        }
        return mInstance;
    }

    public void onNext(Context context){
        Intent intent = new Intent(context, RequestsTableActivity.class);
        context.startActivity(intent);
    }

    public void linkStock(Context context){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ec-electric.ru/stock_free/"));
        context.startActivity(intent);
    }

    public void linkSample(Context context){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ec-electric.ru/order/example.xls"));
        context.startActivity(intent);
    }

    // TODO: Добавлять всё что в списке в корзину
    public void toBasket(Context context){
        Intent intent = new Intent(context, RequestsBasketActivity.class);
        context.startActivity(intent);
    }

    // TODO: Реализовать пересчет
    public void onRecount(Context context){
        Toast.makeText(context, "Пересчитать", Toast.LENGTH_LONG).show();
    }

    // TODO: Реализовать очистку
    public void onClear(Context context){
        Toast.makeText(context, "Очистить", Toast.LENGTH_LONG).show();
    }

    // TODO: Реализовать добавление в корзину
    public void addToBasket(Context context){
        Toast.makeText(context, "Добавить в корзину", Toast.LENGTH_LONG).show();
    }

    // TODO: Реализовать оформление заказа
    public void onIssue(Context context){
        Toast.makeText(context, "Оформить заказ", Toast.LENGTH_LONG).show();
    }
}
