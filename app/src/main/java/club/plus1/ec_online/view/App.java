package club.plus1.ec_online.view;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    public static String token;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        token = "";
    }

    public static void log(Context context, boolean isError, String message) {
        if (isError) {
            Log.e("ec", message);
        } else {
            Log.d("ec", message);
        }
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mContext = null;
    }
}
