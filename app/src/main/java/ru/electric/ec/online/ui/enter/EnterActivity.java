package ru.electric.ec.online.ui.enter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import ru.electric.ec.online.R;
import ru.electric.ec.online.databinding.EnterBinding;

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
