package ru.electric.ec.online.viewmodels;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import ru.electric.ec.online.R;
import ru.electric.ec.online.models.Count;
import ru.electric.ec.online.models.Request;
import ru.electric.ec.online.views.SearchActivity;

public class SearchItemViewModel {

    private static SearchItemViewModel mInstance;
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
    private ObservableBoolean needUpdate;

    private SearchItemViewModel() {
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
        needUpdate = new ObservableBoolean();
        needUpdate.set(false);
        parent = RequestViewModel.getInstance();
    }

    // Получение единственного экземпляра класса
    public static SearchItemViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new SearchItemViewModel();
        }
        return mInstance;
    }

    public void onTextChanged(Context context, CharSequence s, int start, int before, int charCount) {
        int newCount = s.toString().isEmpty() ? 0 : Integer.parseInt(s.toString());
        if (newCount != count.get() && newCount != 0)
        {
            needUpdate.set(true);
            if (newCount % multiplicity.get() > 0){
                count.set(newCount + (multiplicity.get() - (newCount % multiplicity.get())));
                Toast.makeText(context,
                        context.getString(R.string.text_multiplicity, multiplicity.get()),
                        Toast.LENGTH_LONG).show();
            } else {
                count.set(newCount);
            }
            sum.set(newCount * price.get());
            Request request = new Request(product.get(), count.get(), stockCount.get(),
                    multiplicity.get(), unit.get(), price.get(), check.get());
            parent.search.set(position.get(), request);

            if (!needUpdate.get()) {
                ((SearchActivity) context).refreshSearch();
            }
            updateStatus();
        }
     }

    public void onFlagClick(View view) {
        this.check.set(((CheckBox) view).isChecked());
        Request request = new Request(product.get(), count.get(), stockCount.get(),
                multiplicity.get(), unit.get(), price.get(), check.get());
        parent.search.set(position.get(), request);
        updateStatus();
    }

    public void onUpdateStatus(Context context){
        ServerResponse.byCode(context, parent.product.get(), count.get(), parent.isFullSearch.get());
        ((SearchActivity)context).refreshSearch();
        needUpdate.set(false);
        updateStatus();
    }

    public void updateStatus() {
        Count countStatus = Service.status(count.get(), stockCount.get(), needUpdate.get());
        status.set(countStatus.status);
        color.set(countStatus.color);
    }
}
