package club.plus1.ec_online.viewmodel.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.databinding.ObservableField;

public class EnterViewModel {

    private static EnterViewModel mInstance;
    public ObservableField<String> login;
    public ObservableField<String> password;

    private EnterViewModel() {
        login = new ObservableField<>();
        password = new ObservableField<>();
    }

    // Получение единственного экземпляра класса
    public static EnterViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new EnterViewModel();
        }
        return mInstance;
    }

    public void onEnter(Context context) {
        ServerViewModel.getEnter(context, login.get(), password.get());
    }

    public void onPhone(Context context, String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }
}
