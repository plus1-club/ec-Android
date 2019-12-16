package ru.electric.ec.online.models;

import androidx.databinding.ObservableDouble;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;

import ru.electric.ec.online.App;
import ru.electric.ec.online.R;
import ru.electric.ec.online.domains.Count;

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

    public static int getInt(String string){
        return Integer.parseInt(Objects.requireNonNull(string));
    }

    public static double getDouble(String string){
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
            text = App.getContext().getString(R.string.status_black);
            color = R.color.black;
        } else if (stockCount == -2) {
            text = App.getContext().getString(R.string.status_violet);
            color = R.color.violet;
        } else if (needUpdate) {
            text = App.getContext().getString(R.string.status_blue);
            color = R.color.blue;
        } else if (stockCount == 0) {
            text = App.getContext().getString(R.string.status_red);
            color = R.color.red;
        } else if (stockCount >= count) {
            text = App.getContext().getString(R.string.status_green);
            color = R.color.green;
        } else if (stockCount > 500 || stockCount == -1) {
            text = App.getContext().getString(R.string.status_orange);
            color = R.color.orange;
        } else {
            text = App.getContext().getString(R.string.status_yellow, stockCount);
            color = R.color.yellow;
        }

        return new Count(count, stockCount, text, color);
    }
}
