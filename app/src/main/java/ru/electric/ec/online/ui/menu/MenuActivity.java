package ru.electric.ec.online.ui.menu;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import ru.electric.ec.online.R;
import ru.electric.ec.online.common.Service;
import ru.electric.ec.online.databinding.MenuBinding;
import ru.electric.ec.online.models.Info;
import ru.electric.ec.online.router.RouterData;
import ru.electric.ec.online.router.RouterServer;
import ru.electric.ec.online.router.RouterView;
import ru.electric.ec.online.server.ServerData;

public class MenuActivity extends AppCompatActivity {

    public MenuItemViewModel viewModel;
    MenuViewModel navigationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new MenuItemViewModel(this);

        MenuBinding binding = DataBindingUtil.setContentView(this, R.layout.menu);
        binding.setViewModel(viewModel);

        navigationModel = new MenuViewModel(
                this,  binding.drawer, binding.include.toolbar, binding.navigator);
        // Установить Toolbar для замены ActionBar'а.
        setSupportActionBar(navigationModel.toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (navigationModel.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        navigationModel.actionBar.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        navigationModel.actionBar.onConfigurationChanged(newConfig);
    }

    public void exitOk(ServerData body) {
        if (RouterServer.isSuccess(body)) {
            RouterView.openEnter(this);
        } else {
            String message = Service.getStr(R.string.text_response_error, body.error, body.message);
            Info info = new Info(false, true, message);
            RouterData.saveInfo(info);
            RouterView.openInfo(this, info);
        }
    }

    public void exitError(Throwable throwable) {
        Info info = new Info(false, true, throwable.getMessage());
        RouterData.saveInfo(info);
        RouterView.openInfo(this, info);
    }
}
