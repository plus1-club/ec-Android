package club.plus1.ec_electric.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import club.plus1.ec_electric.R;
import club.plus1.ec_electric.model.Request;

public class RequestBasketAdapter extends RecyclerView.Adapter<RequestBasketAdapter.RequestBasketHolder>{

    private List<Request> requests;

    // Provide a suitable constructor (depends on the kind of dataset)
    public RequestBasketAdapter() {
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
    public RequestBasketAdapter.RequestBasketHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_basket_item, parent, false);
        return new RequestBasketHolder(view);
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
        private  TextView textPrice;
        private  TextView textSum;

        RequestBasketHolder(View v) {
            super(v);
            textProduct = v.findViewById(R.id.text_product);
            editCount = v.findViewById(R.id.edit_count);
            textPrice = v.findViewById(R.id.text_price);
            textSum = v.findViewById(R.id.text_sum);
        }

        void bind(Request request){
            textProduct.setText(request.product);
            editCount.setText(String.format(Locale.getDefault(),"%d", request.count));
            textPrice.setText(String.format(Locale.getDefault(),"Цена: %a", request.price));
            textSum.setText(String.format(Locale.getDefault(),"Сумма: %a", request.sum));
        }
    }
}
