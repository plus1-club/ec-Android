package ru.electric.ec.online.ui.files;

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

import ru.electric.ec.online.common.App;
import ru.electric.ec.online.models.Info;
import ru.electric.ec.online.router.RouterView;

/**
 * Менеджер файлов
 */
public class FilesViewModel {

    File currentDirectory;
    final File rootDirectory;

    public ObservableField<String> title;
    ObservableList<File> files;
    ObservableField<FilesViewAdapter> adapter;

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
        if (directory.equals(currentDirectory)){
            directory = currentDirectory.getParentFile();
        }

        // Проверим, является ли файл директорией
        if (!Objects.requireNonNull(directory).isDirectory()) {
            RouterView.openInfo(App.getAppContext(),
                    new Info(true, false, String.format("%1$s не директория", directory.getAbsolutePath())));
            return false;
        }

        // Проверим, не поднялись ли мы выше rootDirectory
        if (!directory.equals(rootDirectory) &&
                rootDirectory.getAbsolutePath().contains(directory.getAbsolutePath())) {
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
        if (!currentDirectory.equals(rootDirectory)){
            files.add(0, currentDirectory);
        }
        return files;
    }
}
