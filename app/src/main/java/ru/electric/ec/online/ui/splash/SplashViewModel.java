package ru.electric.ec.online.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import ru.electric.ec.online.common.App;
import ru.electric.ec.online.ui.enter.EnterActivity;

public class SplashViewModel {

    private final int SPLASH_TIME = 1000;
    public int versionCode;
    public String versionName;

    SplashViewModel(Context context){
        this.versionName = App.versionName;
        this.versionCode = App.versionCode;
    }

    // Запуск экрана "1.Вход" после завершения загрузки приложения
    void startEnterActivity(final Context context) {
        new Handler().postDelayed(() -> {
            Intent mainIntent = new Intent(context, EnterActivity.class);
            context.startActivity(mainIntent);
        }, SPLASH_TIME);
    }
}
