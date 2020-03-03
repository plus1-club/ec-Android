package ru.electric.ec.online.ui.invoice;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.databinding.InvoiceBinding;
import ru.electric.ec.online.server.ServerResponse;
import ru.electric.ec.online.ui.menu.MenuViewModel;

public class InvoiceActivity extends AppCompatActivity {

    public InvoiceViewModel viewModel;
    MenuViewModel navigationModel;

    private InvoiceViewAdapter adapter;
    public InvoiceBinding binding;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = InvoiceViewModel.getInstance();
        viewModel.invoices.clear();

        Bundle bundle = getIntent().getExtras();
        this.setTitle(Objects.requireNonNull(bundle).getString("title"));
        viewModel.title.set(Objects.requireNonNull(bundle).getString("title"));

        // Подготовка биндинга
        binding = DataBindingUtil.setContentView(this, R.layout.invoice);
        binding.setViewModel(viewModel);
        binding.list.setHasFixedSize(true);

        // Подготовка и установка лайаут-менеджера
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.list.setLayoutManager(layoutManager);

        // Подготовка и установка адаптера
        adapter = new InvoiceViewAdapter();
        binding.list.setAdapter(adapter);
        adapter.setItems(viewModel.invoices);

        // Подключение навигации
        navigationModel = new MenuViewModel(
                this,  binding.drawer, binding.include.toolbar, binding.navigator);
        setSupportActionBar(navigationModel.toolbar);

        // Обновление списка
        updateList();
        binding.swiperefresh.setRefreshing(true);
        binding.swiperefresh.setOnRefreshListener(this::refresh);
        binding.swiperefresh.setRefreshing(false);
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

    public void updateList(){
        if (Objects.equals(viewModel.title.get(), getString(R.string.text_list_unconfirmed))) {
            ServerResponse.unconfirmedList(this);
        } else if (Objects.equals(viewModel.title.get(), getString(R.string.text_list_reserved))) {
            ServerResponse.reservedList(this);
        } else if (Objects.equals(viewModel.title.get(), getString(R.string.text_list_ordered))) {
            ServerResponse.orderedList(this);
        } else if (Objects.equals(viewModel.title.get(), getString(R.string.text_list_canceled))) {
            ServerResponse.canceledList(this);
        } else if (Objects.equals(viewModel.title.get(), getString(R.string.text_list_shipped))) {
            ServerResponse.shippedList(this);
        } else {
            adapter.setItems(new ArrayList<>());
        }
    }

    public void refresh(){
        updateList();
        new Handler().postDelayed(() -> {
            adapter = new InvoiceViewAdapter();
            adapter.setItems(viewModel.invoices);
            binding.list.setAdapter(adapter);
            binding.swiperefresh.setRefreshing(false);
        }, 3000);
    }
}
