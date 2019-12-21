package ru.electric.ec.online.models;

import android.content.res.Resources;
import android.util.Log;

import androidx.databinding.ObservableDouble;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Objects;

import ru.electric.ec.online.R;

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
        return template.replace("%1$d",textValue);
    }

    public static int getInt(String string) {
        if (string == null || string.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(string);
        }
    }

    public static double getDouble(String string){
        if (string == null || string.isEmpty()) {
            return 0.0;
        } else {
            String cleared = string;
            cleared = cleared.replaceAll(" ", "");
            cleared = cleared.replaceAll(",", ".");
            return Double.parseDouble(cleared);
        }
    }

    public static boolean isEqual(String one, String two){
        return Objects.requireNonNull(one).toLowerCase().equals(two.toLowerCase());
    }

    public static Count status(int count, int stockCount, boolean needUpdate){
        String textStatus;
        String colorName;
        int color;
        if (count == 0) {
            textStatus = Service.getStr(R.string.status_black);
            color = R.color.black;
            colorName = "black";
        } else if (stockCount == -2) {
            textStatus = Service.getStr(R.string.status_violet);
            color = R.color.violet;
            colorName = "violet";
        } else if (needUpdate) {
            textStatus = Service.getStr(R.string.status_blue);
            color = R.color.blue;
            colorName = "blue";
        } else if (stockCount == 0) {
            textStatus = Service.getStr(R.string.status_red);
            color = R.color.red;
            colorName = "red";
        } else if (stockCount >= count) {
            textStatus = Service.getStr(R.string.status_green);
            color = R.color.green;
            colorName = "green";
        } else if (stockCount > 500 || stockCount == -1) {
            textStatus = Service.getStr(R.string.status_orange);
            color = R.color.orange;
            colorName = "orange";
        } else {
            textStatus = Service.getStr(R.string.status_yellow, stockCount);
            color = R.color.yellow;
            colorName = "yellow";
        }

        return new Count(count, stockCount, textStatus, color, colorName);
    }

    public static void log(boolean isError, String message) {
        try{
            if (isError) {
                Log.e("ec", message);
            } else {
                Log.d("ec", message);
            }
        } catch (Exception ignored){
        }
    }

    public static String getStr(int res){
        try{
            return Resources.getSystem().getString(res);
        } catch (Exception e){
            return "";
        }
    }

    static String getStr(int res, Object param){
        try {
            return Resources.getSystem().getString(res, param);
        } catch (Exception e) {
            return param.toString();
        }
    }

    static String getStr(int res, Object param1, Object param2){
        try {
            return Resources.getSystem().getString(res, param1, param2);
        } catch (Exception e) {
            return param1.toString() + param2.toString();
        }
    }
}
