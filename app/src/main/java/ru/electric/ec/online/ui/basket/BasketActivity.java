package ru.electric.ec.online.ui.basket;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.databinding.BasketBinding;
import ru.electric.ec.online.models.Request;
import ru.electric.ec.online.server.ServerResponse;
import ru.electric.ec.online.ui.menu.MenuViewModel;
import ru.electric.ec.online.ui.request.RequestViewModel;

public class BasketActivity extends AppCompatActivity {

    RequestViewModel viewModel;
    MenuViewModel navigationModel;

    private BasketViewAdapter adapter;
    private BasketBinding binding;
    private LinearLayoutManager layoutManager;
    private AppCompatActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        viewModel = RequestViewModel.getInstance();
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
        viewModel.basketAdapter.set(adapter);

        // Подключение навигации
        navigationModel = new MenuViewModel(
                this,  binding.drawer, binding.include.toolbar, binding.navigator);
        setSupportActionBar(navigationModel.toolbar);

        // Обновление списка
        binding.swiperefresh.setRefreshing(true);
        ServerResponse.getBasket(activity);
        refreshBasket();
        binding.swiperefresh.setOnRefreshListener(
            () -> {
                ServerResponse.getBasket(activity);
                refreshBasket();
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

    public void refreshBasket(){
        new Handler().postDelayed(() -> {
            adapter = new BasketViewAdapter();
            adapter.setItems(viewModel.basket);
            binding.list.setAdapter(adapter);
            viewModel.total.set(0);
            viewModel.basketAdapter.set(adapter);
            for (Request item : viewModel.basket) {
                if(item.check) {
                    viewModel.total.set(viewModel.total.get() + item.requestCount * item.price);
                }
            }
            binding.swiperefresh.setRefreshing(false);
        }, 1000);
    }
}
