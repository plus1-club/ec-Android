package club.plus1.ec_online.viewmodel.converters;

import androidx.databinding.BindingConversion;
import androidx.databinding.ObservableDouble;

import java.util.Locale;

import club.plus1.ec_online.App;
import club.plus1.ec_online.R;

public class BindingConversions {

    @BindingConversion
    public static String convertObservableDoubleToString(ObservableDouble value) {
        String template = App.getContext().getString(R.string.text_money);
        return value.get() == 0.0 ? "0" : String.format(Locale.getDefault(), template, value.get());
    }
}
