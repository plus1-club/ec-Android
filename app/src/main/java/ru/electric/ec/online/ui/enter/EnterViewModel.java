package ru.electric.ec.online.ui.enter;

import android.content.Context;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import ru.electric.ec.online.data.DataRouter;
import ru.electric.ec.online.server.ServerData;
import ru.electric.ec.online.server.ServerRouter;
import ru.electric.ec.online.ui.ViewRouter;

public class EnterViewModel {

    private static EnterViewModel mInstance;
    public ObservableField<String> login;
    public ObservableField<String> password;
    public ObservableBoolean save;

    private EnterViewModel() {
        login = new ObservableField<>();
        password = new ObservableField<>();
        save = new ObservableBoolean();
        DataRouter.restoreUser(this);
    }

    // Получение единственного экземпляра класса
    public static EnterViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new EnterViewModel();
        }
        return mInstance;
    }

    public void onEnter(Context context) {
        DataRouter.saveUser(this);
        ServerRouter.enterUser(context, this);
    }

    public void onPhone(Context context, String phone){
        ViewRouter.openPhoneCall(context, phone);
    }

    public void onSave(){
        DataRouter.saveUser(this);
    }

    public void enterOk(Context context, ServerData body) {
        if (ServerRouter.isSuccess(body)) {
            ServerRouter.setToken(body);
            ViewRouter.openMenu(context);
        } else {
            ViewRouter.onUnsuccessful(context, body);
        }
    }

    public void enterError(Context context, Throwable throwable) {
        ViewRouter.onError(context, throwable);
    }

    public void exitOk(Context context, ServerData body){
        if (ServerRouter.isSuccess(body)) {
            ViewRouter.openEnter(context);
        } else {
            ViewRouter.onUnsuccessful(context, body);
        }
    }

    public void exitError(Context context, Throwable throwable) {
        ViewRouter.onError(context, throwable);
    }

}
