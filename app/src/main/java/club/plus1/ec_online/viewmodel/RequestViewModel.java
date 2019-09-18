package club.plus1.ec_online.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import club.plus1.ec_online.model.Request;
import club.plus1.ec_online.view.RequestsBasketActivity;
import club.plus1.ec_online.view.RequestsTableActivity;

public class RequestViewModel {

    public ObservableField<String> product;
    public ObservableField<String> count;
    public ObservableField<String> productColumn;
    public ObservableField<String> countColumn;
    public ObservableBoolean isFullSearch;
    public ObservableList<Request> requests;
    public ObservableDouble total;

    private static RequestViewModel mInstance;    // Ссылка для биндинга с View

    private RequestViewModel() {
        requests = new ObservableArrayList<>();
        total = new ObservableDouble();
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

    // TODO: Реализовать скачивание складских остатков
    public void linkStock(Context context){
        Toast.makeText(context, "Складские остатки", Toast.LENGTH_LONG).show();
    }

    // TODO: Реализовать скачивание спецификаций
    public void linkSpecifications(Context context){
        Toast.makeText(context, "Спецификации", Toast.LENGTH_LONG).show();
    }

    // TODO: Реализовать скачивание примера файла
    public void linkSample(Context context){
        Toast.makeText(context, "Пример файла", Toast.LENGTH_LONG).show();
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
