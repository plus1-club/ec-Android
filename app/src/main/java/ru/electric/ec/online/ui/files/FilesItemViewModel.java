package ru.electric.ec.online.ui.files;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import java.io.File;
import java.util.Objects;

import ru.electric.ec.online.router.RouterView;
import ru.electric.ec.online.ui.request.RequestViewModel;

public class FilesItemViewModel {

    public ObservableInt position;
    public ObservableField<String> name;
    ObservableField<String> ext;
    ObservableBoolean isFolder;
    public ObservableField<Drawable> icon;

    public FilesViewModel parent;

    FilesItemViewModel(Context context){
        position = new ObservableInt();
        name = new ObservableField<>();
        ext = new ObservableField<>();
        isFolder = new ObservableBoolean();
        icon = new ObservableField<>();
        parent = FilesViewModel.getInstance(context);
    }

    public void onSelect(Context context){
        File file = parent.files.get(position.get());
        if (isFolder.get()){
            parent.navigateTo(file);
            FilesViewAdapter adapter = Objects.requireNonNull(parent.adapter.get());
            adapter.setFiles(parent.getFiles());
            if (parent.currentDirectory.equals(parent.rootDirectory)){
                ((FilesActivity) context).setTitle("");
            } else {
                ((FilesActivity) context).setTitle(parent.currentDirectory.getName());
            }
            adapter.notifyDataSetChanged();
        } else {
            int dotIndex = file.getName().lastIndexOf(".");
            String ext = "";
            if (dotIndex > -1) {
                ext = file.getName().substring(dotIndex).toLowerCase();
            }
            if (ext.equals(".xls") || ext.equals(".xlsx") || ext.equals(".ods")) {
                RequestViewModel.getInstance().excel.set(file.getAbsolutePath());
                RouterView.openRequest(context, 1);
            }
        }
    }
}
