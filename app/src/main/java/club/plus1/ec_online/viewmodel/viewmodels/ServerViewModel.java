package club.plus1.ec_online.viewmodel.viewmodels;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import club.plus1.ec_online.App;
import club.plus1.ec_online.R;
import club.plus1.ec_online.domain.Request;
import club.plus1.ec_online.model.web.Network;
import club.plus1.ec_online.model.web.Server;
import club.plus1.ec_online.view.activities.EnterActivity;
import club.plus1.ec_online.view.activities.MenuActivity;
import club.plus1.ec_online.view.activities.RequestsBasketActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ServerViewModel {

    static void getEnter(final Context context, String login, String password) {
        Network.getInstance();
        Network.getApi().enter(login, password).enqueue(new Callback<Server>() {
            @Override
            public void onResponse(@NonNull Call<Server> call, @NonNull Response<Server> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).isSuccess()) {
                        Object data = response.body().getData();
                        App.model.token = (String) ((LinkedTreeMap) data).get("user_token");
                        Intent intent = new Intent(context, MenuActivity.class);
                        context.startActivity(intent);
                    } else {
                        String message = " (" + response.body().getError() + ")"
                                + response.body().getMessage();
                        App.log(context, false, true, message);
                    }
                } else {
                    App.log(context, false, true, "response code " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Server> call, @NonNull Throwable t) {
                App.log(context, true, true, "failure " + t);
            }
        });

    }

    static void getExit(final Context context) {
        Network.getApi().exit(App.model.token).enqueue(new Callback<Server>() {
            @Override
            public void onResponse(@NonNull Call<Server> call, @NonNull Response<Server> response) {
                if (!response.isSuccessful()) {
                    App.log(context, false, true, "response code " + response.code());
                }
                Intent intent = new Intent(context, EnterActivity.class);
                context.startActivity(intent);
            }

            @Override
            public void onFailure(@NonNull Call<Server> call, @NonNull Throwable t) {
                App.log(context, true, true, "failure " + t);
            }
        });
    }

    static void getBasket(final Context context) {
        Network.getApi().getBasket(App.model.token).enqueue(new Callback<Server>() {
                @Override
                public void onResponse(@NonNull Call<Server> call, @NonNull Response<Server> response) {
                    if (response.isSuccessful()) {
                        App.model.basket.clear();
                        if (response.body() != null && response.body().getError().isEmpty()) {
                            List<?> data = (List<?>) response.body().getData();
                            for (Object element : data) {
                                @SuppressWarnings("unchecked")
                                Map<String, String> el = (LinkedTreeMap<String, String>) element;
                                Request request = new Request(
                                        el.get("product"),
                                        Integer.parseInt(Objects.requireNonNull(el.get("requestCount"))),
                                        Integer.parseInt(Objects.requireNonNull(el.get("stockCount"))),
                                        Double.parseDouble(Objects.requireNonNull(el.get("price"))));
                                App.model.basket.add(request);
                             }
                        }
                    }
                    Intent intent = new Intent(context, RequestsBasketActivity.class);
                    intent.putExtra("title", context.getString(R.string.title_basket));
                    context.startActivity(intent);
                }

                @Override
                public void onFailure(@NonNull Call<Server> call, @NonNull Throwable t) {
                    App.log(context, true, true, "failure " + t);
                }
            }
        );
    }

    static void postBasket(final Context context, List<Request> requests){
        Network.getApi().postBasket(App.model.token, requests).enqueue(new Callback<Server>() {
            @Override
            public void onResponse(@NonNull Call<Server> call, @NonNull Response<Server> response) {
                if (!response.isSuccessful()) {
                    App.log(context, false, true, "response code " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Server> call, @NonNull Throwable t) {
                App.log(context, true, true, "failure " + t);
            }
        });

    }
}
