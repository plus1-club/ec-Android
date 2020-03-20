package ru.electric.ec.online.ui.invoice;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.common.Service;
import ru.electric.ec.online.databinding.InvoiceItemMessageBinding;
import ru.electric.ec.online.models.Invoice;

class InvoiceItemMessageViewHolder extends RecyclerView.ViewHolder {

    private InvoiceItemMessageBinding binding;
    private InvoiceItemViewModel viewModel;

    InvoiceItemMessageViewHolder(View view) {
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
        viewModel.message.set(invoice.message);
        viewModel.itemType.set(invoice.itemType);
        viewModel.showWaybill.set(
                Service.isEqual(viewModel.status.get(), Service.getStr(R.string.status_shipped)));
        viewModel.showInvoiceButton.set(
                Service.isEqual(viewModel.status.get(), Service.getStr(R.string.status_reserved)) ||
                        Service.isEqual(viewModel.status.get(), Service.getStr(R.string.status_ordered)));
        viewModel.details.clear();
        viewModel.details.addAll(invoice.details);
    }
}
