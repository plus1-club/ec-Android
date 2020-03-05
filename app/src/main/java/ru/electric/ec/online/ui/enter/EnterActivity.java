package ru.electric.ec.online.ui.enter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import ru.electric.ec.online.R;
import ru.electric.ec.online.common.Service;
import ru.electric.ec.online.databinding.EnterBinding;
import ru.electric.ec.online.models.Info;
import ru.electric.ec.online.router.RouterData;
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
            String message = Service.getStr(R.string.text_response_error, body.error, body.message);
            Info info = new Info(false, true, message);
            RouterData.saveInfo(info);
            RouterView.openInfo(this, info);
        }
    }

    public void enterError(Throwable throwable) {
        Info info = new Info(false, true, throwable.getMessage());
        RouterData.saveInfo(info);
        RouterView.openInfo(this, info);
    }

}
