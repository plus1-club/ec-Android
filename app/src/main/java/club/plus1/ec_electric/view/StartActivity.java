package club.plus1.ec_electric.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import club.plus1.ec_electric.R;
import club.plus1.ec_electric.databinding.StartBinding;
import club.plus1.ec_electric.viewmodel.StartViewModel;

public class StartActivity extends AppCompatActivity {

    StartViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartBinding binding = DataBindingUtil.setContentView(this, R.layout.start);
        viewModel = new StartViewModel(this);
        binding.setViewModel(viewModel);

    }
}
