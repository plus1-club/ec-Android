package ru.electric.ec.online.ui.search;

import android.app.AlertDialog;
import android.content.Context;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.common.App;
import ru.electric.ec.online.common.Service;
import ru.electric.ec.online.databinding.SearchBinding;
import ru.electric.ec.online.models.Basket;
import ru.electric.ec.online.models.Search;
import ru.electric.ec.online.server.ServerData;
import ru.electric.ec.online.server.ServerRouter;
import ru.electric.ec.online.ui.ViewRouter;
import ru.electric.ec.online.ui.basket.BasketViewModel;

public class SearchViewModel {

    public ObservableField<String> product;
    public ObservableInt count;
    public ObservableInt productColumn;
    public ObservableInt countColumn;
    public ObservableBoolean isFullSearch;
    public ObservableField<String> excel;
    ObservableBoolean isExcel;

    public ObservableField<String> title;
    public ObservableList<Search> search;
    public ObservableField<SearchViewAdapter> adapter;
    public ObservableField<SearchBinding> binding;

    private static SearchViewModel mInstance;    // Ссылка для биндинга с View

    private SearchViewModel() {
        product = new ObservableField<>();
        count = new ObservableInt();
        count.set(1);
        productColumn = new ObservableInt();
        productColumn.set(1);
        countColumn = new ObservableInt();
        countColumn.set(2);
        isFullSearch = new ObservableBoolean();
        excel = new ObservableField<>();
        isExcel = new ObservableBoolean();

        isFullSearch.set(true);

        title = new ObservableField<>();
        search = new ObservableArrayList<>();
        adapter = new ObservableField<>();
        binding = new ObservableField<>();
    }

