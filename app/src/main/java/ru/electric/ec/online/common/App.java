package ru.electric.ec.online.common;

import android.app.Application;
import android.content.Context;

import ru.electric.ec.online.data.LocalDatabase;
import ru.electric.ec.online.server.ServerNetwork;

public class App extends Application {

    private static Context appContext;
    private static Model model;
    private static LocalDatabase db;

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
        setDb(LocalDatabase.getAppDatabase(getAppContext()));
        ServerNetwork.getInstance();
    }

    private void terminate(){
        setAppContext(null);
        setModel(null);
        LocalDatabase.destroyInstance();
        setDb(null);
    }
}
