package club.plus1.ec_online.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import club.plus1.ec_online.R;
import club.plus1.ec_online.databinding.RequestsTableBinding;
import club.plus1.ec_online.model.Stub;
import club.plus1.ec_online.viewmodel.RequestViewModel;
import club.plus1.ec_online.viewmodel.adapters.RequestsTableAdapter;

public class RequestsTableActivity extends AppCompatActivity {

    RequestViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = RequestViewModel.getInstance();
        Stub stub = new Stub();
        RequestsTableAdapter requestsTableAdapter = new RequestsTableAdapter();
        requestsTableAdapter.setItems(stub.requests);

        RequestsTableBinding binding = DataBindingUtil.setContentView(this, R.layout.requests_table);
        binding.setViewModel(viewModel);
        binding.list.setHasFixedSize(true);
        binding.list.setLayoutManager(new LinearLayoutManager(this));
        binding.list.setAdapter(requestsTableAdapter);
    }
}
