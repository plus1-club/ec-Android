package ru.electric.ec.online.ui.files;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.electric.ec.online.R;
import ru.electric.ec.online.databinding.FilesItemBinding;

public class FilesViewAdapter extends RecyclerView.Adapter<FilesItemViewHolder> {

    private List<File> files;
    private FilesItemViewModel viewModel;

    FilesViewAdapter(){
        files = new ArrayList<>();
    }

    void setFiles(Collection<File> collection) {
        files.clear();
        files.addAll(collection);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FilesItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewModel = new FilesItemViewModel(parent.getContext());
        viewModel.parent.files.clear();
        viewModel.parent.files.addAll(files);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FilesItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.files_item, parent, false);
        binding.setViewModel(viewModel);
        return new FilesItemViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(FilesItemViewHolder holder, int position) {
        holder.bind(files.get(position), position);
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
