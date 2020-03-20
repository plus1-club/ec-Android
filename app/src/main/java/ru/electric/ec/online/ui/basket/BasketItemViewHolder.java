package ru.electric.ec.online.ui.basket;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import ru.electric.ec.online.common.Service;
import ru.electric.ec.online.databinding.BasketItemBinding;
import ru.electric.ec.online.models.Basket;

class BasketItemViewHolder extends RecyclerView.ViewHolder {

    private BasketItemBinding binding;
    private BasketItemViewModel viewModel;

    BasketItemViewHolder(View view) {
        super(view);
        binding = DataBindingUtil.bind(view);
        viewModel = Objects.requireNonNull(binding).getViewModel();
    }

    void bind(Basket request, int position) {
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
        viewModel.colorName.set(request.colorName);
        viewModel.status.set(request.status);
        viewModel.color = request.color;
        viewModel.updateStatus();
        binding.checkMark.setButtonTintList(Service.getColorStateList(viewModel.color));
        binding.editCount.setBackgroundTintList(Service.getColorStateList(viewModel.color));
        binding.textUnit.setTextColor(Service.getColor(viewModel.color));
        binding.textStatus.setTextColor(Service.getColor(viewModel.color));
    }
}