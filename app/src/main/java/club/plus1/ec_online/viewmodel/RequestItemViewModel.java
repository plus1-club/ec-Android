package club.plus1.ec_online.viewmodel;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

public class RequestItemViewModel {

    public ObservableField<String> product;
    public ObservableField<String> count;
    public ObservableField<String> price;
    public ObservableField<String> sum;
    public ObservableField<String> status;
    public ObservableInt color;

    public RequestItemViewModel() {
        product = new ObservableField<>();
        count = new ObservableField<>();
        price = new ObservableField<>();
        sum = new ObservableField<>();
        status = new ObservableField<>();
        color = new ObservableInt();
    }
}