    // Получение единственного экземпляра класса
    public static SearchViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new SearchViewModel();
        }
        return mInstance;
    }

    public void selectAllSearch(Context context){
        Objects.requireNonNull(binding.get()).swiperefresh.setRefreshing(true);
        for (Search item: search){
            if (item.stockCount != -3 && item.variantsCount == 1){
                item.check = true;
            }
        }
        adapter.set(new SearchViewAdapter());
        Objects.requireNonNull(adapter.get()).setItems(search);
        Objects.requireNonNull(binding.get()).list.setAdapter(adapter.get());
        Objects.requireNonNull(binding.get()).swiperefresh.setRefreshing(false);
    }

    public void selectNoneSearch(Context context){
        Objects.requireNonNull(binding.get()).swiperefresh.setRefreshing(true);
        for (Search item: search){
            if (item.variantsCount == 1){
                item.check = false;
            }
        }
        adapter.set(new SearchViewAdapter());
        Objects.requireNonNull(adapter.get()).setItems(search);
        Objects.requireNonNull(binding.get()).list.setAdapter(adapter.get());
        Objects.requireNonNull(binding.get()).swiperefresh.setRefreshing(false);
    }

    public void searchOk(Context context, ServerData body) {
        App.getModel().search.search.clear();
        if (ServerRouter.isSuccess(body)) {
            List<?> data = (List<?>) body.data;
            Map<String, Integer> variants = new HashMap<>();
            for (Object element : data) {
                @SuppressWarnings("unchecked")
                Map<String, String> el = (LinkedTreeMap<String, String>) element;
                Search request = new Search(
                        el.get("product"),
                        el.get("requestProduct"),
                        Service.getInt(el.get("requestCount")),
                        Service.getInt(el.get("stockCount")),
                        Service.getInt(el.get("multiplicity")),
                        el.get("unit"),
                        false,
                        false,
                        0,
                        0,
                        "",
                        "",
                        0);
                if ((request.multiplicity > 0) && (request.requestCount % request.multiplicity > 0)) {
                    request.requestCount += request.multiplicity - (request.requestCount % request.multiplicity);
                }
                if (variants.get(request.requestProduct) == null) {
                    variants.put(request.requestProduct, 1);
                    Search group = new Search(
                            "",
                            request.requestProduct,
                            request.requestCount,
                            0,
                            0,
                            "",
                            false,
                            false,
                            0,
                            SearchItemTypeInterface.GROUP_ITEM_TYPE,
                            "",
                            "",
                            0);
                    App.getModel().search.search.add(group);
                } else {
                    int variantCount = Objects.requireNonNull(variants.get(request.requestProduct));
                    variants.put(request.requestProduct, variantCount + 1);
                }
                if (request.stockCount == -3 || request.requestCount != 0){
                    App.getModel().search.search.add(request);
                }
            }

            for (Search request: App.getModel().search.search) {
                request.variantsCount = Objects.requireNonNull(variants.get(request.requestProduct));
                if (request.itemType > 0) {
                    continue;
                }
                if (request.stockCount == -3){
                    request.itemType = SearchItemTypeInterface.NOT_FOUND_ITEM_TYPE;
                } else if (request.variantsCount > 1){
                    request.itemType = SearchItemTypeInterface.RADIO_ITEM_TYPE;
                } else {
                    request.itemType = SearchItemTypeInterface.CHECKBOX_ITEM_TYPE;
                }
            }
        } else {
            if (!body.error.isEmpty()){
                if (!isExcel.get()) {
                    Search group = new Search(
                            "",
                            product.get(),
                            count.get(),
                            0,
                            0,
                            "",
                            false,
                            false,
                            0,
                            SearchItemTypeInterface.GROUP_ITEM_TYPE,
                            "",
                            "",
                            0);
                    App.getModel().search.search.add(group);
                }
                String productText = "";
                int stock;
                if (body.error.equals("data_is_not_found")){
                    productText = product.get();
                    stock = -3;
                } else {
                    stock = -4;
                }
                Search item = new Search(
                        "",
                        productText,
                        count.get(),
                        stock,
                        0,
                        "",
                        false,
                        false,
                        0,
                        SearchItemTypeInterface.NOT_FOUND_ITEM_TYPE,
                        body.message,
                        "",
                        0);
                App.getModel().search.search.add(item);
            }
            Objects.requireNonNull(binding.get()).swiperefresh.setRefreshing(false);
        }
        adapter.set(new SearchViewAdapter());
        Objects.requireNonNull(adapter.get()).setItems(search);
        Objects.requireNonNull(binding.get()).list.setAdapter(adapter.get());
        Objects.requireNonNull(binding.get()).swiperefresh.setRefreshing(false);
    }

    public void searchError(Context context, Throwable throwable) {
        ViewRouter.onError(context, throwable, "RequestActivity");
    }

    public void toBasket(final Context context){
        List<Basket> added = new ArrayList<>();
        int count = 0;
        Map<String, Boolean> variants = new HashMap<>();
        for (Search request: App.getModel().search.search) {
            if (request.itemType == SearchItemTypeInterface.GROUP_ITEM_TYPE){
                continue;
            }
            if (variants.get(request.requestProduct) == null && request.variantsCount > 1) {
                variants.put(request.requestProduct, request.check);
            } else if (request.check){
                variants.put(request.requestProduct, request.check);
            }
            if (request.check){
                count ++;
            }
        }
        boolean allChecked = true;
        for (Boolean checked: variants.values()) {
            if (!checked) {
                allChecked = false;
                break;
            }
        }

        if (count == 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle(title.get());
            alertDialog.setMessage(context.getString(R.string.text_product_not_select));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    (dialog, which) -> dialog.dismiss());
            alertDialog.show();
        } else if (!allChecked) {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle(title.get());
            alertDialog.setMessage(context.getString(R.string.text_variant_not_select));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    (dialog, which) -> dialog.dismiss());
            alertDialog.show();

        } else {
            for (Search request: App.getModel().search.search) {
                if (request.check){
                    added.add(Basket.searchToBasket(request));
                }
            }
            BasketViewModel basketViewModel = BasketViewModel.getInstance();
            basketViewModel.basket.addAll(added);
            ServerRouter.postBasket(context, basketViewModel, added);
            basketViewModel.total.set(0);
            basketViewModel.comment.set("");
        }
    }
}
