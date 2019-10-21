package club.plus1.ec_online.view.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Objects;

import club.plus1.ec_online.R;
import club.plus1.ec_online.databinding.RequestsBasketBinding;
import club.plus1.ec_online.model.storage.Stub;
import club.plus1.ec_online.viewmodel.adapters.RequestsBasketAdapter;
import club.plus1.ec_online.viewmodel.viewmodels.MenuViewModel;
import club.plus1.ec_online.viewmodel.viewmodels.RequestViewModel;

public class RequestsBasketActivity extends AppCompatActivity {

    RequestViewModel viewModel;
    MenuViewModel menuModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        menuModel = new MenuViewModel(this);
        viewModel = RequestViewModel.getInstance();
        Stub stub = Stub.getInstance();
        RequestsBasketAdapter requestsBasketAdapter = new RequestsBasketAdapter();
        requestsBasketAdapter.setItems(stub.basket);

        Bundle bundle = getIntent().getExtras();
        viewModel.title.set(Objects.requireNonNull(bundle).getString("title"));

        RequestsBasketBinding binding = DataBindingUtil.setContentView(this, R.layout.requests_basket);
        binding.setViewModel(viewModel);
        binding.list.setHasFixedSize(true);
        binding.list.setLayoutManager(new LinearLayoutManager(this));
        binding.list.setAdapter(requestsBasketAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
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