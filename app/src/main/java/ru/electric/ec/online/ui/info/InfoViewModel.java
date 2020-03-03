package ru.electric.ec.online.ui.info;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.ObservableField;

import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.common.Service;
import ru.electric.ec.online.models.Info;
import ru.electric.ec.online.router.RouterView;

public class InfoViewModel {

    private static InfoViewModel mInstance;    // Ссылка для биндинга с View
    public ObservableField<String> title;
    public ObservableField<String> info;
    ObservableField<String> activityName;

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
            String className = Objects.requireNonNull(activityName.get());
            String classGroup = className.split("(?=[A-Z])")[1].toLowerCase();
            Class nextClass = Class.forName("ru.electric.ec.online.ui." + classGroup + "." + className);
            Intent intent = new Intent(context, nextClass);
            intent.putExtra("title", title.get());
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            String message = Service.getStr(R.string.text_acitivity_not_found, activityName);
            RouterView.openInfo(context, new Info(true, true, message));
        }
    }
}
