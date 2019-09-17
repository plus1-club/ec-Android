package club.plus1.ec_online.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import club.plus1.ec_online.R;
import club.plus1.ec_online.databinding.SplashBinding;
import club.plus1.ec_online.viewmodel.SplashViewModel;

public class SplashActivity extends AppCompatActivity {

    SplashViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashBinding binding = DataBindingUtil.setContentView(this, R.layout.splash);
        viewModel = new SplashViewModel(this);
        viewModel.startEnterActivity(this);
        binding.setViewModel(viewModel);
    }
}
