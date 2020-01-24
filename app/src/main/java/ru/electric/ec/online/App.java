package ru.electric.ec.online;

import android.app.Application;
import android.content.Context;

import ru.electric.ec.online.server.ServerNetwork;

public class App extends Application {

    private static Context appContext;
    private static Model model;

    public static Context getAppContext() {
        return appContext;
    }

    public static void setAppContext(Context appContext) {
        App.appContext = appContext;
    }

    public static Model getModel() {
        return model;
    }

    public static void setModel(Model model) {
        App.model = model;
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
        ServerNetwork.getInstance();
    }

    private void terminate(){
        setAppContext(null);
        setModel(null);
    }
}
