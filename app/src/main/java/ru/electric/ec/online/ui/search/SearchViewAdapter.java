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
import ru.electric.ec.online.databinding.SearchItemCheckboxBinding;
import ru.electric.ec.online.databinding.SearchItemGroupBinding;
import ru.electric.ec.online.databinding.SearchItemNotFoundBinding;
import ru.electric.ec.online.databinding.SearchItemRadioBinding;
import ru.electric.ec.online.models.Search;

public class SearchViewAdapter extends RecyclerView.Adapter {

    private List<Search> requests;
    private SearchItemViewModel viewModel;

    SearchViewAdapter() {
        requests = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return requests.get(position).itemType;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setItems(Collection<Search> collection) {
        requests.clear();
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
        if (viewType == SearchItemTypeInterface.NOT_FOUND_ITEM_TYPE){
            SearchItemNotFoundBinding binding = DataBindingUtil.inflate(inflater, R.layout.search_item_not_found, parent, false);
            binding.setViewModel(viewModel);
            return new SearchItemNotFoundViewHolder(binding.getRoot());
        } else if (viewType == SearchItemTypeInterface.GROUP_ITEM_TYPE){
            SearchItemGroupBinding binding = DataBindingUtil.inflate(inflater, R.layout.search_item_group, parent, false);
            binding.setViewModel(viewModel);
            return new SearchItemGroupViewHolder(binding.getRoot());
        } else if (viewType == SearchItemTypeInterface.RADIO_ITEM_TYPE){
            SearchItemRadioBinding binding = DataBindingUtil.inflate(inflater, R.layout.search_item_radio, parent, false);
            binding.setViewModel(viewModel);
            return new SearchItemRadioViewHolder(binding.getRoot());
        } else {
            SearchItemCheckboxBinding binding = DataBindingUtil.inflate(inflater, R.layout.search_item_checkbox, parent, false);
            binding.setViewModel(viewModel);
            return new SearchItemCheckboxViewHolder(binding.getRoot());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (this.getItemViewType(position) == SearchItemTypeInterface.NOT_FOUND_ITEM_TYPE){
            ((SearchItemNotFoundViewHolder) holder).bind(requests.get(position), position);
        } else if (this.getItemViewType(position) == SearchItemTypeInterface.GROUP_ITEM_TYPE){
            ((SearchItemGroupViewHolder) holder).bind(requests.get(position), position);
        } else if (this.getItemViewType(position) == SearchItemTypeInterface.RADIO_ITEM_TYPE){
            ((SearchItemRadioViewHolder) holder).bind(requests.get(position), position);
        } else {
            ((SearchItemCheckboxViewHolder) holder).bind(requests.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

}
