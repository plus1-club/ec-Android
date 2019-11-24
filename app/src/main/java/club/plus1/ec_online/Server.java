package club.plus1.ec_online;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import club.plus1.ec_online.domains.Request;
import club.plus1.ec_online.models.ServerData;
import club.plus1.ec_online.models.ServerNetwork;
import club.plus1.ec_online.views.EnterActivity;
import club.plus1.ec_online.views.MenuActivity;
import club.plus1.ec_online.views.RequestsBasketActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Server {

    public static void getEnter(final Context context, String login, String password) {
        ServerNetwork.getInstance();
        ServerNetwork.getApi().enter(login, password).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull Response<ServerData> response) {
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
            public void onFailure(@NonNull Call<ServerData> call, @NonNull Throwable t) {
                App.log(context, true, true, "failure " + t);
            }
        });

    }

    public static void getExit(final Context context) {
        ServerNetwork.getApi().exit(App.model.token).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull Response<ServerData> response) {
                if (!response.isSuccessful()) {
                    App.log(context, false, true, "response code " + response.code());
                }
                Intent intent = new Intent(context, EnterActivity.class);
                context.startActivity(intent);
            }

            @Override
            public void onFailure(@NonNull Call<ServerData> call, @NonNull Throwable t) {
                App.log(context, true, true, "failure " + t);
            }
        });
    }

    public static void getBasket(final Context context) {
        ServerNetwork.getApi().getBasket(App.model.token).enqueue(new Callback<ServerData>() {
                @Override
                public void onResponse(@NonNull Call<ServerData> call, @NonNull Response<ServerData> response) {
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
                                        Double.parseDouble(Objects.requireNonNull(el.get("price"))),
                                        true);
                                App.model.basket.add(request);
                             }
                        }
                    }
                    Intent intent = new Intent(context, RequestsBasketActivity.class);
                    intent.putExtra("title", context.getString(R.string.text_basket));
                    context.startActivity(intent);
                }

                @Override
                public void onFailure(@NonNull Call<ServerData> call, @NonNull Throwable t) {
                    App.log(context, true, true, "failure " + t);
                }
            }
        );
    }

    public static void postBasket(final Context context, List<Request> requests){
        ServerNetwork.getApi().postBasket(App.model.token, requests).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull Response<ServerData> response) {
                if (!response.isSuccessful()) {
                    App.log(context, false, true, "response code " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerData> call, @NonNull Throwable t) {
                App.log(context, true, true, "failure " + t);
            }
        });
    }

    public static void putBasket(final Context context, List<Request> requests){
        ServerNetwork.getApi().putBasket(App.model.token, requests).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull Response<ServerData> response) {
                if (!response.isSuccessful()) {
                    App.log(context, false, true, "response code " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerData> call, @NonNull Throwable t) {
                App.log(context, true, true, "failure " + t);
            }
        });
    }


    public static void deleteBasket(final Context context){
        ServerNetwork.getApi().deleteBasket(App.model.token).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull Response<ServerData> response) {
                if (!response.isSuccessful()) {
                    App.log(context, false, true, "response code " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerData> call, @NonNull Throwable t) {
                App.log(context, true, true, "failure " + t);
            }
        });
    }

}
