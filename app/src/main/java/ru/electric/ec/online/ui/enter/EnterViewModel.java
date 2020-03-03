package ru.electric.ec.online.ui.enter;

import android.content.Context;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import ru.electric.ec.online.router.RouterData;
import ru.electric.ec.online.router.RouterView;

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
        RouterData.enterUser(context, this);
    }

    public void onPhone(Context context, String phone){
        RouterView.openPhoneCall(context, phone);
    }

    public void onSave(){
        RouterData.saveUser(this);
    }
}
