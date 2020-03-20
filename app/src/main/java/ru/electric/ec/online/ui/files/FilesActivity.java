package ru.electric.ec.online.ui.files;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.common.App;
import ru.electric.ec.online.databinding.FilesBinding;
import ru.electric.ec.online.models.Info;
import ru.electric.ec.online.ui.ViewRouter;
import ru.electric.ec.online.ui.menu.MenuViewModel;

public class FilesActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    FilesViewModel viewModel;
    MenuViewModel navigationModel;

    private FilesViewAdapter adapter;
    private FilesBinding binding;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = FilesViewModel.getInstance(this);
        Bundle bundle = getIntent().getExtras();
        this.setTitle(Objects.requireNonNull(bundle).getString("title"));

        // Подготовка биндинга
        binding = DataBindingUtil.setContentView(this, R.layout.files);
        binding.setViewModel(viewModel);
        binding.files.setHasFixedSize(true);
        binding.files.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // Подготовка и установка лайаут-менеджера
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.files.setLayoutManager(layoutManager);

        // Подготовка и установка адаптера
        adapter = new FilesViewAdapter();
        adapter.setFiles(viewModel.files);
        binding.files.setAdapter(adapter);
        viewModel.adapter.set(adapter);

        // Подключение навигации
        navigationModel = new MenuViewModel(
                this,  binding.drawer, binding.include.toolbar, binding.navigator);
        setSupportActionBar(navigationModel.toolbar);

        initFileManager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.files.clear();
    }

    /**
     * Этот метод будет вызван, когда пользователь предоставит разрешения, или откажет в них
     *
     * @param requestCode  Код, который мы передали при запросе
     * @param permissions  Список разрешений
     * @param grantResults Список результатов
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ViewRouter.openInfo(App.getAppContext(),
                        new Info(true, false, "Достур предоставлен"));
                initFileManager();
            } else {
                ViewRouter.openInfo(App.getAppContext(),
                        new Info(true, false, "Доступ запрещен"));
                requestPermissions(); // Запрашиваем ещё раз
            }
        }
    }

    /**
     * Инициализируем менеджер файлов, проверяем разрешение
     */
    private void initFileManager() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Разрешение предоставлено
            viewModel = FilesViewModel.getInstance(this);
            updateFileList();
        } else {
            requestPermissions();
        }
    }

    /**
     * Запрашиваем разрешение
     */
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE
        );
    }

    /**
     * Получаем список файлов и передаём в адаптер
     */
    private void updateFileList() {
        if (viewModel.currentDirectory.equals(viewModel.rootDirectory)){
            setTitle("");
        } else {
            setTitle(viewModel.currentDirectory.getName());
        }
        adapter.setFiles(viewModel.getFiles());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        navigationModel.actionBar.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        navigationModel.actionBar.onConfigurationChanged(newConfig);
    }

}
