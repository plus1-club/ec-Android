package ru.electric.ec.online.ui.request;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.databinding.RequestBinding;
import ru.electric.ec.online.ui.menu.MenuViewModel;

public class RequestActivity extends AppCompatActivity {

    RequestViewModel viewModel;
    MenuViewModel navigationModel;
    RequestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = RequestViewModel.getInstance();
        Bundle bundle = Objects.requireNonNull(getIntent().getExtras());
        String title = bundle.getString("title");
        if (title == null || title.isEmpty()){
            setTitle(viewModel.title.get());
        } else {
            viewModel.title.set(title);
            setTitle(title);
        }
        viewModel.page.set(bundle.getInt("page"));

        binding = DataBindingUtil.setContentView(this, R.layout.request);
        binding.setViewModel(viewModel);
        binding.viewpager.setAdapter(new RequestFragmentAdapter(getSupportFragmentManager()));
        binding.tabs.setupWithViewPager(binding.viewpager);

        navigationModel = new MenuViewModel(
                this,  binding.drawer, binding.include.toolbar, binding.navigator);
        // Установить Toolbar для замены ActionBar'а.
        setSupportActionBar(navigationModel.toolbar);

        if (viewModel.page.get() == 0) {
            viewModel.isExcel.set(false);
        } else {
            viewModel.isExcel.set(true);
        }
        binding.tabs.selectTab(binding.tabs.getTabAt(viewModel.page.get()));

        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    viewModel.isExcel.set(false);
                } else {
                    viewModel.isExcel.set(true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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
