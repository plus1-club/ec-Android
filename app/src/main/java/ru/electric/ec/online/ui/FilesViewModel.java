package ru.electric.ec.online.ui;

import android.content.Context;
import android.os.Environment;

import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import ru.electric.ec.online.Service;

/**
 * Менеджер файлов
 */
public class FilesViewModel {

    private File currentDirectory;
    private final File rootDirectory;

    public ObservableField<String> title;
    ObservableList<File> files;
    ObservableField<FilesAdapter> adapter;

    private static FilesViewModel mInstance;    // Ссылка для биндинга с View

    private FilesViewModel(Context context) {
        title = new ObservableField<>();
        files = new ObservableArrayList<>();
        adapter = new ObservableField<>();

        File directory;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            directory = Environment.getExternalStorageDirectory();
        } else {
            directory = ContextCompat.getDataDir(context);
        }
        rootDirectory = directory;
        navigateTo(Objects.requireNonNull(directory));
    }

    // Получение единственного экземпляра класса
    public static FilesViewModel getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new FilesViewModel(context);
        }
        return mInstance;
    }

    /**
     * Пробуем перейти в указанную директорию
     *
     * @param directory Директория
     * @return true если удалось, false при ошибке
     */
    boolean navigateTo(File directory) {
        // Проверим, является ли файл директорией
        if (!directory.isDirectory()) {
            Service.log(false, String.format("%1$s не директория", directory.getAbsolutePath()));
            return false;
        }

        // Проверим, не поднялись ли мы выше rootDirectory
        if (!directory.equals(rootDirectory) &&
                rootDirectory.getAbsolutePath().contains(directory.getAbsolutePath())) {
            Service.log(false,
                    String.format("Попытка подняться выше корневой директории %1$s",
                    directory.getAbsolutePath()));
            return false;
        }
        currentDirectory = directory;
        return true;
    }

    /**
     * Получаем список файлов в текущей директории
     * @return список файлов
     */
    List<File> getFiles() {
        files.clear();
        files.addAll(Arrays.asList(Objects.requireNonNull(currentDirectory.listFiles())));
        files.add(0, currentDirectory.getParentFile());
        return files;
    }
}
