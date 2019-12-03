package ru.electric.ec.online.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import ru.electric.ec.online.R;
import ru.electric.ec.online.databinding.SplashBinding;
import ru.electric.ec.online.viewmodels.SplashViewModel;

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
