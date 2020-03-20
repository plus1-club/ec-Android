package ru.electric.ec.online.ui.basket;

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
import ru.electric.ec.online.databinding.BasketBinding;
import ru.electric.ec.online.server.ServerRouter;
import ru.electric.ec.online.ui.menu.MenuViewModel;

public class BasketActivity extends AppCompatActivity {

    BasketViewModel viewModel;
    MenuViewModel navigationModel;

    private BasketViewAdapter adapter;
    private BasketBinding binding;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = BasketViewModel.getInstance();

        Bundle bundle = getIntent().getExtras();
        this.setTitle(Objects.requireNonNull(bundle).getString("title"));
        viewModel.total.set(0);

        // Подготовка биндинга
        binding = DataBindingUtil.setContentView(this, R.layout.basket);
        binding.setViewModel(viewModel);
        binding.list.setHasFixedSize(true);
        binding.list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // Подготовка и установка лайаут-менеджера
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.list.setLayoutManager(layoutManager);

        // Подготовка и установка адаптера
        adapter = new BasketViewAdapter();
        adapter.setItems(viewModel.basket);
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
        ServerRouter.getBasket(this, viewModel);
        binding.swiperefresh.setOnRefreshListener(
            () -> ServerRouter.getBasket(this, viewModel)
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
}
