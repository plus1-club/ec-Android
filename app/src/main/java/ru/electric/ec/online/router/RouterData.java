package ru.electric.ec.online.router;

import java.util.concurrent.Executors;

import ru.electric.ec.online.common.Service;
import ru.electric.ec.online.data.DataService;
import ru.electric.ec.online.models.Info;
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

    public static void saveInfo(Info info){
        Executors.newSingleThreadExecutor().execute(() -> {
            Service.log(info.isError, info.message);
            DataService.setInfo(new Info(info.isError, info.show, info.message, info.activityName));
        });
    }
}
