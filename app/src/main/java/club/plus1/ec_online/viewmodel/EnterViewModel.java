package club.plus1.ec_online.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import club.plus1.ec_online.model.App;
import club.plus1.ec_online.model.ServerModel;
import club.plus1.ec_online.view.MenuActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterViewModel {
    public String login;
    public String password;

    public EnterViewModel(Context context) {
    }

    public void onEnter(final Context context) {

        App.getApi().enter().enqueue(new Callback<ServerModel>() {
            @Override
            public void onResponse(@NonNull Call<ServerModel> call, @NonNull Response<ServerModel> response) {
                if (response.isSuccessful()) {
                    App.log(context, false, "response: " + response.body());
                } else {
                    App.log(context, false, "response code " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerModel> call, @NonNull Throwable t) {
                App.log(context, true, "failure " + t);
            }
        });

        Intent intent = new Intent(context, MenuActivity.class);
        context.startActivity(intent);
    }

    public void onPhone(Context context, String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }
}
