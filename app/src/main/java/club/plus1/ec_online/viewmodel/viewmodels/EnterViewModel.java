package club.plus1.ec_online.viewmodel.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.google.gson.internal.LinkedTreeMap;

import java.util.Objects;

import club.plus1.ec_online.App;
import club.plus1.ec_online.model.web.Network;
import club.plus1.ec_online.model.web.Server;
import club.plus1.ec_online.view.activities.MenuActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public void onEnter(final Context context) {

        Network.getInstance();
        Network.getApi().enter(login.get(), password.get()).enqueue(new Callback<Server>() {
            @Override
            public void onResponse(@NonNull Call<Server> call, @NonNull Response<Server> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).isSuccess()) {
                        Object data = response.body().getData();
                        App.model.token = (String) ((LinkedTreeMap) data).get("user_token");
                        Intent intent = new Intent(context, MenuActivity.class);
                        context.startActivity(intent);
                    } else {
                        App.log(context, false,
                                " (" + response.body().getError() + ")"
                                        + response.body().getMessage());
                    }
                } else {
                    App.log(context, false, "response code " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Server> call, @NonNull Throwable t) {
                App.log(context, true, "failure " + t);
            }
        });
    }

    public void onPhone(Context context, String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }
}
