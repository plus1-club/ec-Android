package ru.electric.ec.online.ui.search;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import java.util.Objects;

import ru.electric.ec.online.common.Service;
import ru.electric.ec.online.models.Search;
import ru.electric.ec.online.server.ServerRouter;

public class SearchItemViewModel {

    public SearchViewModel parent;

    public ObservableInt position;
    public ObservableField<String> product;
    public ObservableField<String> requestProduct;
    public ObservableInt count;
    public ObservableInt stockCount;
    ObservableInt multiplicity;
    public ObservableField<String> unit;
    public ObservableBoolean check;
    ObservableInt variantsCount;
    public ObservableInt itemType;
    public ObservableField<String> status;
    public ObservableField<String> colorName;
    public int color;

    private ObservableBoolean needUpdate;

    SearchItemViewModel() {
        position = new ObservableInt();
        product = new ObservableField<>();
        requestProduct = new ObservableField<>();
        count = new ObservableInt();
        stockCount = new ObservableInt();
        multiplicity = new ObservableInt();
        unit = new ObservableField<>();
        check = new ObservableBoolean();
        variantsCount = new ObservableInt();
        itemType = new ObservableInt();
        status = new ObservableField<>();
        color = 0;
        colorName = new ObservableField<>();
        needUpdate = new ObservableBoolean();
        needUpdate.set(false);
        parent = SearchViewModel.getInstance();
    }

    public void onFlagClick(View view) {
        this.check.set(((CheckBox) view).isChecked());
        Search request = new Search(product.get(), requestProduct.get(),
                count.get(), stockCount.get(),
                multiplicity.get(), unit.get(),
                check.get(), needUpdate.get(), variantsCount.get(), itemType.get(),
                status.get(), colorName.get(), color);
        Service.status(request);
        parent.search.set(position.get(), request);
    }

    public void onRadioClick(View view) {
        check.set(true);
        Search request = parent.search.get(position.get());
        request.check = true;
        Service.status(request);
        parent.search.set(position.get(), request);
        SearchViewAdapter adapter = parent.adapter.get();
        Objects.requireNonNull(adapter).setItems(parent.search);
        for (int pos = 0; pos < parent.search.size()-1; pos++) {
            Search item = parent.search.get(pos);
            Service.status(item);
            if ((item.requestProduct.equals(request.requestProduct))
                    && !item.product.equals(request.product)
                    && item.check){
                item.check = false;
            }
            Objects.requireNonNull(adapter).notifyItemChanged(pos);
        }
        parent.adapter.set(adapter);
    }

    public void onUpdateStatus(Context context){
        if (needUpdate.get()){
            if (parent.isExcel.get()){
                ServerRouter.fromExcel(context, parent);
            } else {
                ServerRouter.byCode(context, parent);
            }
            ((SearchActivity)context).refreshSearch();
            needUpdate.set(false);
            updateStatus();
        }
    }

    void updateStatus() {
        Search request = parent.search.get(position.get());
        Service.status(request);
        product.set(request.product);
        requestProduct.set(request.requestProduct);
        count.set(request.requestCount);
        stockCount.set(request.stockCount);
        multiplicity.set(request.multiplicity);
        unit.set(request.unit);
        check.set(request.check);
        needUpdate.set(request.needUpdate);
        variantsCount.set(request.variantsCount);
        itemType.set(request.itemType);
        status.set(request.status);
        colorName.set(request.colorName);
        color = request.color;
    }
}
