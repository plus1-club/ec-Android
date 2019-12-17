package ru.electric.ec.online.views;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.databinding.SearchBinding;
import ru.electric.ec.online.viewadapters.SearchAdapter;
import ru.electric.ec.online.viewmodels.NavigationViewModel;
import ru.electric.ec.online.viewmodels.RequestViewModel;
import ru.electric.ec.online.viewmodels.ServerResponse;

public class SearchActivity extends AppCompatActivity {

    RequestViewModel viewModel;
    NavigationViewModel navigationModel;

    private SearchAdapter adapter;
    private SearchBinding binding;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = RequestViewModel.getInstance();
        Bundle bundle = getIntent().getExtras();
        this.setTitle(Objects.requireNonNull(bundle).getString("title"));

        // Подготовка биндинга
        binding = DataBindingUtil.setContentView(this, R.layout.search);
        binding.setViewModel(viewModel);
        binding.list.setHasFixedSize(true);

        // Подготовка и установка лайаут-менеджера
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.list.setLayoutManager(layoutManager);

        // Подготовка и установка адаптера
        adapter = new SearchAdapter();
        adapter.setItems(viewModel.search);
        viewModel.searchAdapter.set(adapter);
        binding.list.setAdapter(adapter);

        // Подключение навигации
        navigationModel = new NavigationViewModel(
                this,  binding.drawer, binding.include.toolbar, binding.navigator);
        setSupportActionBar(navigationModel.toolbar);

        // Обновление списка
        binding.swiperefresh.setRefreshing(true);
        ServerResponse.byCode(this, viewModel.product.get(), viewModel.count.get(), viewModel.isFullSearch.get());
        refreshSearch();
        binding.swiperefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refreshSearch();
                    }
                }
        );
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

    public void refreshSearch(){
        binding.swiperefresh.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter = new SearchAdapter();
                adapter.setItems(viewModel.search);
                binding.list.setAdapter(adapter);
                viewModel.searchAdapter.set(adapter);
                binding.swiperefresh.setRefreshing(false);
            }
        }, 1000);
    }
}
