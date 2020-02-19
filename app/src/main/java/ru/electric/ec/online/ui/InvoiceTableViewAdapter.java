package ru.electric.ec.online.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.electric.ec.online.App;
import ru.electric.ec.online.R;
import ru.electric.ec.online.databinding.InvoiceTableItemBinding;
import ru.electric.ec.online.models.Invoice;

public class InvoiceTableViewAdapter extends RecyclerView.Adapter<InvoiceTableItemViewHolder> {

    private List<Invoice> invoices;
    private InvoiceTableItemViewModel viewModel;

    public InvoiceTableViewAdapter() {
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
    public InvoiceTableItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewModel = new InvoiceTableItemViewModel();
        viewModel.parent.invoices.clear();
        viewModel.parent.invoices.addAll(invoices);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        InvoiceTableItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.invoice_table_item, parent, false);
        binding.setViewModel(viewModel);
        return new InvoiceTableItemViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(InvoiceTableItemViewHolder holder, int position) {
        holder.bind(invoices.get(position), position);
    }

    @Override
    public int getItemCount() {
        return invoices.size();
    }

}

