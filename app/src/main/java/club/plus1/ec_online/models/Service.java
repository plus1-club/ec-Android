package club.plus1.ec_online.models;

import androidx.databinding.ObservableDouble;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

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

}
