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
import club.plus1.ec_online.Server;
import club.plus1.ec_online.databinding.InvoiceDetailsBinding;
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

        if (Objects.equals(Objects.requireNonNull(viewModel.status.get()).toLowerCase(), getString(R.string.status_unconfirmed).toLowerCase())) {
            setTitle(getString(R.string.text_item_unconfirmed));
            Server.unconfirmedItem(this, viewModel.number.get());
        } else if (Objects.equals(Objects.requireNonNull(viewModel.status.get()).toLowerCase(), getString(R.string.status_reserved).toLowerCase())) {
            setTitle(getString(R.string.text_item_reserved));
            Server.reservedItem(this, viewModel.number.get());
        } else if (Objects.equals(Objects.requireNonNull(viewModel.status.get()).toLowerCase(), getString(R.string.status_ordered).toLowerCase())) {
            setTitle(getString(R.string.text_item_ordered));
            Server.orderedItem(this, viewModel.number.get());
        } else if (Objects.equals(Objects.requireNonNull(viewModel.status.get()).toLowerCase(), getString(R.string.status_canceled).toLowerCase())) {
            setTitle(getString(R.string.text_item_canceled));
            Server.canceledItem(this, viewModel.number.get());
        } else if (Objects.equals(Objects.requireNonNull(viewModel.status.get()).toLowerCase(), getString(R.string.status_overdie).toLowerCase())) {
            this.setTitle(getString(R.string.text_item_overdie));
            Server.canceledItem(this, viewModel.number.get());
        } else if (Objects.equals(Objects.requireNonNull(viewModel.status.get()).toLowerCase(), getString(R.string.status_shipped).toLowerCase())) {
            this.setTitle(getString(R.string.text_item_shipped));
            Server.shippedItem(this, viewModel.number.get());
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
