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
import club.plus1.ec_online.databinding.RequestsBasketItemBinding;
import club.plus1.ec_online.model.Request;
import club.plus1.ec_online.viewmodel.RequestItemViewModel;

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

    public void clearItems() {
        requests.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RequestsBasketAdapter.RequestsBasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewModel = new RequestItemViewModel();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RequestsBasketItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.requests_basket_item, parent, false);
        binding.setViewModel(viewModel);
        return new RequestsBasketAdapter.RequestsBasketViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RequestsBasketViewHolder holder, int position) {
        holder.bind(requests.get(position));
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
            viewModel.price.set(String.format(Locale.getDefault(),
                    context.getString(R.string.text_price), request.price));
            viewModel.sum.set(String.format(Locale.getDefault(),
                    context.getString(R.string.text_sum), request.sum));
            viewModel.status.set(status);
            viewModel.color.set(color);
        }
    }
}
