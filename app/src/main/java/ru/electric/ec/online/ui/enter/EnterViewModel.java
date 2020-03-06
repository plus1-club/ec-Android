package ru.electric.ec.online.ui.enter;

import android.content.Context;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import ru.electric.ec.online.router.RouterData;
import ru.electric.ec.online.router.RouterServer;
import ru.electric.ec.online.router.RouterView;
import ru.electric.ec.online.server.ServerData;

public class EnterViewModel {

    private static EnterViewModel mInstance;
    public ObservableField<String> login;
    public ObservableField<String> password;
    public ObservableBoolean save;

    private EnterViewModel() {
        login = new ObservableField<>();
        password = new ObservableField<>();
        save = new ObservableBoolean();
        RouterData.restoreUser(this);
    }

    // Получение единственного экземпляра класса
    public static EnterViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new EnterViewModel();
        }
        return mInstance;
    }

    public void onEnter(Context context) {
        RouterData.saveUser(this);
        RouterServer.enterUser(context, this);
    }

    public void onPhone(Context context, String phone){
        RouterView.openPhoneCall(context, phone);
    }

    public void onSave(){
        RouterData.saveUser(this);
    }

    public void enterOk(Context context, ServerData body) {
        if (RouterServer.isSuccess(body)) {
            RouterServer.setToken(body);
            RouterView.openMenu(context);
        } else {
            RouterView.onUnsuccessful(context, body);
        }
    }

    public void enterError(Context context, Throwable throwable) {
        RouterView.onError(context, throwable);
    }

    public void exitOk(Context context, ServerData body){
        if (RouterServer.isSuccess(body)) {
            RouterView.openEnter(context);
        } else {
            RouterView.onUnsuccessful(context, body);
        }
    }

    public void exitError(Context context, Throwable throwable) {
        RouterView.onError(context, throwable);
    }

}
