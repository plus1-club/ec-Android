package ru.electric.ec.online.viewmodels;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import ru.electric.ec.online.App;
import ru.electric.ec.online.R;
import ru.electric.ec.online.domains.Count;
import ru.electric.ec.online.domains.Request;
import ru.electric.ec.online.models.Service;
import ru.electric.ec.online.views.SearchActivity;

public class SearchItemViewModel {

    public RequestViewModel parent;

    public ObservableInt position;
    public ObservableField<String> product;
    public ObservableInt count;
    public ObservableInt stockCount;
    public ObservableInt multiplicity;
    public ObservableField<String> unit;
    private ObservableDouble price;
    public ObservableDouble sum;
    public ObservableField<String> status;
    public ObservableInt color;
    public ObservableBoolean check;

    public SearchItemViewModel() {
        position = new ObservableInt();
        product = new ObservableField<>();
        count = new ObservableInt();
        stockCount = new ObservableInt();
        multiplicity = new ObservableInt();
        unit = new ObservableField<>();
        price = new ObservableDouble();
        sum = new ObservableDouble();
        status = new ObservableField<>();
        color = new ObservableInt();
        check = new ObservableBoolean();
        parent = RequestViewModel.getInstance();
    }

    public void onTextChanged(Context context, CharSequence s, int start, int before, int count) {
        int newCount = s.toString().isEmpty() ? 0 : Integer.parseInt(s.toString());
        if (newCount != this.count.get() && newCount != 0)
        {
            if (newCount % multiplicity.get() > 0){
                this.count.set(newCount + (multiplicity.get() - (newCount % multiplicity.get())));
                Toast.makeText(context,
                        context.getString(R.string.text_multiplicity, multiplicity.get()),
                        Toast.LENGTH_LONG).show();
            } else {
                this.count.set(newCount);
            }
            this.sum.set(newCount * price.get());
            Request request = new Request(product.get(), this.count.get(), stockCount.get(),
                    multiplicity.get(), unit.get(), price.get(), check.get());
            parent.requests.set(position.get(), request);
            App.model.search.clear();
            App.model.search.addAll(parent.requests);

            ((SearchActivity)context).refreshSearch();
            updateStatus();
        }
     }

    public void onFlagClick(View view) {
        this.check.set(((CheckBox) view).isChecked());
        Request request = new Request(product.get(), count.get(), stockCount.get(),
                multiplicity.get(), unit.get(), price.get(), check.get());
        parent.requests.set(position.get(), request);
        updateStatus();
    }

    public void updateStatus() {
        Count countStatus = Service.status(count.get(), stockCount.get());
        status.set(countStatus.status);
        color.set(countStatus.color);
    }
}
