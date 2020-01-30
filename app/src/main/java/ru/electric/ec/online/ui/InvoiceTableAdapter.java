package ru.electric.ec.online.ui;

import android.content.Context;
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

import ru.electric.ec.online.App;
import ru.electric.ec.online.R;
import ru.electric.ec.online.Service;
import ru.electric.ec.online.databinding.InvoiceTableItemBinding;
import ru.electric.ec.online.models.Invoice;

public class InvoiceTableAdapter extends RecyclerView.Adapter<InvoiceTableAdapter.InvoiceTableViewHolder> {

    private List<Invoice> invoices;
    private InvoiceTableItemViewModel viewModel;

    public InvoiceTableAdapter() {
        invoices = new ArrayList<>();
    }

    public void updateAdapter(InvoiceTableViewModel invoice, Context context){
        this.setItems(invoice.invoices);
        try{
            ((InvoiceTableActivity)context).binding.list.setAdapter(this);
        } catch (Exception e){
            InfoViewModel.log(context, true, false, "Ошибка вывода списка");
        }
    }

    void setItems(Collection<Invoice> collection) {
        try{
            invoices.addAll(collection);
            notifyDataSetChanged();
        } catch (Exception e){
            InfoViewModel.log(App.getAppContext(), true, false, "Ошибка добавления значений");
        }
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

