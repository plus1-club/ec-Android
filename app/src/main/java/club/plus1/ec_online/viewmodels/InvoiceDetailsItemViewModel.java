package club.plus1.ec_online.viewmodels;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

public class InvoiceDetailsItemViewModel {

    public InvoiceDetailsViewModel parent;
    public ObservableInt position;

    public ObservableField<String> product;
    public ObservableInt count;
    public ObservableDouble price;
    public ObservableDouble sum;
    public ObservableField<String> available;
    public ObservableField<String> delivery;

    public ObservableBoolean showAvailable;
    public ObservableBoolean showDelivery;

    public InvoiceDetailsItemViewModel() {
        product = new ObservableField<>();
        count = new ObservableInt();
        price = new ObservableDouble();
        sum = new ObservableDouble();
        available = new ObservableField<>();
        delivery = new ObservableField<>();
        parent = InvoiceDetailsViewModel.getInstance();
        position = new ObservableInt();
        showAvailable = new ObservableBoolean();
        showDelivery = new ObservableBoolean();
    }

}
