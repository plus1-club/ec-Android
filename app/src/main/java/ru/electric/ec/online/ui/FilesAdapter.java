package ru.electric.ec.online.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.databinding.FilesItemBinding;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.FilesViewHolder> {

    private List<File> files;
    private FilesItemViewModel viewModel;

    FilesAdapter(){
        files = new ArrayList<>();
    }

    void setFiles(Collection<File> collection) {
        files.addAll(collection);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FilesAdapter.FilesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewModel = FilesItemViewModel.getInstance(parent.getContext());
        viewModel.parent.files.clear();
        viewModel.parent.files.addAll(files);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FilesItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.files_item, parent, false);
        binding.setViewModel(viewModel);
        return new FilesAdapter.FilesViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(FilesAdapter.FilesViewHolder holder, int position) {
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

    /**
     * View holder
     */
    static class FilesViewHolder extends RecyclerView.ViewHolder {

        private FilesItemBinding binding;
        private FilesItemViewModel viewModel;

        FilesViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
            viewModel = Objects.requireNonNull(binding).getViewModel();
        }

        void bind(File file, int position) {
            viewModel.position.set(position);
            viewModel.name.set(file.getName());
            viewModel.isFolder.set(file.isDirectory());
            if (file.isDirectory()){
                viewModel.icon.set(R.drawable.folder);
            } else {
                viewModel.icon.set(R.drawable.file);
            }
        }

    }
}
