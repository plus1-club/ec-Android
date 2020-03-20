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

import ru.electric.ec.online.R;
import ru.electric.ec.online.common.App;
import ru.electric.ec.online.databinding.InvoiceItemBinding;
import ru.electric.ec.online.databinding.InvoiceItemMessageBinding;
import ru.electric.ec.online.models.Info;
import ru.electric.ec.online.models.Invoice;
import ru.electric.ec.online.ui.ViewRouter;

public class InvoiceViewAdapter extends RecyclerView.Adapter {

    private List<Invoice> invoices;
    private InvoiceItemViewModel viewModel;

    InvoiceViewAdapter() {
        invoices = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return invoices.get(position).itemType;
    }

    void updateAdapter(InvoiceViewModel invoice, Context context){
        this.setItems(invoice.invoices);
        try{
            ((InvoiceActivity)context).binding.list.setAdapter(this);
        } catch (Exception e){
            ViewRouter.openInfo(context, new Info(true, false, "Ошибка вывода списка"));
        }
    }

    void setItems(Collection<Invoice> collection) {
        try{
            invoices.addAll(collection);
            notifyDataSetChanged();
        } catch (Exception e){
            ViewRouter.openInfo(App.getAppContext(),  new Info(true, false, "Ошибка добавления значений"));
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewModel = new InvoiceItemViewModel();
        viewModel.parent.invoices.clear();
        viewModel.parent.invoices.addAll(invoices);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == InvoiceItemTypeInterface.MESSAGE_ITEM_TYPE) {
            InvoiceItemMessageBinding binding = DataBindingUtil.inflate(inflater, R.layout.invoice_item_message, parent, false);
            binding.setViewModel(viewModel);
            return new InvoiceItemMessageViewHolder(binding.getRoot());

        } else {
            InvoiceItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.invoice_item, parent, false);
            binding.setViewModel(viewModel);
            return new InvoiceItemViewHolder(binding.getRoot());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (this.getItemViewType(position) == InvoiceItemTypeInterface.MESSAGE_ITEM_TYPE){
            ((InvoiceItemMessageViewHolder) holder).bind(invoices.get(position), position);
        } else {
            ((InvoiceItemViewHolder) holder).bind(invoices.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        return invoices.size();
    }

}

