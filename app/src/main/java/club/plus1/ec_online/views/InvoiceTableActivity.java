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
import club.plus1.ec_online.databinding.InvoiceTableBinding;
import club.plus1.ec_online.models.StorageStub;
import club.plus1.ec_online.viewadapters.InvoiceTableAdapter;
import club.plus1.ec_online.viewmodels.InvoiceTableViewModel;
import club.plus1.ec_online.viewmodels.NavigationViewModel;

public class InvoiceTableActivity extends AppCompatActivity {

    public InvoiceTableViewModel viewModel;
    public InvoiceTableBinding binding;
    NavigationViewModel navigationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = InvoiceTableViewModel.getInstance();
        StorageStub storageStub = StorageStub.getInstance();
        InvoiceTableAdapter invoiceTableAdapter = new InvoiceTableAdapter();

        Bundle bundle = getIntent().getExtras();
        this.setTitle(Objects.requireNonNull(bundle).getString("title"));
        viewModel.title.set(Objects.requireNonNull(bundle).getString("title"));

        if (Objects.equals(viewModel.title.get(), getString(R.string.text_list_unconfirmed))) {
            Server.unconfirmedList(this);
            //invoiceTableAdapter.setItems(storageStub.invoicesUnconf);
        } else if (Objects.equals(viewModel.title.get(), getString(R.string.text_list_reserved))) {
            //Server.reservedList(this);
            invoiceTableAdapter.setItems(storageStub.invoicesReserv);
        } else if (Objects.equals(viewModel.title.get(), getString(R.string.text_list_ordered))) {
            //Server.orderedList(this);
            invoiceTableAdapter.setItems(storageStub.invoicesOrder);
        } else if (Objects.equals(viewModel.title.get(), getString(R.string.text_list_canceled))) {
            Server.canceledList(this);
            //invoiceTableAdapter.setItems(storageStub.invoicesCancel);
        } else if (Objects.equals(viewModel.title.get(), getString(R.string.text_list_shipped))) {
            //Server.shippedList(this);
            invoiceTableAdapter.setItems(storageStub.invoicesShip);
        } else {
            invoiceTableAdapter.setItems(storageStub.invoicesEmpty);
        }

        binding = DataBindingUtil.setContentView(this, R.layout.invoice_table);
        binding.setViewModel(viewModel);
        binding.list.setHasFixedSize(true);
        binding.list.setLayoutManager(new LinearLayoutManager(this));
        binding.list.setAdapter(invoiceTableAdapter);

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
