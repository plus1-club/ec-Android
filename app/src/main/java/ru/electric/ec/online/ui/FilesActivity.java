package ru.electric.ec.online.ui;

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
import ru.electric.ec.online.Service;
import ru.electric.ec.online.databinding.FilesBinding;

public class FilesActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    FilesViewModel viewModel;
    NavigationViewModel navigationModel;

    private FilesAdapter adapter;
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
        adapter = new FilesAdapter();
        adapter.setFiles(viewModel.files);
        binding.files.setAdapter(adapter);
        viewModel.adapter.set(adapter);

        // Подключение навигации
        navigationModel = new NavigationViewModel(
                this,  binding.drawer, binding.include.toolbar, binding.navigator);
        setSupportActionBar(navigationModel.toolbar);

        initFileManager();
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
                Service.log(false, "Достур предоставлен");
                initFileManager();
            } else {
                Service.log(false, "Доступ запрещен");
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
