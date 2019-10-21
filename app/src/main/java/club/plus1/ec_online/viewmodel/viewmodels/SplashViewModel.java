package club.plus1.ec_online.viewmodel.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;

import club.plus1.ec_online.view.activities.EnterActivity;

public class SplashViewModel {

    private final int SPLASH_TIME = 1000;
    public int versionCode;
    public String versionName;

    public SplashViewModel(Context context){
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
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
    public void startEnterActivity(final Context context) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(context, EnterActivity.class);
                context.startActivity(mainIntent);
            }
        }, SPLASH_TIME);
    }
}
