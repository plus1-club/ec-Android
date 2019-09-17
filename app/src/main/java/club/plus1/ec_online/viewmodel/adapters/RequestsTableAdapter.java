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
import club.plus1.ec_online.databinding.RequestsTableItemBinding;
import club.plus1.ec_online.model.Request;
import club.plus1.ec_online.viewmodel.RequestViewModel;

public class RequestsTableAdapter extends RecyclerView.Adapter<RequestsTableAdapter.RequestViewHolder>{

    private List<Request> requests;
    private RequestViewModel viewModel;

    // Provide a suitable constructor (depends on the kind of dataset)
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

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public RequestsTableAdapter.RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewModel = RequestViewModel.getInstance();
        RequestsTableItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.requests_table_item, parent, false);
        binding.setViewModel(viewModel);
        return new RequestViewHolder(binding.getRoot());
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RequestViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.bind(requests.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return requests.size();
    }

    static class RequestViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private  TextView textProduct;
        private  EditText editCount;

        RequestViewHolder(View v) {
            super(v);
            textProduct = v.findViewById(R.id.textProduct);
            editCount = v.findViewById(R.id.editCount);
        }

        void bind(Request request){
            textProduct.setText(request.product);
            editCount.setText(String.format(Locale.getDefault(),"%d", request.count));
        }
    }
}
