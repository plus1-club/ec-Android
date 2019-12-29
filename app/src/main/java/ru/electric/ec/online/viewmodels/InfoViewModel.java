package ru.electric.ec.online.viewmodels;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.ObservableField;

import ru.electric.ec.online.R;
import ru.electric.ec.online.Service;
import ru.electric.ec.online.views.InfoActivity;

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

    public static void log(Context context, boolean isError, boolean showToast, String message){
        Service.log(isError, message);
        if (showToast) {
            Intent intent = new Intent(context, InfoActivity.class);
            if (isError) {
                intent.putExtra("title", Service.getStr(R.string.text_error));
            } else {
                intent.putExtra("title", Service.getStr(R.string.text_info));
            }
            intent.putExtra("info", message);
            intent.putExtra("activityName", context.getClass().getSimpleName());
            context.startActivity(intent);
        }
    }
}
