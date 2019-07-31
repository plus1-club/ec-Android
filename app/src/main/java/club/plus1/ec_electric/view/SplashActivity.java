package club.plus1.ec_electric.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import club.plus1.ec_electric.R;
import club.plus1.ec_electric.databinding.SplashBinding;
import club.plus1.ec_electric.viewmodel.SplashViewModel;

public class SplashActivity extends AppCompatActivity {

    SplashViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashBinding binding = DataBindingUtil.setContentView(this, R.layout.splash);
        viewModel = new SplashViewModel(this);
        binding.setViewModel(viewModel);
    }
}
