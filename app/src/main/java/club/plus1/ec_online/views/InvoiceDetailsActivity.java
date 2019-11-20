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
import club.plus1.ec_online.databinding.InvoiceDetailsBinding;
import club.plus1.ec_online.viewadapters.InvoiceDetailsAdapter;
import club.plus1.ec_online.viewmodels.InvoiceDetailsViewModel;
import club.plus1.ec_online.viewmodels.InvoiceTableViewModel;
import club.plus1.ec_online.viewmodels.MenuViewModel;

public class InvoiceDetailsActivity extends AppCompatActivity {

    InvoiceDetailsViewModel viewModel;
    InvoiceTableViewModel parent;
    MenuViewModel menuModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        menuModel = new MenuViewModel(this);
        viewModel = InvoiceDetailsViewModel.getInstance();
        parent = InvoiceTableViewModel.getInstance();
        InvoiceDetailsAdapter invoiceDetailsAdapter = new InvoiceDetailsAdapter();

        InvoiceDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.invoice_details);
        binding.setViewModel(viewModel);
        binding.list.setHasFixedSize(true);
        binding.list.setLayoutManager(new LinearLayoutManager(this));
        binding.list.setAdapter(invoiceDetailsAdapter);

        Bundle bundle = getIntent().getExtras();
        viewModel.number.set(Objects.requireNonNull(bundle).getInt("number"));
        viewModel.date.set(bundle.getString("date"));
        viewModel.sum.set(bundle.getDouble("sum"));
        viewModel.status.set(bundle.getString("status"));
        viewModel.waybill.set(bundle.getInt("waybill"));
        invoiceDetailsAdapter.setItems(parent.invoices.get(bundle.getInt("position")).details);
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
