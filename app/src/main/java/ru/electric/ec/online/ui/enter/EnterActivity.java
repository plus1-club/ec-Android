package ru.electric.ec.online.ui.enter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import ru.electric.ec.online.R;
import ru.electric.ec.online.databinding.EnterBinding;
import ru.electric.ec.online.router.RouterServer;
import ru.electric.ec.online.router.RouterView;
import ru.electric.ec.online.server.ServerData;

public class EnterActivity extends AppCompatActivity {

    EnterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = EnterViewModel.getInstance();
        EnterBinding binding = DataBindingUtil.setContentView(this, R.layout.enter);
        binding.setViewModel(viewModel);
    }

    public void enterOk(ServerData body) {
        if (RouterServer.isSuccess(body)) {
            RouterServer.setToken(body);
            RouterView.openMenu(this);
        } else {
            RouterView.onUnsuccessful(this, body);
        }
    }

    public void enterError(Throwable throwable) {
        RouterView.onError(this, throwable);
    }
}
