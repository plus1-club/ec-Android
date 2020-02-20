package ru.electric.ec.online.data;

import java.util.Date;

import ru.electric.ec.online.App;
import ru.electric.ec.online.models.User;

public class DataService {
    public static void createUser(String login, String password, boolean save){
        if (App.getDb() != null && login != null && !login.isEmpty()){
            User user = App.getDb().userDao().readUser(login, password);
            if (user == null) {
                user = new User();
                user.login = login;
                user.password = password;
                user.date = DateConverter.fromDate(new Date());
                user.save = save;
                App.getDb().userDao().create(user);
            } else {
                user.date = DateConverter.fromDate(new Date());
                user.save = save;
                App.getDb().userDao().update(user);
            }
        }
    }
}
