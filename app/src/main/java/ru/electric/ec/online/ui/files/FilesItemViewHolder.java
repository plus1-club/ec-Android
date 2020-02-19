package ru.electric.ec.online.ui.files;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.databinding.FilesItemBinding;

class FilesItemViewHolder extends RecyclerView.ViewHolder {

    private FilesItemBinding binding;
    private FilesItemViewModel viewModel;

    FilesItemViewHolder(View view) {
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

