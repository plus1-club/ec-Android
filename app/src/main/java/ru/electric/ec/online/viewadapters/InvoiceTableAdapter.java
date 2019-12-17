package ru.electric.ec.online.viewadapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.databinding.InvoiceTableItemBinding;
import ru.electric.ec.online.models.Invoice;
import ru.electric.ec.online.viewmodels.InvoiceTableItemViewModel;
import ru.electric.ec.online.viewmodels.Service;

public class InvoiceTableAdapter extends RecyclerView.Adapter<InvoiceTableAdapter.InvoiceTableViewHolder> {

    private List<Invoice> invoices;
    private InvoiceTableItemViewModel viewModel;

    public InvoiceTableAdapter() {
        invoices = new ArrayList<>();
    }

    public void setItems(Collection<Invoice> collection) {
        invoices.addAll(collection);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InvoiceTableAdapter.InvoiceTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewModel = new InvoiceTableItemViewModel();
        viewModel.parent.invoices.clear();
        viewModel.parent.invoices.addAll(invoices);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        InvoiceTableItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.invoice_table_item, parent, false);
        binding.setViewModel(viewModel);
        return new InvoiceTableAdapter.InvoiceTableViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(InvoiceTableAdapter.InvoiceTableViewHolder holder, int position) {
        holder.bind(invoices.get(position), position);
    }

    @Override
    public int getItemCount() {
        return invoices.size();
    }

    static class InvoiceTableViewHolder extends RecyclerView.ViewHolder {

        private InvoiceTableItemBinding binding;
        private InvoiceTableItemViewModel viewModel;

        InvoiceTableViewHolder(View view) {
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
}

