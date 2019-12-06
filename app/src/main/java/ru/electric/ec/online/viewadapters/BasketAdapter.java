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
import ru.electric.ec.online.databinding.BasketItemBinding;
import ru.electric.ec.online.domains.Request;
import ru.electric.ec.online.viewmodels.BasketItemViewModel;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.RequestsBasketViewHolder> {

    private List<Request> requests;
    private BasketItemViewModel viewModel;

    public BasketAdapter() {
        requests = new ArrayList<>();
    }

    public void setItems(Collection<Request> collection) {
        requests.addAll(collection);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BasketAdapter.RequestsBasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewModel = new BasketItemViewModel();
        viewModel.parent.requests.clear();
        viewModel.parent.requests.addAll(requests);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        BasketItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.basket_item, parent, false);
        binding.setViewModel(viewModel);
        return new BasketAdapter.RequestsBasketViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RequestsBasketViewHolder holder, int position) {
        holder.bind(requests.get(position), position);
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    static class RequestsBasketViewHolder extends RecyclerView.ViewHolder {

        private BasketItemBinding binding;
        private BasketItemViewModel viewModel;

        RequestsBasketViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
            viewModel = Objects.requireNonNull(binding).getViewModel();
        }

        void bind(Request request, int position) {
            viewModel.position.set(position);
            viewModel.product.set(request.product);
            viewModel.count.set(request.requestCount);
            viewModel.stockCount.set(request.stockCount);
            viewModel.multiplicity.set(request.multiplicity);
            viewModel.unit.set(request.unit);
            viewModel.price.set(request.price);
            viewModel.sum.set(request.sum);
            viewModel.check.set(true);
            viewModel.updateStatus();
        }
    }
}
