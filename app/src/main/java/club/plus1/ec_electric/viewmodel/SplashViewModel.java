package club.plus1.ec_electric.viewmodel;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class SplashViewModel {

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
}
