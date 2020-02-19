package ru.electric.ec.online.ui.info;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.databinding.InfoBinding;
import ru.electric.ec.online.ui.menu.MenuViewModel;

public class InfoActivity extends AppCompatActivity {

    InfoViewModel viewModel;
    MenuViewModel navigationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = InfoViewModel.getInstance();
        Bundle bundle = getIntent().getExtras();
        viewModel.title.set(Objects.requireNonNull(bundle).getString("title"));
        viewModel.info.set(bundle.getString("info"));
        viewModel.activityName.set(bundle.getString("activityName"));
        this.setTitle(viewModel.title.get());

        InfoBinding binding = DataBindingUtil.setContentView(this, R.layout.info);
        binding.setViewModel(viewModel);

        navigationModel = new MenuViewModel(
                this,  binding.drawer, binding.include.toolbar, binding.navigator);
        // Установить Toolbar для замены ActionBar'а.
        setSupportActionBar(navigationModel.toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (navigationModel.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
