package club.plus1.ec_online.views;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Objects;

import club.plus1.ec_online.R;
import club.plus1.ec_online.databinding.InvoiceTableBinding;
import club.plus1.ec_online.models.StorageStub;
import club.plus1.ec_online.viewadapters.InvoiceTableAdapter;
import club.plus1.ec_online.viewmodels.InvoiceTableViewModel;
import club.plus1.ec_online.viewmodels.MenuViewModel;

public class InvoiceTableActivity extends AppCompatActivity {

    InvoiceTableViewModel viewModel;
    MenuViewModel menuModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        menuModel = new MenuViewModel(this);
        viewModel = InvoiceTableViewModel.getInstance();
        StorageStub storageStub = StorageStub.getInstance();
        InvoiceTableAdapter invoiceTableAdapter = new InvoiceTableAdapter();

        Bundle bundle = getIntent().getExtras();
        viewModel.title.set(Objects.requireNonNull(bundle).getString("title"));
        if (Objects.equals(viewModel.title.get(), getString(R.string.title_list_unconfirmed))) {
            invoiceTableAdapter.setItems(storageStub.invoicesUnconf);
        } else if (Objects.equals(viewModel.title.get(), getString(R.string.title_list_reserves))) {
            invoiceTableAdapter.setItems(storageStub.invoicesReserv);
        } else if (Objects.equals(viewModel.title.get(), getString(R.string.title_list_orders))) {
            invoiceTableAdapter.setItems(storageStub.invoicesOrder);
        } else if (Objects.equals(viewModel.title.get(), getString(R.string.title_list_canceled))) {
            invoiceTableAdapter.setItems(storageStub.invoicesCancel);
        } else if (Objects.equals(viewModel.title.get(), getString(R.string.title_list_history))) {
            invoiceTableAdapter.setItems(storageStub.invoicesShip);
        } else {
            invoiceTableAdapter.setItems(storageStub.invoicesEmpty);
        }

        InvoiceTableBinding binding = DataBindingUtil.setContentView(this, R.layout.invoice_table);
        binding.setViewModel(viewModel);
        binding.list.setHasFixedSize(true);
        binding.list.setLayoutManager(new LinearLayoutManager(this));
        binding.list.setAdapter(invoiceTableAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (menuModel.onOptionsItemSelected(this, item)) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
