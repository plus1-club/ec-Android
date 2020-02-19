package ru.electric.ec.online.ui;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.Service;
import ru.electric.ec.online.databinding.InvoiceDetailsItemBinding;
import ru.electric.ec.online.models.Detail;

class InvoiceDetailsItemViewHolder extends RecyclerView.ViewHolder {

    private InvoiceDetailsItemBinding binding;
    private InvoiceDetailsItemViewModel viewModel;

    InvoiceDetailsItemViewHolder(View view) {
        super(view);
        binding = DataBindingUtil.bind(view);
        viewModel = Objects.requireNonNull(binding).getViewModel();
    }

    void bind(Detail detail, int position) {
        viewModel.position.set(position);
        viewModel.product.set(detail.product);
        viewModel.count.set(detail.count);
        viewModel.unit.set(detail.unit);
        viewModel.price.set(detail.price);
        viewModel.sum.set(detail.sum);
        viewModel.available.set(detail.available);
        viewModel.delivery.set(detail.delivery);
        viewModel.updateColor();
        String status = viewModel.parent.status.get();
        viewModel.showAvailable.set(
                Service.isEqual(status, Service.getStr(R.string.status_unconfirmed)) ||

                        Service.isEqual(status, Service.getStr(R.string.status_reserved)) ||
                        Service.isEqual(status, Service.getStr(R.string.status_ordered)));
        viewModel.showDelivery.set(
                Service.isEqual(status, Service.getStr(R.string.status_reserved)) ||
                        Service.isEqual(status, Service.getStr(R.string.status_ordered)));
    }
}

