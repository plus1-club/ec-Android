package club.plus1.ec_online.viewmodel.adapters;

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
import java.util.Locale;
import java.util.Objects;

import club.plus1.ec_online.R;
import club.plus1.ec_online.databinding.RequestsTableItemBinding;
import club.plus1.ec_online.model.Request;
import club.plus1.ec_online.viewmodel.RequestItemViewModel;

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

    public void clearItems() {
        requests.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RequestsTableAdapter.RequestsTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewModel = new RequestItemViewModel();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RequestsTableItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.requests_table_item, parent, false);
        binding.setViewModel(viewModel);
        return new RequestsTableViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RequestsTableViewHolder holder, int position) {
        holder.bind(requests.get(position));
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

        void bind(Request request){
            Context context = binding.getRoot().getContext();
            String status;
            int color;
            if (request.stockCount == 0) {
                status = context.getString(R.string.text_status_red);
                color = R.color.red;
            } else if (request.stockCount > request.requestCount) {
                status = context.getString(R.string.text_status_green);
                color = R.color.green;
            } else {
                status = context.getString(R.string.text_status_yellow, request.stockCount, request.requestCount);
                color = R.color.yellow;
            }

            viewModel.product.set(request.product);
            viewModel.count.set(String.format(Locale.getDefault(),
                    context.getString(R.string.text_count), request.requestCount));
            viewModel.status.set(status);
            viewModel.color.set(color);
        }
    }
}
