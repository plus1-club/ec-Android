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
import ru.electric.ec.online.databinding.RequestsSearchItemBinding;
import ru.electric.ec.online.domains.Request;
import ru.electric.ec.online.viewmodels.RequestSearchItemViewModel;

public class RequestsSearchAdapter extends RecyclerView.Adapter<RequestsSearchAdapter.RequestsTableViewHolder> {

    private List<Request> requests;
    private RequestSearchItemViewModel viewModel;

    public RequestsSearchAdapter() {
        requests = new ArrayList<>();
    }

    public void setItems(Collection<Request> collection) {
        requests.addAll(collection);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RequestsSearchAdapter.RequestsTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewModel = new RequestSearchItemViewModel();
        viewModel.parent.requests.clear();
        viewModel.parent.requests.addAll(requests);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RequestsSearchItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.requests_search_item, parent, false);
        binding.setViewModel(viewModel);
        return new RequestsTableViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RequestsTableViewHolder holder, int position) {
        holder.bind(requests.get(position), position);
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    static class RequestsTableViewHolder extends RecyclerView.ViewHolder {

        private RequestsSearchItemBinding binding;
        private RequestSearchItemViewModel viewModel;

        RequestsTableViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
            viewModel = Objects.requireNonNull(binding).getViewModel();
        }

        void bind(Request request, int position) {
            viewModel.position.set(position);
            viewModel.product.set(request.product);
            viewModel.count.set(request.requestCount);
            viewModel.stockCount.set(request.stockCount);
            viewModel.check.set(false);
            viewModel.updateStatus();
        }
    }
}
