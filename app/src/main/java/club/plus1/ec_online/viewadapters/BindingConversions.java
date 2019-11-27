package club.plus1.ec_online.viewadapters;

import androidx.databinding.BindingConversion;
import androidx.databinding.ObservableDouble;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import club.plus1.ec_online.App;
import club.plus1.ec_online.R;

public class BindingConversions {

    @BindingConversion
    public static String convertObservableDoubleToString(ObservableDouble value) {
        String template = App.getContext().getString(R.string.text_money);
        DecimalFormat formatter=new DecimalFormat();
        DecimalFormatSymbols symbols=DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator(' '); //явно задаем символ разделителя тысяч
        symbols.setDecimalSeparator(',');
        formatter.setDecimalFormatSymbols(symbols);
        String textValue = formatter.format(value.get());
        return String.format(Locale.getDefault(), template, textValue);
    }
}
