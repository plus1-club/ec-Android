package club.plus1.ec_online.viewmodel.adapters;

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

import club.plus1.ec_online.R;
import club.plus1.ec_online.databinding.InvoiceItemBinding;
import club.plus1.ec_online.model.Invoice;
import club.plus1.ec_online.viewmodel.InvoiceItemViewModel;

public class InvoiceTableAdapter extends RecyclerView.Adapter<InvoiceTableAdapter.InvoiceTableViewHolder> {

    private List<Invoice> invoices;
    private InvoiceItemViewModel viewModel;

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
        viewModel = new InvoiceItemViewModel();
        viewModel.parent.invoices.clear();
        viewModel.parent.invoices.addAll(invoices);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        InvoiceItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.invoice_item, parent, false);
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

        private InvoiceItemBinding binding;
        private InvoiceItemViewModel viewModel;

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
        }
    }
}

