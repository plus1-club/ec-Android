package ru.electric.ec.online.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;

import ru.electric.ec.online.models.Info;
import ru.electric.ec.online.router.RouterView;
import ru.electric.ec.online.ui.enter.EnterActivity;

public class SplashViewModel {

    private final int SPLASH_TIME = 1000;
    public int versionCode;
    public String versionName;

    SplashViewModel(Context context){
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            RouterView.openInfo(context, new Info(true, true, "Не удалось найти пакет приложения"));
        }
        if (pInfo == null) {
            pInfo = new PackageInfo();
            pInfo.versionName = "0.0";
            pInfo.versionCode = 0;
        }
        this.versionName = pInfo.versionName;
        this.versionCode = pInfo.versionCode;
    }

    // Запуск экрана "1.Вход" после завершения загрузки приложения
    void startEnterActivity(final Context context) {
        new Handler().postDelayed(() -> {
            Intent mainIntent = new Intent(context, EnterActivity.class);
            context.startActivity(mainIntent);
        }, SPLASH_TIME);
    }
}
