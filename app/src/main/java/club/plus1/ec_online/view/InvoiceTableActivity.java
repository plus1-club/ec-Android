package club.plus1.ec_online.view;

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
import club.plus1.ec_online.model.Stub;
import club.plus1.ec_online.viewmodel.InvoiceTableViewModel;
import club.plus1.ec_online.viewmodel.MenuViewModel;
import club.plus1.ec_online.viewmodel.adapters.InvoiceTableAdapter;

public class InvoiceTableActivity extends AppCompatActivity {

    InvoiceTableViewModel viewModel;
    MenuViewModel menuModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        menuModel = new MenuViewModel(this);
        viewModel = InvoiceTableViewModel.getInstance();
        Stub stub = Stub.getInstance();
        InvoiceTableAdapter invoiceTableAdapter = new InvoiceTableAdapter();

        Bundle bundle = getIntent().getExtras();
        viewModel.title.set(Objects.requireNonNull(bundle).getString("title"));
        if (Objects.equals(viewModel.title.get(), getString(R.string.title_list_unconfirmed))) {
            invoiceTableAdapter.setItems(stub.invoicesUnconf);
        } else if (Objects.equals(viewModel.title.get(), getString(R.string.title_list_reserves))) {
            invoiceTableAdapter.setItems(stub.invoicesReserv);
        } else if (Objects.equals(viewModel.title.get(), getString(R.string.title_list_orders))) {
            invoiceTableAdapter.setItems(stub.invoicesOrder);
        } else if (Objects.equals(viewModel.title.get(), getString(R.string.title_list_canceled))) {
            invoiceTableAdapter.setItems(stub.invoicesCancel);
        } else if (Objects.equals(viewModel.title.get(), getString(R.string.title_list_history))) {
            invoiceTableAdapter.setItems(stub.invoicesShip);
        } else {
            invoiceTableAdapter.setItems(stub.invoicesEmpty);
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
