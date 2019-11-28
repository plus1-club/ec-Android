package club.plus1.ec_online.views;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Objects;

import club.plus1.ec_online.App;
import club.plus1.ec_online.R;
import club.plus1.ec_online.databinding.RequestsSearchBinding;
import club.plus1.ec_online.models.ServerResponse;
import club.plus1.ec_online.viewadapters.RequestsSearchAdapter;
import club.plus1.ec_online.viewmodels.NavigationViewModel;
import club.plus1.ec_online.viewmodels.RequestViewModel;

public class RequestsSearchActivity extends AppCompatActivity {

    RequestViewModel viewModel;
    NavigationViewModel navigationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = RequestViewModel.getInstance();
        Bundle bundle = getIntent().getExtras();
        this.setTitle(Objects.requireNonNull(bundle).getString("title"));

        RequestsSearchAdapter requestsSearchAdapter = new RequestsSearchAdapter();
        requestsSearchAdapter.setItems(App.model.search);
        viewModel.searchAdapter.set(requestsSearchAdapter);

        RequestsSearchBinding binding = DataBindingUtil.setContentView(this, R.layout.requests_search);
        binding.setViewModel(viewModel);
        binding.list.setHasFixedSize(true);
        binding.list.setLayoutManager(new LinearLayoutManager(this));
        binding.list.setAdapter(requestsSearchAdapter);

        navigationModel = new NavigationViewModel(
                this,  binding.drawer, binding.include.toolbar, binding.navigator);
        // Установить Toolbar для замены ActionBar'а.
        setSupportActionBar(navigationModel.toolbar);

        ServerResponse.byCode(this, viewModel.product.get(), viewModel.count.get(), viewModel.isFullSearch.get());
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
