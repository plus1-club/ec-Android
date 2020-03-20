package ru.electric.ec.online.ui.details;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.common.Service;
import ru.electric.ec.online.databinding.DetailsBinding;
import ru.electric.ec.online.server.ServerRouter;
import ru.electric.ec.online.ui.invoice.InvoiceViewModel;
import ru.electric.ec.online.ui.menu.MenuViewModel;

public class DetailsActivity extends AppCompatActivity {

    DetailsViewModel viewModel;
    InvoiceViewModel parent;
    MenuViewModel navigationModel;

    private DetailsViewAdapter adapter;
    public DetailsBinding binding;
    private LinearLayoutManager layoutManager;
    private AppCompatActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        viewModel = DetailsViewModel.getInstance();

        Bundle bundle = getIntent().getExtras();
        viewModel.number.set(Objects.requireNonNull(bundle).getInt("number"));
        viewModel.date.set(bundle.getString("date"));
        viewModel.sum.set(bundle.getDouble("sum"));
        viewModel.status.set(bundle.getString("status"));
        viewModel.waybill.set(bundle.getInt("waybill"));
        String status = viewModel.status.get();

        // Подготовка биндинга
        binding = DataBindingUtil.setContentView(this, R.layout.details);
        binding.setViewModel(viewModel);
        binding.list.setHasFixedSize(true);

        // Подготовка и установка лайаут-менеджера
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.list.setLayoutManager(layoutManager);

        // Подготовка и установка адаптера
        parent = InvoiceViewModel.getInstance();
        adapter = new DetailsViewAdapter();
        binding.list.setAdapter(adapter);
        adapter.setItems(parent.invoices.get(bundle.getInt("position")).details);

        viewModel.showInvoiceButton.set(
                Service.isEqual(status, Service.getStr(R.string.status_reserved)) ||
                        Service.isEqual(status, Service.getStr(R.string.status_ordered)));

        // Сохранение адаптера и биндинга в модели
        viewModel.adapter.set(adapter);
        viewModel.binding.set(binding);

        // Подключение навигации
        navigationModel = new MenuViewModel(
                this,  binding.drawer, binding.include.toolbar, binding.navigator);
        setSupportActionBar(navigationModel.toolbar);

        // Обновление списка
        updateItem(status);
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

    public void updateItem(String status){
        if (Service.isEqual(status, getString(R.string.status_unconfirmed))) {
            setTitle(getString(R.string.text_item_unconfirmed));
            ServerRouter.unconfirmedItem(this, viewModel, viewModel.number.get());
        } else if (Service.isEqual(status, getString(R.string.status_reserved))) {
            setTitle(getString(R.string.text_item_reserved));
            ServerRouter.reservedItem(this, viewModel, viewModel.number.get());
        } else if (Service.isEqual(status, getString(R.string.status_ordered))) {
            setTitle(getString(R.string.text_item_ordered));
            ServerRouter.orderedItem(this, viewModel, viewModel.number.get());
        } else if (Service.isEqual(status, getString(R.string.status_canceled))) {
            setTitle(getString(R.string.text_item_canceled));
            ServerRouter.canceledItem(this, viewModel, viewModel.number.get());
        } else if (Service.isEqual(status, getString(R.string.status_overdie))) {
            this.setTitle(getString(R.string.text_item_overdie));
            ServerRouter.canceledItem(this, viewModel, viewModel.number.get());
        } else if (Service.isEqual(status, getString(R.string.status_shipped)) ||
                Service.isEqual(status, getString(R.string.text_item_shipped))) {
            this.setTitle(getString(R.string.text_item_shipped));
            ServerRouter.shippedItem(this, viewModel, viewModel.number.get());
        } else {
            this.setTitle(getString(R.string.text_item_invoice));
        }
    }

    public void refresh(){
        updateItem(viewModel.status.get());
    }
}
