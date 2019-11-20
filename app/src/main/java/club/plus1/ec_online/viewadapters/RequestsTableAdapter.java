package club.plus1.ec_online.viewadapters;

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
import club.plus1.ec_online.databinding.RequestsTableItemBinding;
import club.plus1.ec_online.domains.Request;
import club.plus1.ec_online.viewmodels.RequestItemViewModel;

public class RequestsTableAdapter extends RecyclerView.Adapter<RequestsTableAdapter.RequestsTableViewHolder> {

    private List<Request> requests;
    private RequestItemViewModel viewModel;

    public RequestsTableAdapter() {
        requests = new ArrayList<>();
    }

    public void setItems(Collection<Request> collection) {
        requests.addAll(collection);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RequestsTableAdapter.RequestsTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewModel = new RequestItemViewModel();
        viewModel.parent.requests.clear();
        viewModel.parent.requests.addAll(requests);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RequestsTableItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.requests_table_item, parent, false);
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

        private RequestsTableItemBinding binding;
        private RequestItemViewModel viewModel;

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
            viewModel.mark.set(false);
            viewModel.updateStatus();
        }
    }
}
