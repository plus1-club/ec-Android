package ru.electric.ec.online.ui;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

public class BindingAdapters {

    @BindingAdapter({"android:buttonTint"})
    public static void setButtonTintList(CheckBox view, int colorId) {
        view.setButtonTintList(ContextCompat.getColorStateList(view.getContext(), colorId));
    }

    @BindingAdapter({"android:backgroundTint"})
    public static void setBackgroundTintList(EditText view, int colorId) {
        view.setBackgroundTintList(ContextCompat.getColorStateList(view.getContext(), colorId));
    }

    @BindingAdapter({"android:textColor"})
    public static void setTextColor(TextView view, int colorId) {
        view.setTextColor(ContextCompat.getColorStateList(view.getContext(), colorId));
    }

    @BindingAdapter({"android:src"})
    public static void setSrc(ImageView view, int icon) {
        view.setImageResource(icon);
    }

}
