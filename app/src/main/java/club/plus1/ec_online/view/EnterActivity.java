package club.plus1.ec_online.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import club.plus1.ec_online.R;
import club.plus1.ec_online.databinding.EnterBinding;
import club.plus1.ec_online.viewmodel.EnterViewModel;

public class EnterActivity extends AppCompatActivity {

    EnterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EnterBinding binding = DataBindingUtil.setContentView(this, R.layout.enter);
        viewModel = new EnterViewModel(this);
        binding.setViewModel(viewModel);
    }
}
