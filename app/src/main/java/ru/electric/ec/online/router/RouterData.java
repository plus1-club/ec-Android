package ru.electric.ec.online.router;

import android.content.Context;

import com.google.gson.internal.LinkedTreeMap;

import java.util.concurrent.Executors;

import ru.electric.ec.online.common.App;
import ru.electric.ec.online.common.Service;
import ru.electric.ec.online.data.DataService;
import ru.electric.ec.online.models.Info;
import ru.electric.ec.online.server.ServerData;
import ru.electric.ec.online.server.ServerNetwork;
import ru.electric.ec.online.server.ServerRun;
import ru.electric.ec.online.ui.enter.EnterViewModel;

public class RouterData {

    public static void restoreUser(EnterViewModel viewModel){
        Executors.newSingleThreadExecutor().execute(() ->
            DataService.restoreUser(viewModel));
    }

    public static void saveUser(EnterViewModel viewModel){
        Executors.newSingleThreadExecutor().execute(() ->
            DataService.saveUser(viewModel.login.get(), viewModel.password.get(), viewModel.save.get()));
    }

    public static void enterUser(Context context, EnterViewModel viewModel){
        ServerNetwork.getApi().enter(viewModel.login.get(), viewModel.password.get()).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::getEnter, 0));
    }

    public static void saveInfo(Info info){
        Executors.newSingleThreadExecutor().execute(() -> {
            Service.log(info.isError, info.message);
            DataService.setInfo(new Info(info.isError, info.show, info.message, info.activityName));
        });
    }

    public static void setToken(ServerData body){
        App.getModel().token = (String) ((LinkedTreeMap) body.data).get("user_token");
    }
}
