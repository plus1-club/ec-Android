package ru.electric.ec.online.ui.search;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import ru.electric.ec.online.databinding.SearchItemCheckboxBinding;
import ru.electric.ec.online.models.Request;

class SearchItemCheckboxViewHolder extends RecyclerView.ViewHolder {

    private SearchItemCheckboxBinding binding;
    private SearchItemViewModel viewModel;

    SearchItemCheckboxViewHolder(View view) {
        super(view);
        binding = DataBindingUtil.bind(view);
        viewModel = Objects.requireNonNull(binding).getViewModel();
    }

    void bind(Request request, int position) {
        viewModel.position.set(position);
        viewModel.product.set(request.product);
        viewModel.requestProduct.set(request.requestProduct);
        viewModel.count.set(request.requestCount);
        viewModel.stockCount.set(request.stockCount);
        viewModel.multiplicity.set(request.multiplicity);
        viewModel.unit.set(request.unit);
        viewModel.check.set(request.check);
        viewModel.variantsCount.set(request.variantsCount);
        viewModel.updateStatus();
    }
}

