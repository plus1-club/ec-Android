package ru.electric.ec.online.ui.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.electric.ec.online.R;
import ru.electric.ec.online.common.App;
import ru.electric.ec.online.databinding.DetailsItemBinding;
import ru.electric.ec.online.models.Detail;
import ru.electric.ec.online.models.Info;
import ru.electric.ec.online.models.Invoice;
import ru.electric.ec.online.ui.ViewRouter;

public class DetailsViewAdapter extends RecyclerView.Adapter<DetailsItemViewHolder> {

    private List<Detail> details;
    private DetailsItemViewModel viewModel;

    DetailsViewAdapter() {
        details = new ArrayList<>();
    }

    void updateAdapter(Invoice invoice, Context context){
        this.setItems(invoice.details);
        try{
            ((DetailsActivity)context).binding.list.setAdapter(this);
        } catch (Exception e){
            ViewRouter.openInfo(context, new Info(true, false, "Ошибка вывода списка"));
        }
    }

    void setItems(Collection<Detail> collection) {
        try{
            details.addAll(collection);
            notifyDataSetChanged();
        } catch (Exception e){
            ViewRouter.openInfo(App.getAppContext(), new Info(true, false, "Ошибка добавления значений"));
        }
    }

    @NonNull
    @Override
    public DetailsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewModel = new DetailsItemViewModel();
        viewModel.parent.details.clear();
        viewModel.parent.details.addAll(details);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        DetailsItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.details_item, parent, false);
        binding.setViewModel(viewModel);
        return new DetailsItemViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(DetailsItemViewHolder holder, int position) {
        holder.bind(details.get(position), position);
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

}
