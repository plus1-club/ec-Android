package ru.electric.ec.online.ui.menu;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import ru.electric.ec.online.R;
import ru.electric.ec.online.databinding.MenuBinding;

public class MenuActivity extends AppCompatActivity {

    public MenuItemViewModel viewModel;
    MenuViewModel navigationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new MenuItemViewModel(this);

        MenuBinding binding = DataBindingUtil.setContentView(this, R.layout.menu);
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
