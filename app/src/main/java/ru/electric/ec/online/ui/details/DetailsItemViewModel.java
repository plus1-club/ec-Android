package ru.electric.ec.online.ui.details;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import java.util.Objects;

import ru.electric.ec.online.R;

public class DetailsItemViewModel {

    public DetailsViewModel parent;
    public ObservableInt position;

    public ObservableField<String> product;
    public ObservableInt count;
    public ObservableField<String> unit;
    public ObservableDouble price;
    public ObservableDouble sum;
    public ObservableField<String> available;
    public ObservableField<String> delivery;
    public ObservableInt color;

    public ObservableBoolean showAvailable;
    ObservableBoolean showDelivery;

    DetailsItemViewModel() {
        product = new ObservableField<>();
        count = new ObservableInt();
        unit = new ObservableField<>();
        price = new ObservableDouble();
        sum = new ObservableDouble();
        available = new ObservableField<>();
        delivery = new ObservableField<>();
        color = new ObservableInt();
        parent = DetailsViewModel.getInstance();
        position = new ObservableInt();
        showAvailable = new ObservableBoolean();
        showDelivery = new ObservableBoolean();
    }

    void updateColor() {
        if (Objects.equals(available.get(), "Нет")) {
            color.set(R.color.red);
        } else if (Objects.equals(available.get(), "В наличии")) {
            color.set(R.color.green);
        } else if (Objects.requireNonNull(available.get()).contains("Есть только")) {
            color.set(R.color.yellow);
         } else {
            color.set(R.color.black);
        }
    }
}
