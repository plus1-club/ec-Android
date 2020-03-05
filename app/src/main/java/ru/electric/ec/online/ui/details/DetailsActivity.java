package ru.electric.ec.online.ui.details;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.common.App;
import ru.electric.ec.online.common.Service;
import ru.electric.ec.online.databinding.DetailsBinding;
import ru.electric.ec.online.models.Detail;
import ru.electric.ec.online.models.Invoice;
import ru.electric.ec.online.router.RouterServer;
import ru.electric.ec.online.router.RouterView;
import ru.electric.ec.online.server.ServerData;
import ru.electric.ec.online.ui.bill.BillActivity;
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
            RouterServer.unconfirmedItem(this, viewModel.number.get());
        } else if (Service.isEqual(status, getString(R.string.status_reserved))) {
            setTitle(getString(R.string.text_item_reserved));
            RouterServer.reservedItem(this, viewModel.number.get());
        } else if (Service.isEqual(status, getString(R.string.status_ordered))) {
            setTitle(getString(R.string.text_item_ordered));
            RouterServer.orderedItem(this, viewModel.number.get());
        } else if (Service.isEqual(status, getString(R.string.status_canceled))) {
            setTitle(getString(R.string.text_item_canceled));
            RouterServer.canceledItem(this, viewModel.number.get());
        } else if (Service.isEqual(status, getString(R.string.status_overdie))) {
            this.setTitle(getString(R.string.text_item_overdie));
            RouterServer.canceledItem(this, viewModel.number.get());
        } else if (Service.isEqual(status, getString(R.string.status_shipped)) ||
                Service.isEqual(status, getString(R.string.text_item_shipped))) {
            this.setTitle(getString(R.string.text_item_shipped));
            RouterServer.shippedItem(this, viewModel.number.get());
        } else {
            this.setTitle(getString(R.string.text_item_invoice));
        }
    }

    public void refresh(){
        updateItem(viewModel.status.get());
    }

    public void detailOk(ServerData body) {
        if (RouterServer.isSuccess(body)) {
            Invoice invoice = null;
            for (Invoice item: App.getModel().invoice.invoices) {
                if(item.number == viewModel.number.get()){
                    invoice = item;
                }
            }
            if (invoice == null) return;

            List<?> data = (List<?>) body.data;
            invoice.details.clear();
            for (Object element : data) {
                @SuppressWarnings("unchecked")
                Map<String, String> el = (LinkedTreeMap<String, String>) element;
                Detail detail = new Detail(
                        el.get("product"),
                        Service.getInt(el.get("count")),
                        el.get("unit"),
                        Service.getDouble(el.get("price")),
                        Service.getDouble(el.get("sum")),
                        el.get("available"),
                        el.get("delivery"));
                invoice.details.add(detail);
            }
            DetailsViewAdapter adapter = new DetailsViewAdapter();
            adapter.updateAdapter(invoice, this);
        } else {
            RouterView.onUnsuccessful(this, body);
        }
        binding.swiperefresh.setRefreshing(false);
    }

    public void printOk(ServerData body) {
        if (RouterServer.isSuccess(body)) {
            LinkedTreeMap data = (LinkedTreeMap) body.data;
            String link = (String) data.get("file");
            Intent intent = new Intent(this, BillActivity.class);
            intent.putExtra("title", Service.getStr(R.string.text_invoice_short, viewModel.number.get()));
            intent.putExtra("number", viewModel.number.get());
            intent.putExtra("link", link);
            startActivity(intent);
        } else {
            RouterView.onUnsuccessful(this, body);
        }
    }

    public void detailError(Throwable throwable) {
        RouterView.onError(this, throwable);
    }
}
