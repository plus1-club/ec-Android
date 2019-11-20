package club.plus1.ec_online.views;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Objects;

import club.plus1.ec_online.R;
import club.plus1.ec_online.databinding.RequestsTableBinding;
import club.plus1.ec_online.models.StorageStub;
import club.plus1.ec_online.viewadapters.RequestsTableAdapter;
import club.plus1.ec_online.viewmodels.MenuViewModel;
import club.plus1.ec_online.viewmodels.RequestViewModel;

public class RequestsTableActivity extends AppCompatActivity {

    RequestViewModel viewModel;
    MenuViewModel menuModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        menuModel = new MenuViewModel(this);
        viewModel = RequestViewModel.getInstance();
        StorageStub storageStub = StorageStub.getInstance();
        RequestsTableAdapter requestsTableAdapter = new RequestsTableAdapter();
        requestsTableAdapter.setItems(storageStub.getRequests(Objects.requireNonNull(viewModel.product.get()), viewModel.count.get()));

        Bundle bundle = getIntent().getExtras();
        viewModel.title.set(Objects.requireNonNull(bundle).getString("title"));

        RequestsTableBinding binding = DataBindingUtil.setContentView(this, R.layout.requests_table);
        binding.setViewModel(viewModel);
        binding.list.setHasFixedSize(true);
        binding.list.setLayoutManager(new LinearLayoutManager(this));
        binding.list.setAdapter(requestsTableAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuViewModel.PrepareMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(menuModel.onOptionsItemSelected(this, item)) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}
