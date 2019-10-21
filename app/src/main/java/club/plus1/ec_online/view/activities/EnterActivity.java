package club.plus1.ec_online.view.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import club.plus1.ec_online.R;
import club.plus1.ec_online.databinding.EnterBinding;
import club.plus1.ec_online.viewmodel.viewmodels.EnterViewModel;

public class EnterActivity extends AppCompatActivity {

    EnterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = EnterViewModel.getInstance();
        EnterBinding binding = DataBindingUtil.setContentView(this, R.layout.enter);
        binding.setViewModel(viewModel);
    }
}
