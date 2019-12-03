package ru.electric.ec.online.views;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.databinding.InvoiceTableBinding;
import ru.electric.ec.online.domains.Invoice;
import ru.electric.ec.online.models.ServerResponse;
import ru.electric.ec.online.viewadapters.InvoiceTableAdapter;
import ru.electric.ec.online.viewmodels.InvoiceTableViewModel;
import ru.electric.ec.online.viewmodels.NavigationViewModel;

public class InvoiceTableActivity extends AppCompatActivity {

    public InvoiceTableViewModel viewModel;
    public InvoiceTableBinding binding;
    NavigationViewModel navigationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = InvoiceTableViewModel.getInstance();
        InvoiceTableAdapter invoiceTableAdapter = new InvoiceTableAdapter();

        Bundle bundle = getIntent().getExtras();
        this.setTitle(Objects.requireNonNull(bundle).getString("title"));
        viewModel.title.set(Objects.requireNonNull(bundle).getString("title"));

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
            invoiceTableAdapter.setItems(new ArrayList<Invoice>());
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
