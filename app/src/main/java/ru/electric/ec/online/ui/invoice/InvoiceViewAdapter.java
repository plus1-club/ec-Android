package ru.electric.ec.online.ui.invoice;

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
import ru.electric.ec.online.databinding.InvoiceItemBinding;
import ru.electric.ec.online.models.Invoice;
import ru.electric.ec.online.ui.info.InfoViewModel;

public class InvoiceViewAdapter extends RecyclerView.Adapter<InvoiceItemViewHolder> {

    private List<Invoice> invoices;
    private InvoiceItemViewModel viewModel;

    public InvoiceViewAdapter() {
        invoices = new ArrayList<>();
    }

    public void updateAdapter(InvoiceViewModel invoice, Context context){
        this.setItems(invoice.invoices);
        try{
            ((InvoiceActivity)context).binding.list.setAdapter(this);
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
    public InvoiceItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewModel = new InvoiceItemViewModel();
        viewModel.parent.invoices.clear();
        viewModel.parent.invoices.addAll(invoices);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        InvoiceItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.invoice_item, parent, false);
        binding.setViewModel(viewModel);
        return new InvoiceItemViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(InvoiceItemViewHolder holder, int position) {
        holder.bind(invoices.get(position), position);
    }

    @Override
    public int getItemCount() {
        return invoices.size();
    }

}

