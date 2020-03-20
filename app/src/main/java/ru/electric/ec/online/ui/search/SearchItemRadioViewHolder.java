package ru.electric.ec.online.ui.search;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import ru.electric.ec.online.common.Service;
import ru.electric.ec.online.databinding.SearchItemRadioBinding;
import ru.electric.ec.online.models.Request;

class SearchItemRadioViewHolder extends RecyclerView.ViewHolder {

    private SearchItemRadioBinding binding;
    private SearchItemViewModel viewModel;

    SearchItemRadioViewHolder(View view) {
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
        viewModel.itemType.set(request.itemType);
        viewModel.colorName.set(request.colorName);
        viewModel.status.set(request.status);
        viewModel.color = request.color;
        viewModel.updateStatus();
        binding.radioMark.setButtonTintList(Service.getColorStateList(viewModel.color));
        binding.editCount.setBackgroundTintList(Service.getColorStateList(viewModel.color));
        binding.textUnit.setTextColor(Service.getColor(viewModel.color));
        binding.textStatus.setTextColor(Service.getColor(viewModel.color));
    }
}

