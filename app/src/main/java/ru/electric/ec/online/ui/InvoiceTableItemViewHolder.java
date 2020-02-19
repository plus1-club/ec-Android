package ru.electric.ec.online.ui;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.Service;
import ru.electric.ec.online.databinding.InvoiceTableItemBinding;
import ru.electric.ec.online.models.Invoice;

class InvoiceTableItemViewHolder extends RecyclerView.ViewHolder {

    private InvoiceTableItemBinding binding;
    private InvoiceTableItemViewModel viewModel;

    InvoiceTableItemViewHolder(View view) {
        super(view);
        binding = DataBindingUtil.bind(view);
        viewModel = Objects.requireNonNull(binding).getViewModel();
    }

    void bind(Invoice invoice, int position) {
        viewModel.position.set(position);
        viewModel.number.set(invoice.number);
        viewModel.date.set(invoice.date);
        viewModel.sum.set(invoice.sum);
        viewModel.status.set(invoice.status);
        viewModel.waybill.set(invoice.waybill);
        viewModel.showWaybill.set(
                Service.isEqual(viewModel.status.get(), Service.getStr(R.string.status_shipped)));
        viewModel.showInvoiceButton.set(
                Service.isEqual(viewModel.status.get(), Service.getStr(R.string.status_reserved)) ||
                        Service.isEqual(viewModel.status.get(), Service.getStr(R.string.status_ordered)));
        viewModel.details.clear();
        viewModel.details.addAll(invoice.details);
    }
}
