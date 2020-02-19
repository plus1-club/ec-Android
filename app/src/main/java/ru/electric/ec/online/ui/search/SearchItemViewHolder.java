package ru.electric.ec.online.ui.search;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import ru.electric.ec.online.databinding.SearchItemBinding;
import ru.electric.ec.online.models.Request;

class SearchItemViewHolder extends RecyclerView.ViewHolder {

    private SearchItemBinding binding;
    private SearchItemViewModel viewModel;

    SearchItemViewHolder(View view) {
        super(view);
        binding = DataBindingUtil.bind(view);
        viewModel = Objects.requireNonNull(binding).getViewModel();
    }

    void bind(Request request, int position) {
        viewModel.position.set(position);
        viewModel.product.set(request.product);
        viewModel.count.set(request.requestCount);
        viewModel.stockCount.set(request.stockCount);
        viewModel.multiplicity.set(request.multiplicity);
        viewModel.unit.set(request.unit);
        viewModel.check.set(false);
        viewModel.updateStatus();
    }
}

