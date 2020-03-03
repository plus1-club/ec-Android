package ru.electric.ec.online.data;

import java.util.Date;
import java.util.Objects;

import ru.electric.ec.online.common.App;
import ru.electric.ec.online.models.Info;
import ru.electric.ec.online.models.User;
import ru.electric.ec.online.ui.enter.EnterViewModel;

public class DataService {

    public static void saveUser(String login, String password, Boolean save){
        if (App.getDb() != null){
            if (login != null && !Objects.requireNonNull(login).isEmpty()){
                User user = App.getDb().userDao().readUser(login);
                if (user == null) {
                    user = new User();
                    user.login = login;
                    user.password = password;
                    user.save = save;
                    user.date = DateConverter.fromDate(new Date());
                    App.getDb().userDao().create(user);
                } else if (App.getDb().userDao().readUser(login, password) != null) {
                    user.save = save;
                    user.date = DateConverter.fromDate(new Date());
                    App.getDb().userDao().update(user);
                }
            }
        }
    }

    public static void restoreUser(EnterViewModel viewModel){
        User user = App.getDb().userDao().readLast();
        if (user != null ) {
            viewModel.save.set(user.save);
            if (user.save) {
                viewModel.login.set(user.login);
                viewModel.password.set(user.password);
                App.getModel().token = user.token;
            }
        }
    }

    public static void setInfo(Info info){
        App.getDb().infoDao().create(info);
    }

    public static Info getInfo(){
        return App.getDb().infoDao().readLast();
    }
}
