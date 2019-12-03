package ru.electric.ec.online;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import ru.electric.ec.online.domains.Model;
import ru.electric.ec.online.models.ServerNetwork;

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    public static Model model;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        model = new Model();
        ServerNetwork.getInstance();
    }

    public static void log(Context context, boolean isError, boolean isToast, String message) {
        if (isError) {
            Log.e("ec", message);
        } else {
            Log.d("ec", message);
        }
        if (isToast) Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mContext = null;
    }
}
