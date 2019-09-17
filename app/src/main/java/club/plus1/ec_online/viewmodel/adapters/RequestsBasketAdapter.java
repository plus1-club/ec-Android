package club.plus1.ec_online.viewmodel.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import club.plus1.ec_online.R;
import club.plus1.ec_online.databinding.RequestsBasketItemBinding;
import club.plus1.ec_online.model.Request;
import club.plus1.ec_online.viewmodel.RequestViewModel;

public class RequestsBasketAdapter extends RecyclerView.Adapter<RequestsBasketAdapter.RequestBasketHolder>{

    private List<Request> requests;
    private RequestViewModel viewModel;

    // Provide a suitable constructor (depends on the kind of dataset)
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

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public RequestsBasketAdapter.RequestBasketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewModel = RequestViewModel.getInstance();
        RequestsBasketItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.requests_basket_item, parent, false);
        binding.setViewModel(viewModel);
        return new RequestsBasketAdapter.RequestBasketHolder(binding.getRoot());
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RequestBasketHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.bind(requests.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return requests.size();
    }

    static class RequestBasketHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private  TextView textProduct;
        private  EditText editCount;
        private  TextView textSum;

        RequestBasketHolder(View v) {
            super(v);
            textProduct = v.findViewById(R.id.textProduct);
            editCount = v.findViewById(R.id.editCount);
            textSum = v.findViewById(R.id.editSum);
        }

        void bind(Request request){
            textProduct.setText(request.product);
            editCount.setText(String.format(Locale.getDefault(), "%d", request.count));
            textSum.setText(String.format(Locale.getDefault(),
                    "Цена: %1$.2f \u20BD\nСумма: %2$.2f \u20BD", request.price, request.sum));
        }
    }
}
