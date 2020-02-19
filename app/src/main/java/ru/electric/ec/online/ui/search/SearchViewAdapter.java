package ru.electric.ec.online.ui.search;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.electric.ec.online.R;
import ru.electric.ec.online.databinding.SearchItemBinding;
import ru.electric.ec.online.models.Request;

public class SearchViewAdapter extends RecyclerView.Adapter<SearchItemViewHolder> {

    private List<Request> requests;
    private SearchItemViewModel viewModel;

    SearchViewAdapter() {
        requests = new ArrayList<>();
    }

    void setItems(Collection<Request> collection) {
        requests.addAll(collection);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewModel = SearchItemViewModel.getInstance();
        viewModel.parent.search.clear();
        viewModel.parent.search.addAll(requests);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SearchItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.search_item, parent, false);
        binding.setViewModel(viewModel);
        return new SearchItemViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(SearchItemViewHolder holder, int position) {
        holder.bind(requests.get(position), position);
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }
}
