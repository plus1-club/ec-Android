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
import ru.electric.ec.online.databinding.SearchItemNotFoundBinding;
import ru.electric.ec.online.databinding.SearchItemRegularBinding;
import ru.electric.ec.online.models.Request;

public class SearchViewAdapter extends RecyclerView.Adapter {

    private List<Request> requests;
    private SearchItemViewModel viewModel;

    public SearchViewAdapter() {
        requests = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (requests.get(position).stockCount == -3){
            return SearchItemInterface.NOT_FOUND_TYPE;
        } else {
            return SearchItemInterface.REGULAR_TYPE;
        }
    }

    public void setItems(Collection<Request> collection) {
        requests.addAll(collection);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewModel = new SearchItemViewModel();
        viewModel.parent.search.clear();
        viewModel.parent.search.addAll(requests);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == SearchItemInterface.NOT_FOUND_TYPE)
        {
            SearchItemNotFoundBinding binding = DataBindingUtil.inflate(inflater, R.layout.search_item_not_found, parent, false);
            binding.setViewModel(viewModel);
            return new SearchItemNotFoundViewHolder(binding.getRoot());
        } else {
            SearchItemRegularBinding binding = DataBindingUtil.inflate(inflater, R.layout.search_item_regular, parent, false);
            binding.setViewModel(viewModel);
            return new SearchItemRegularViewHolder(binding.getRoot());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (this.getItemViewType(position) == SearchItemInterface.NOT_FOUND_TYPE){
            ((SearchItemNotFoundViewHolder) holder).bind(requests.get(position), position);
        } else {
            ((SearchItemRegularViewHolder) holder).bind(requests.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

}
