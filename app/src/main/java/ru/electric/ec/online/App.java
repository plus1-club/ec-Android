package ru.electric.ec.online;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import ru.electric.ec.online.server.ServerNetwork;

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    public static Context appContext;
    public static Model model;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        model = new Model();
        ServerNetwork.getInstance();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        appContext = null;
        model = null;
    }
}
