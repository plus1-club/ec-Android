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
import club.plus1.ec_online.databinding.InvoiceDetailsBinding;
import club.plus1.ec_online.model.Stub;
import club.plus1.ec_online.viewmodel.InvoiceDetailsViewModel;
import club.plus1.ec_online.viewmodel.MenuViewModel;
import club.plus1.ec_online.viewmodel.adapters.InvoiceDetailsAdapter;

public class InvoiceDetailsActivity extends AppCompatActivity {

    InvoiceDetailsViewModel viewModel;
    MenuViewModel menuModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        menuModel = new MenuViewModel(this);
        viewModel = InvoiceDetailsViewModel.getInstance();
        Stub stub = Stub.getInstance();
        InvoiceDetailsAdapter invoiceDetailsAdapter = new InvoiceDetailsAdapter();
        invoiceDetailsAdapter.setItems(stub.details);

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
