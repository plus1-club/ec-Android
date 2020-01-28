package ru.electric.ec.online.ui.invoice;

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
import ru.electric.ec.online.databinding.InvoiceDetailsItemBinding;
import ru.electric.ec.online.models.Detail;
import ru.electric.ec.online.models.Invoice;
import ru.electric.ec.online.ui.info.InfoViewModel;

public class InvoiceDetailsAdapter extends RecyclerView.Adapter<InvoiceDetailsAdapter.InvoiceDetailsViewHolder> {

    private List<Detail> details;
    private InvoiceDetailsItemViewModel viewModel;

    public InvoiceDetailsAdapter() {
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
    public InvoiceDetailsAdapter.InvoiceDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewModel = new InvoiceDetailsItemViewModel();
        viewModel.parent.details.clear();
        viewModel.parent.details.addAll(details);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        InvoiceDetailsItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.invoice_details_item, parent, false);
        binding.setViewModel(viewModel);
        return new InvoiceDetailsAdapter.InvoiceDetailsViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(InvoiceDetailsAdapter.InvoiceDetailsViewHolder holder, int position) {
        holder.bind(details.get(position), position);
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    static class InvoiceDetailsViewHolder extends RecyclerView.ViewHolder {

        private InvoiceDetailsItemBinding binding;
        private InvoiceDetailsItemViewModel viewModel;

        InvoiceDetailsViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
            viewModel = Objects.requireNonNull(binding).getViewModel();
        }

        void bind(Detail detail, int position) {
            viewModel.position.set(position);
            viewModel.product.set(detail.product);
            viewModel.count.set(detail.count);
            viewModel.price.set(detail.price);
            viewModel.sum.set(detail.sum);
            viewModel.available.set(detail.available);
            viewModel.delivery.set(detail.delivery);

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

}
