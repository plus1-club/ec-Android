package ru.electric.ec.online.ui.request;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.databinding.RequestBinding;
import ru.electric.ec.online.ui.menu.MenuViewModel;

public class RequestActivity extends AppCompatActivity {

    RequestViewModel viewModel;
    MenuViewModel navigationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = RequestViewModel.getInstance();
        Bundle bundle = getIntent().getExtras();
        this.setTitle(Objects.requireNonNull(bundle).getString("title"));

        RequestBinding binding = DataBindingUtil.setContentView(this, R.layout.request);
        binding.setViewModel(viewModel);
        binding.viewpager.setAdapter(new RequestFragmentAdapter(getSupportFragmentManager()));
        binding.tabs.setupWithViewPager(binding.viewpager);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 0) {
            if (resultCode == Activity.RESULT_OK) {
                String path = Objects.requireNonNull(data.getData()).getPath();
                path = Objects.requireNonNull(path).replace("/document/raw:", "");
                viewModel.excel.set(path);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
