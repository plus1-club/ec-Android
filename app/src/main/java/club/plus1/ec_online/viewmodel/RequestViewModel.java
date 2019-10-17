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

    public ObservableField<String> title;
    public ObservableList<Request> requests;

    private static RequestViewModel mInstance;    // Ссылка для биндинга с View

    private RequestViewModel() {
        product = new ObservableField<>();
        count = new ObservableInt();
        productColumn = new ObservableInt();
        countColumn = new ObservableInt();
        isFullSearch = new ObservableBoolean();
        total = new ObservableDouble();

        title = new ObservableField<>();
        requests = new ObservableArrayList<>();
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
        Intent intent = new Intent(context, RequestsTableActivity.class);
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

    // TODO: Добавлять всё что в списке в корзину
    public void toBasket(Context context){
        Toast.makeText(context, "Добавление в корзину - в разработке", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context, RequestsBasketActivity.class);
        context.startActivity(intent);
    }

    // TODO: Реализовать очистку
    public void onClear(Context context){
        Toast.makeText(context, "Очистить - в разработке", Toast.LENGTH_LONG).show();
    }

    // TODO: Реализовать добавление в корзину
    public void addToBasket(Context context){
        Toast.makeText(context, "Добавить в корзину - в разработке", Toast.LENGTH_LONG).show();
    }

    // TODO: Реализовать оформление заказа
    public void onIssue(Context context){
        Toast.makeText(context, "Оформить заказ - в разработке", Toast.LENGTH_LONG).show();
    }
}
