package club.plus1.ec_electric.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import club.plus1.ec_electric.R;
import club.plus1.ec_electric.databinding.RequestsBasketBinding;
import club.plus1.ec_electric.model.Stub;
import club.plus1.ec_electric.viewmodel.RequestViewModel;
import club.plus1.ec_electric.viewmodel.adapters.RequestsBasketAdapter;

public class RequestsBasketActivity extends AppCompatActivity {

    RequestViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = RequestViewModel.getInstance();
        Stub stub = new Stub();
        RequestsBasketAdapter requestsBasketAdapter = new RequestsBasketAdapter();
        requestsBasketAdapter.setItems(stub.requests);

        RequestsBasketBinding binding = DataBindingUtil.setContentView(this, R.layout.requests_basket);
        binding.setViewModel(viewModel);
        binding.list.setHasFixedSize(true);
        binding.list.setLayoutManager(new LinearLayoutManager(this));
        binding.list.setAdapter(requestsBasketAdapter);
    }
}
