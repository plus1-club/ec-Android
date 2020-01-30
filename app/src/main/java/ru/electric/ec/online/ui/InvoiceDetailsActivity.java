package ru.electric.ec.online.ui;

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
import ru.electric.ec.online.Service;
import ru.electric.ec.online.databinding.InvoiceDetailsBinding;
import ru.electric.ec.online.models.Invoice;
import ru.electric.ec.online.server.ServerResponse;

public class InvoiceDetailsActivity extends AppCompatActivity {

    InvoiceDetailsViewModel viewModel;
    InvoiceTableViewModel parent;
    NavigationViewModel navigationModel;

    private InvoiceDetailsAdapter adapter;
    public InvoiceDetailsBinding binding;
    private LinearLayoutManager layoutManager;
    private AppCompatActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        viewModel = InvoiceDetailsViewModel.getInstance();

        Bundle bundle = getIntent().getExtras();
        viewModel.number.set(Objects.requireNonNull(bundle).getInt("number"));
        viewModel.date.set(bundle.getString("date"));
        viewModel.sum.set(bundle.getDouble("sum"));
        viewModel.status.set(bundle.getString("status"));
        viewModel.waybill.set(bundle.getInt("waybill"));
        String status = viewModel.status.get();

        // Подготовка биндинга
        binding = DataBindingUtil.setContentView(this, R.layout.invoice_details);
        binding.setViewModel(viewModel);
        binding.list.setHasFixedSize(true);

        // Подготовка и установка лайаут-менеджера
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.list.setLayoutManager(layoutManager);

        // Подготовка и установка адаптера
        parent = InvoiceTableViewModel.getInstance();
        adapter = new InvoiceDetailsAdapter();
        binding.list.setAdapter(adapter);
        adapter.setItems(parent.invoices.get(bundle.getInt("position")).details);

        viewModel.showInvoiceButton.set(
                Service.isEqual(status, Service.getStr(R.string.status_reserved)) ||
                        Service.isEqual(status, Service.getStr(R.string.status_ordered)));

        // Подключение навигации
        navigationModel = new NavigationViewModel(
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
            ServerResponse.unconfirmedItem(this, viewModel.number.get());
        } else if (Service.isEqual(status, getString(R.string.status_reserved))) {
            setTitle(getString(R.string.text_item_reserved));
            ServerResponse.reservedItem(this, viewModel.number.get());
        } else if (Service.isEqual(status, getString(R.string.status_ordered))) {
            setTitle(getString(R.string.text_item_ordered));
            ServerResponse.orderedItem(this, viewModel.number.get());
        } else if (Service.isEqual(status, getString(R.string.status_canceled))) {
            setTitle(getString(R.string.text_item_canceled));
            ServerResponse.canceledItem(this, viewModel.number.get());
        } else if (Service.isEqual(status, getString(R.string.status_overdie))) {
            this.setTitle(getString(R.string.text_item_overdie));
            ServerResponse.canceledItem(this, viewModel.number.get());
        } else if (Service.isEqual(status, getString(R.string.status_shipped)) ||
                Service.isEqual(status, getString(R.string.text_item_shipped))) {
            this.setTitle(getString(R.string.text_item_shipped));
            ServerResponse.shippedItem(this, viewModel.number.get());
        } else {
            this.setTitle(getString(R.string.text_item_invoice));
        }
    }

    public void refresh(){
        int number = viewModel.number.get();
        Invoice invoice = null;
        for (Invoice item:parent.invoices) {
            if(item.number == number){
                invoice = item;
            }
        }
        if (invoice == null) return;
        final Invoice thisInvoice = invoice;

        updateItem(viewModel.status.get());
        new Handler().postDelayed(() -> {
            adapter = new InvoiceDetailsAdapter();
            adapter.setItems(thisInvoice.details);
            binding.list.setAdapter(adapter);
            binding.swiperefresh.setRefreshing(false);
        }, 1000);
    }
}
