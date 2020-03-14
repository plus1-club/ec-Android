package ru.electric.ec.online.ui.basket;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import ru.electric.ec.online.databinding.BasketItemBinding;
import ru.electric.ec.online.models.Request;

class BasketItemViewHolder extends RecyclerView.ViewHolder {

    private BasketItemBinding binding;
    private BasketItemViewModel viewModel;

    BasketItemViewHolder(View view) {
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
        viewModel.price.set(request.price);
        viewModel.sum.set(request.sum);
        viewModel.check.set(request.check);
        viewModel.updateStatus();
    }
}