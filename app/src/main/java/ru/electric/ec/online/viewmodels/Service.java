package ru.electric.ec.online.viewmodels;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.ObservableDouble;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.models.Count;

public class Service {

    public static String rub(ObservableDouble value, String template){
        DecimalFormat formatter=new DecimalFormat();
        DecimalFormatSymbols symbols=DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator(' '); //явно задаем символ разделителя тысяч
        symbols.setDecimalSeparator(',');
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        formatter.setDecimalFormatSymbols(symbols);
        String textValue = formatter.format(value.get());
        return String.format(Locale.getDefault(), template, textValue);
    }

    static int getInt(String string){
        return Integer.parseInt(Objects.requireNonNull(string));
    }

    static double getDouble(String string){
        String cleared = Objects.requireNonNull(string);
        cleared = cleared.replaceAll(" ","");
        cleared = cleared.replaceAll(",",".");
        return Double.parseDouble(cleared);
    }

    public static boolean isEqual(String one, String two){
        return Objects.requireNonNull(one).toLowerCase().equals(two.toLowerCase());
    }

    public static Count status(int count, int stockCount, boolean needUpdate){
        String text;
        int color;
        if (count == 0) {
            text = Service.getStr(R.string.status_black);
            color = R.color.black;
        } else if (stockCount == -2) {
            text = Service.getStr(R.string.status_violet);
            color = R.color.violet;
        } else if (needUpdate) {
            text = Service.getStr(R.string.status_blue);
            color = R.color.blue;
        } else if (stockCount == 0) {
            text = Service.getStr(R.string.status_red);
            color = R.color.red;
        } else if (stockCount >= count) {
            text = Service.getStr(R.string.status_green);
            color = R.color.green;
        } else if (stockCount > 500 || stockCount == -1) {
            text = Service.getStr(R.string.status_orange);
            color = R.color.orange;
        } else {
            text = Service.getStr(R.string.status_yellow, stockCount);
            color = R.color.yellow;
        }

        return new Count(count, stockCount, text, color);
    }

    static void log(Context context, boolean isError, boolean isToast, String message) {
        if (isError) {
            Log.e("ec", message);
        } else {
            Log.d("ec", message);
        }
        if (isToast) Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static String getStr(int res){
        return App.appContext.getString(res);
    }

    private static String getStr(int res, Object param){
        return App.appContext.getString(res, param);
    }
}
