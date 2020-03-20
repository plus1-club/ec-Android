package ru.electric.ec.online.ui.basket;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.electric.ec.online.R;
import ru.electric.ec.online.databinding.BasketItemBinding;
import ru.electric.ec.online.models.Basket;

public class BasketViewAdapter extends RecyclerView.Adapter<BasketItemViewHolder> {

    private List<Basket> requests;
    private BasketItemViewModel viewModel;

    BasketViewAdapter() {
        requests = new ArrayList<>();
    }

    public void setItems(Collection<Basket> collection) {
        requests.clear();
        requests.addAll(collection);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BasketItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewModel = new BasketItemViewModel();
        viewModel.parent.basket.clear();
        viewModel.parent.basket.addAll(requests);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        BasketItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.basket_item, parent, false);
        binding.setViewModel(viewModel);
        return new BasketItemViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(BasketItemViewHolder holder, int position) {
        holder.bind(requests.get(position), position);
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }


}
