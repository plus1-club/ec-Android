package club.plus1.ec_electric.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import club.plus1.ec_electric.R;

public class RequestTableAdapter  extends RecyclerView.Adapter<RequestTableAdapter.RequestViewHolder>{

    private String[] dataset;

    static class RequestViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView textView;
        RequestViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.text_screen_name);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RequestTableAdapter(String[] myDataset) {
        dataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public RequestTableAdapter.RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.invoice_table, parent, false);
        return new RequestViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RequestViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(dataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.length;
    }
}
