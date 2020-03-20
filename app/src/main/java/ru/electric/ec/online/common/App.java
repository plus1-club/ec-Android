package ru.electric.ec.online.common;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import ru.electric.ec.online.data.LocalDatabase;
import ru.electric.ec.online.models.Info;
import ru.electric.ec.online.server.ServerNetwork;
import ru.electric.ec.online.ui.ViewRouter;

public class App extends Application {

    private static Context appContext;
    private static Model model;
    private static LocalDatabase db;
    public static int versionCode;
    public static String versionName;

    public static Context getAppContext() {
        return appContext;
    }

    public static void setAppContext(Context appContext) {
        App.appContext = appContext;
    }

    public static Model getModel() {
        if (model == null){
            setModel(new Model());
        }
        return model;
    }

    public static void setModel(Model model) {
        App.model = model;
    }

    public static LocalDatabase getDb() {
        return db;
    }

    public static void setDb(LocalDatabase db) {
        App.db = db;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        terminate();
    }

    private void init(){
        setAppContext(this);
        setModel(new Model());
        setDb(LocalDatabase.getLocalDatabase(this));
        ServerNetwork.getInstance();
        PackageInfo pInfo = null;
        try {
            PackageManager pm = getPackageManager();
            if (pm != null){
                pInfo = pm.getPackageInfo(getPackageName(), 0);
            }
        } catch (PackageManager.NameNotFoundException e) {
            ViewRouter.openInfo(getAppContext(), new Info(true, true, "Не удалось найти пакет приложения"));
        }
        if (pInfo == null) {
            pInfo = new PackageInfo();
            pInfo.versionName = "0.0";
            pInfo.versionCode = 0;
        }
        versionName = pInfo.versionName;
        versionCode = pInfo.versionCode;
    }

    private void terminate(){
        setAppContext(null);
        setModel(null);
        LocalDatabase.destroyInstance();
        setDb(null);
    }
}
