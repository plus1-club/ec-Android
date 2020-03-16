package ru.electric.ec.online.ui.search;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import ru.electric.ec.online.databinding.SearchItemGroupBinding;
import ru.electric.ec.online.models.Request;

class SearchItemGroupViewHolder extends RecyclerView.ViewHolder {

    private SearchItemGroupBinding binding;
    private SearchItemViewModel viewModel;

    SearchItemGroupViewHolder(View view) {
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
        viewModel.variantsCount.set(request.variantsCount);
        viewModel.updateStatus();
    }
}

