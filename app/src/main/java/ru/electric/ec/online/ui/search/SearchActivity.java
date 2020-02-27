package ru.electric.ec.online.ui.search;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.databinding.SearchBinding;
import ru.electric.ec.online.server.ServerResponse;
import ru.electric.ec.online.ui.info.InfoActivity;
import ru.electric.ec.online.ui.menu.MenuViewModel;
import ru.electric.ec.online.ui.request.RequestViewModel;

public class SearchActivity extends AppCompatActivity {

    RequestViewModel viewModel;
    MenuViewModel navigationModel;

    private SearchViewAdapter adapter;
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
        adapter = new SearchViewAdapter();
        adapter.setItems(viewModel.search);
        viewModel.searchAdapter.set(adapter);
        binding.list.setAdapter(adapter);

        // Подключение навигации
        navigationModel = new MenuViewModel(
                this,  binding.drawer, binding.include.toolbar, binding.navigator);
        setSupportActionBar(navigationModel.toolbar);

        // Обновление списка
        binding.swiperefresh.setRefreshing(true);
        ServerResponse.byCode(this, viewModel.product.get(), viewModel.count.get(), viewModel.isFullSearch.get());
        refreshSearch();
        binding.swiperefresh.setOnRefreshListener(this::refreshSearch);
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
        new Handler().postDelayed(() -> {
            adapter = new SearchViewAdapter();
            if (viewModel.search.size() > 0){
                adapter.setItems(viewModel.search);
                binding.list.setAdapter(adapter);
                viewModel.searchAdapter.set(adapter);
                binding.swiperefresh.setRefreshing(false);
            } else {
                Intent intent = new Intent(this, InfoActivity.class);
                intent.putExtra("title", this.getTitle());
                intent.putExtra("info", this.getString(R.string.text_product_not_found));
                intent.putExtra("activityName", "RequestActivity");
                this.startActivity(intent);
            }
        }, 3000);
    }
}
