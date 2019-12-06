package ru.electric.ec.online.viewmodels;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.ObservableField;

public class InfoViewModel {

    private static InfoViewModel mInstance;    // Ссылка для биндинга с View
    public ObservableField<String> title;
    public ObservableField<String> info;
    public ObservableField<String> activityName;

    private InfoViewModel() {
        title = new ObservableField<>();
        info = new ObservableField<>();
        activityName = new ObservableField<>();
    }

    // Получение единственного экземпляра класса
    public static InfoViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new InfoViewModel();
        }
        return mInstance;
    }

    public void onClose(Context context){
        try {
            Class nextClass = Class.forName("ru.electric.ec.online.views." + activityName.get());
            Intent intent = new Intent(context, nextClass);
            intent.putExtra("title", title.get());
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
