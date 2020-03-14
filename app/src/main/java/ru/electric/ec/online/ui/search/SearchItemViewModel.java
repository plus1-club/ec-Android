package ru.electric.ec.online.ui.search;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import ru.electric.ec.online.common.Service;
import ru.electric.ec.online.models.Count;
import ru.electric.ec.online.models.Request;
import ru.electric.ec.online.router.RouterServer;
import ru.electric.ec.online.ui.request.RequestViewModel;

public class SearchItemViewModel {

    public RequestViewModel parent;

    public ObservableInt position;
    public ObservableField<String> product;
    public ObservableField<String> requestProduct;
    public ObservableInt count;
    public ObservableInt stockCount;
    ObservableInt multiplicity;
    public ObservableField<String> unit;
    private ObservableDouble price;
    public ObservableDouble sum;
    public ObservableField<String> status;
    public int color;
    public ObservableBoolean check;
    private ObservableBoolean needUpdate;

    SearchItemViewModel() {
        position = new ObservableInt();
        product = new ObservableField<>();
        requestProduct = new ObservableField<>();
        count = new ObservableInt();
        stockCount = new ObservableInt();
        multiplicity = new ObservableInt();
        unit = new ObservableField<>();
        price = new ObservableDouble();
        sum = new ObservableDouble();
        status = new ObservableField<>();
        check = new ObservableBoolean();
        needUpdate = new ObservableBoolean();
        needUpdate.set(false);
        parent = RequestViewModel.getInstance();
    }

    public void onTextChanged(Context context, CharSequence s, int start, int before, int charCount) {
        int newCount;
        try {
            newCount = s.toString().isEmpty() ? 0 : Integer.parseInt(s.toString());
        } catch (Exception e) {
            newCount = 0;
        }
        if (newCount != count.get() && newCount != 0)
        {
            needUpdate.set(true);
            if (newCount % multiplicity.get() > 0){
                count.set(newCount + (multiplicity.get() - (newCount % multiplicity.get())));
                //RouterView.openInfo(context, new Info(false, true,
                //  Service.getStr(R.string.text_multiplicity, count.get())));
            } else {
                count.set(newCount);
            }
            sum.set(count.get() * price.get());
            Request request = new Request(product.get(), requestProduct.get(), count.get(), stockCount.get(),
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
        Request request = new Request(product.get(), requestProduct.get(), count.get(), stockCount.get(),
                multiplicity.get(), unit.get(), price.get(), check.get());
        parent.search.set(position.get(), request);
        updateStatus();
    }

    public void onUpdateStatus(Context context){
        if (parent.isExcel.get()){
            RouterServer.fromExcel(context, parent);
        } else {
            RouterServer.byCode(context, parent);
        }
        ((SearchActivity)context).refreshSearch();
        needUpdate.set(false);
        updateStatus();
    }

    void updateStatus() {
        Count countStatus = Service.status(count.get(), stockCount.get(), requestProduct.get(), needUpdate.get());
        status.set(countStatus.status);
        color = countStatus.color;
    }
}
