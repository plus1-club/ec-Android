package ru.electric.ec.online.router;

import android.annotation.SuppressLint;

import com.google.gson.internal.LinkedTreeMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.electric.ec.online.common.App;
import ru.electric.ec.online.server.ServerData;
import ru.electric.ec.online.server.ServerNetwork;
import ru.electric.ec.online.ui.enter.EnterActivity;
import ru.electric.ec.online.ui.enter.EnterViewModel;
import ru.electric.ec.online.ui.menu.MenuActivity;

@SuppressWarnings("ResultOfMethodCallIgnored")
@SuppressLint("CheckResult")
public class RouterServer {

    public static void setToken(ServerData body){
        App.getModel().token = (String) ((LinkedTreeMap) body.data).get("user_token");
    }

    public static boolean isSuccess(ServerData data){
        if (data != null)
        {
            return data.success && data.error.isEmpty();
        } else {
            return false;
        }
    }

    public static void enterUser(EnterActivity context, EnterViewModel viewModel){
        ServerNetwork.getApi()
            .enter(viewModel.login.get(), viewModel.password.get())
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(context::enterOk, context::enterError);
    }

    public static void getExit(MenuActivity context) {
        ServerNetwork.getApi()
            .exit(App.getModel().token)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(context::exitOk, context::exitError);
    }


    /*
    public static Callback<ServerData> callback(final Context context, ServerRunInterface func, int number){
        return new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    handler.post(() -> func.run(context, response, number));
                } else {
                    RouterView.openInfo(context, new Info(false, true,
                            Service.getStr(R.string.text_response_error, response.code(), response.message())));
                }
            }
            @Override
            public void onFailure(@NonNull Call<ServerData> call, @NonNull Throwable t) {
                RouterView.openInfo(context, new Info(true, true,
                        Service.getStr(R.string.text_response_failure, t)));
            }
        };
    }
     */

}
