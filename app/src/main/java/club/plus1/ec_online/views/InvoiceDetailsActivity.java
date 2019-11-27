package club.plus1.ec_online.views;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Objects;

import club.plus1.ec_online.R;
import club.plus1.ec_online.databinding.InvoiceDetailsBinding;
import club.plus1.ec_online.models.ServerResponse;
import club.plus1.ec_online.models.Service;
import club.plus1.ec_online.viewadapters.InvoiceDetailsAdapter;
import club.plus1.ec_online.viewmodels.InvoiceDetailsViewModel;
import club.plus1.ec_online.viewmodels.InvoiceTableViewModel;
import club.plus1.ec_online.viewmodels.NavigationViewModel;

public class InvoiceDetailsActivity extends AppCompatActivity {

    InvoiceDetailsViewModel viewModel;
    InvoiceTableViewModel parent;
    public InvoiceDetailsBinding binding;
    NavigationViewModel navigationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = InvoiceDetailsViewModel.getInstance();

        Bundle bundle = getIntent().getExtras();
        viewModel.number.set(Objects.requireNonNull(bundle).getInt("number"));
        viewModel.date.set(bundle.getString("date"));
        viewModel.sum.set(bundle.getDouble("sum"));
        viewModel.status.set(bundle.getString("status"));
        viewModel.waybill.set(bundle.getInt("waybill"));

        parent = InvoiceTableViewModel.getInstance();
        InvoiceDetailsAdapter invoiceDetailsAdapter = new InvoiceDetailsAdapter();
        invoiceDetailsAdapter.setItems(parent.invoices.get(bundle.getInt("position")).details);

        binding = DataBindingUtil.setContentView(this, R.layout.invoice_details);
        binding.setViewModel(viewModel);
        binding.list.setHasFixedSize(true);
        binding.list.setLayoutManager(new LinearLayoutManager(this));
        binding.list.setAdapter(invoiceDetailsAdapter);

        String status = viewModel.status.get();
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
        } else if (Service.isEqual(status, getString(R.string.status_shipped))) {
            this.setTitle(getString(R.string.text_item_shipped));
            ServerResponse.shippedItem(this, viewModel.number.get());
        } else {
            this.setTitle(getString(R.string.text_item_invoice));
        }

        navigationModel = new NavigationViewModel(
                this,  binding.drawer, binding.include.toolbar, binding.navigator);
        // Установить Toolbar для замены ActionBar'а.
        setSupportActionBar(navigationModel.toolbar);
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
