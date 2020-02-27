package ru.electric.ec.online.data;

import java.util.Date;

import ru.electric.ec.online.common.App;
import ru.electric.ec.online.models.User;

public class DataService {
    public static void createUser(String login, String password, boolean save){
        if (App.getDb() != null){
            if (login != null && !login.isEmpty()){
                User user = App.getDb().userDao().readUser(login);
                if (user == null) {
                    user = new User();
                    user.login = login;
                    user.password = password;
                    user.date = DateConverter.fromDate(new Date());
                    user.save = save;
                    App.getDb().userDao().create(user);
                } else if (App.getDb().userDao().readUser(login, password) != null) {
                    user.date = DateConverter.fromDate(new Date());
                    user.save = save;
                    App.getDb().userDao().update(user);
                }
            }
        }
    }
}
