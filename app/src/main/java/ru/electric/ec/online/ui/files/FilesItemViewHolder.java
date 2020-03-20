package ru.electric.ec.online.ui.files;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.common.App;
import ru.electric.ec.online.databinding.FilesItemBinding;

/**
 * View holder
 */
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
        if (file.equals(viewModel.parent.currentDirectory)){
            viewModel.name.set("..");
        } else {
            viewModel.name.set(file.getName());
        }
        viewModel.isFolder.set(file.isDirectory());
        if (file.isDirectory()){
            if (file.equals(viewModel.parent.currentDirectory)) {
                viewModel.icon.set(App.getAppContext().getDrawable(R.drawable.up));
            } else {
                viewModel.icon.set(App.getAppContext().getDrawable(R.drawable.folder));
            }
            viewModel.ext.set("");
        } else {
            String name = Objects.requireNonNull(viewModel.name.get());
            int dotIndex = name.lastIndexOf(".");
            String ext = "";
            if (dotIndex > -1) {
                ext = name.substring(dotIndex).toLowerCase();
            }
            viewModel.ext.set(ext);
            if (ext.equals(".xls") || ext.equals(".xlsx") || ext.equals(".ods")){
                viewModel.icon.set(App.getAppContext().getDrawable(R.drawable.excel));
            } else {
                viewModel.icon.set(App.getAppContext().getDrawable(R.drawable.file));
            }
        }
        binding.icon.setImageDrawable(viewModel.icon.get());
    }
}

