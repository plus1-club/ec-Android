package ru.electric.ec.online.ui.search;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.databinding.SearchBinding;
import ru.electric.ec.online.server.ServerRouter;
import ru.electric.ec.online.ui.info.InfoActivity;
import ru.electric.ec.online.ui.menu.MenuViewModel;
import ru.electric.ec.online.ui.request.RequestViewModel;

public class SearchActivity extends AppCompatActivity {

    SearchViewModel viewModel;
    MenuViewModel navigationModel;

    private SearchViewAdapter adapter;
    private SearchBinding binding;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = SearchViewModel.getInstance();

        Bundle bundle = getIntent().getExtras();
        this.setTitle(Objects.requireNonNull(bundle).getString("title"));
        copyFromRequest();

        // Подготовка биндинга
        binding = DataBindingUtil.setContentView(this, R.layout.search);
        binding.setViewModel(viewModel);
        binding.list.setHasFixedSize(true);
        binding.list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // Подготовка и установка лайаут-менеджера
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.list.setLayoutManager(layoutManager);

        // Подготовка и установка адаптера
        adapter = new SearchViewAdapter();
        adapter.setItems(viewModel.search);
        binding.list.setAdapter(adapter);

        // Заполнение модели
        viewModel.adapter.set(adapter);
        viewModel.binding.set(binding);

        // Подключение навигации
        navigationModel = new MenuViewModel(
                this,  binding.drawer, binding.include.toolbar, binding.navigator);
        setSupportActionBar(navigationModel.toolbar);

        // Обновление списка
        binding.swiperefresh.setRefreshing(true);
        if (viewModel.isExcel.get()){
            ServerRouter.fromExcel(this, viewModel);
        } else {
            ServerRouter.byCode(this, viewModel);
        }
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
        adapter = new SearchViewAdapter();
        if (viewModel.search.size() > 0){
            adapter.setItems(viewModel.search);
            binding.list.setAdapter(adapter);
            viewModel.adapter.set(adapter);
            binding.swiperefresh.setRefreshing(false);
        } else {
            Intent intent = new Intent(this, InfoActivity.class);
            intent.putExtra("title", this.getTitle());
            intent.putExtra("info", this.getString(R.string.text_product_not_found));
            intent.putExtra("activityName", "RequestActivity");
            this.startActivity(intent);
        }
    }

    private void copyFromRequest(){
        RequestViewModel from = RequestViewModel.getInstance();
        viewModel.product.set(from.product.get());
        viewModel.count.set(from.count.get());
        viewModel.productColumn.set(from.productColumn.get());
        viewModel.countColumn.set(from.countColumn.get());
        viewModel.isFullSearch.set(from.isFullSearch.get());
        viewModel.excel.set(from.excel.get());
        viewModel.isExcel.set(from.isExcel.get());

    }
}
