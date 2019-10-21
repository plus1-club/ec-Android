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
import club.plus1.ec_online.databinding.RequestsBasketItemBinding;
import club.plus1.ec_online.domain.Request;
import club.plus1.ec_online.viewmodel.viewmodels.RequestItemViewModel;

public class RequestsBasketAdapter extends RecyclerView.Adapter<RequestsBasketAdapter.RequestsBasketViewHolder> {

    private List<Request> requests;
    private RequestItemViewModel viewModel;

    public RequestsBasketAdapter() {
        requests = new ArrayList<>();
    }

    public void setItems(Collection<Request> collection) {
        requests.addAll(collection);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RequestsBasketAdapter.RequestsBasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewModel = new RequestItemViewModel();
        viewModel.parent.requests.clear();
        viewModel.parent.requests.addAll(requests);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RequestsBasketItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.requests_basket_item, parent, false);
        binding.setViewModel(viewModel);
        return new RequestsBasketAdapter.RequestsBasketViewHolder(binding.getRoot());
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

        private RequestsBasketItemBinding binding;
        private RequestItemViewModel viewModel;

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
            viewModel.price.set(request.price);
            viewModel.sum.set(request.sum);
            viewModel.mark.set(true);
            viewModel.updateStatus();
        }
    }
}
