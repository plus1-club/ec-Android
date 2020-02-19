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
import ru.electric.ec.online.databinding.InvoiceDetailsItemBinding;
import ru.electric.ec.online.models.Detail;
import ru.electric.ec.online.models.Invoice;

public class InvoiceDetailsViewAdapter extends RecyclerView.Adapter<InvoiceDetailsItemViewHolder> {

    private List<Detail> details;
    private InvoiceDetailsItemViewModel viewModel;

    public InvoiceDetailsViewAdapter() {
        details = new ArrayList<>();
    }

    public void updateAdapter(Invoice invoice, Context context){
        this.setItems(invoice.details);
        try{
            ((InvoiceDetailsActivity)context).binding.list.setAdapter(this);
        } catch (Exception e){
            InfoViewModel.log(context, true, false, "Ошибка вывода списка");
        }
    }

    void setItems(Collection<Detail> collection) {
        try{
            details.addAll(collection);
            notifyDataSetChanged();
        } catch (Exception e){
            InfoViewModel.log(App.getAppContext(), true, false, "Ошибка добавления значений");
        }
    }

    @NonNull
    @Override
    public InvoiceDetailsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewModel = new InvoiceDetailsItemViewModel();
        viewModel.parent.details.clear();
        viewModel.parent.details.addAll(details);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        InvoiceDetailsItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.invoice_details_item, parent, false);
        binding.setViewModel(viewModel);
        return new InvoiceDetailsItemViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(InvoiceDetailsItemViewHolder holder, int position) {
        holder.bind(details.get(position), position);
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

}
