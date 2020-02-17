package ru.electric.ec.online.ui;

import android.content.Context;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import java.io.File;
import java.util.Objects;

public class FilesItemViewModel {

    public ObservableInt position;
    public ObservableField<String> name;
    ObservableBoolean isFolder;
    public ObservableInt icon;

    public FilesViewModel parent;
    private static FilesItemViewModel mInstance;

    private FilesItemViewModel(Context context){
        position = new ObservableInt();
        name = new ObservableField<>();
        isFolder = new ObservableBoolean();
        icon = new ObservableInt();
        parent = FilesViewModel.getInstance(context);
    }

    // Получение единственного экземпляра класса
    public static FilesItemViewModel getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new FilesItemViewModel(context);
        }
        return mInstance;
    }

    public void onSelect(Context context){
        File file = parent.files.get(position.get());
        if (isFolder.get()){
            parent.navigateTo(file);
            Objects.requireNonNull(parent.adapter.get()).setFiles(parent.getFiles());
            Objects.requireNonNull(parent.adapter.get()).notifyDataSetChanged();
        } else {
            InfoViewModel.log(context, false, true, file.getAbsolutePath());
        }
    }
}
