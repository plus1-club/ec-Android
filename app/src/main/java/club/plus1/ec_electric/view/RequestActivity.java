package club.plus1.ec_electric.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import club.plus1.ec_electric.R;
import club.plus1.ec_electric.databinding.RequestBinding;
import club.plus1.ec_electric.viewmodel.RequestViewModel;
import club.plus1.ec_electric.viewmodel.adapters.RequestFragmentAdapter;

public class RequestActivity extends AppCompatActivity {

    RequestViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = RequestViewModel.getInstance();

        RequestBinding binding = DataBindingUtil.setContentView(this, R.layout.request);
        binding.setViewModel(viewModel);
        binding.viewpager.setAdapter(new RequestFragmentAdapter(getSupportFragmentManager()));
        binding.tabs.setupWithViewPager(binding.viewpager);
    }
}
