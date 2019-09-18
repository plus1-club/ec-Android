package club.plus1.ec_online.viewmodel.adapters;

import androidx.databinding.BindingConversion;
import androidx.databinding.ObservableDouble;

import java.util.Locale;

import club.plus1.ec_online.R;
import club.plus1.ec_online.model.App;

public class BindingConversions {

    @BindingConversion
    public static String convertObservableDoubleToString(ObservableDouble value) {
        String template = App.getContext().getString(R.string.text_money);
        return value.get() == 0.0 ? "0" : String.format(Locale.getDefault(), template, value.get());
    }
}
