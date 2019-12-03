package ru.electric.ec.online.models;

import androidx.databinding.ObservableDouble;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;

public class Service {

    public static String rub(ObservableDouble value, String template){
        DecimalFormat formatter=new DecimalFormat();
        DecimalFormatSymbols symbols=DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator(' '); //явно задаем символ разделителя тысяч
        symbols.setDecimalSeparator(',');
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
}
