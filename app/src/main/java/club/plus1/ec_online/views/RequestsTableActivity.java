package club.plus1.ec_online.views;

import android.content.res.Configuration;
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
import club.plus1.ec_online.viewmodels.NavigationViewModel;
import club.plus1.ec_online.viewmodels.RequestViewModel;

public class RequestsTableActivity extends AppCompatActivity {

    RequestViewModel viewModel;
    NavigationViewModel navigationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = RequestViewModel.getInstance();
        Bundle bundle = getIntent().getExtras();
        this.setTitle(Objects.requireNonNull(bundle).getString("title"));

        StorageStub storageStub = StorageStub.getInstance();
        RequestsTableAdapter requestsTableAdapter = new RequestsTableAdapter();
        requestsTableAdapter.setItems(storageStub.getRequests(Objects.requireNonNull(viewModel.product.get()), viewModel.count.get()));

        RequestsTableBinding binding = DataBindingUtil.setContentView(this, R.layout.requests_table);
        binding.setViewModel(viewModel);
        binding.list.setHasFixedSize(true);
        binding.list.setLayoutManager(new LinearLayoutManager(this));
        binding.list.setAdapter(requestsTableAdapter);

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
