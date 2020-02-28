package ru.electric.ec.online.ui.enter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import java.util.concurrent.Executors;

import ru.electric.ec.online.common.App;
import ru.electric.ec.online.data.DataService;
import ru.electric.ec.online.models.User;
import ru.electric.ec.online.server.ServerResponse;

public class EnterViewModel {

    private static EnterViewModel mInstance;
    public ObservableField<String> login;
    public ObservableField<String> password;
    public ObservableBoolean save;

    private EnterViewModel() {
        login = new ObservableField<>();
        password = new ObservableField<>();
        save = new ObservableBoolean();
        Executors.newSingleThreadExecutor().execute(this::saveAndRestore);
    }

    // Получение единственного экземпляра класса
    public static EnterViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new EnterViewModel();
        }
        return mInstance;
    }

    private void saveAndRestore(){
        User user = App.getDb().userDao().readLast();
        if (user != null ){
            save.set(user.save);
            if (user.save){
                login.set(user.login);
                password.set(user.password);
                App.getModel().token = user.token;
            }
        }
    }

    public void onEnter(Context context) {
        ServerResponse.getEnter(context, login.get(), password.get(), save.get());
    }

    public void onPhone(Context context, String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }

    public void onSave(){
        Executors.newSingleThreadExecutor().execute(() ->
            DataService.createUser(login.get(), password.get(), save.get()));
    }
}
