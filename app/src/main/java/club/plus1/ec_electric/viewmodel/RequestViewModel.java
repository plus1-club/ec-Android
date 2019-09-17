package club.plus1.ec_electric.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import club.plus1.ec_electric.view.RequestsTableActivity;

public class RequestViewModel {

    public ObservableField<String> product;
    public ObservableField<String> count;
    public ObservableField<String> productColumn;
    public ObservableField<String> countColumn;
    public ObservableBoolean isFullSearch;

    private static RequestViewModel mInstance;    // Ссылка для биндинга с View

    private RequestViewModel(){}

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
        Toast.makeText(context, "Складские остатки", Toast.LENGTH_LONG).show();
    }

    public void linkSpecifications(Context context){
        Toast.makeText(context, "Спецификации", Toast.LENGTH_LONG).show();
    }

    public void linkSample(Context context){
        Toast.makeText(context, "Пример файла", Toast.LENGTH_LONG).show();
    }
}
