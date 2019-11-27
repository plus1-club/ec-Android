package club.plus1.ec_online.models;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.List;

import club.plus1.ec_online.App;
import club.plus1.ec_online.domains.Request;
import club.plus1.ec_online.viewmodels.ServerRun;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServerResponse {

    private static Handler handler;

    public static void getEnter(final Context context, String login, String password) {
        handler = new Handler(Looper.getMainLooper());
        ServerNetwork.getInstance();
        ServerNetwork.getApi().enter(login, password).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ServerRun.getInstance().getEnter(context, response);
                        }
                    });
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
        handler = new Handler(Looper.getMainLooper());
        ServerNetwork.getInstance();
        ServerNetwork.getApi().exit(App.model.token).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ServerRun.getInstance().getExit(context, response);
                        }
                    });
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

    public static void getBasket(final Context context) {
        handler = new Handler(Looper.getMainLooper());
        ServerNetwork.getInstance();
        ServerNetwork.getApi().getBasket(App.model.token).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ServerRun.getInstance().getBasket(context, response);
                        }
                    });
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

    public static void postBasket(final Context context, List<Request> requests){
        handler = new Handler(Looper.getMainLooper());
        ServerNetwork.getInstance();
        ServerNetwork.getApi().postBasket(App.model.token, requests).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ServerResponse.getBasket(context);
                        }
                    });
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

    public static void putBasket(final Context context, List<Request> requests){
        handler = new Handler(Looper.getMainLooper());
        ServerNetwork.getInstance();
        ServerNetwork.getApi().putBasket(App.model.token, requests).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ServerRun.getInstance().putBasket(context, response);
                        }
                    });
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


    public static void deleteBasket(final Context context){
        handler = new Handler(Looper.getMainLooper());
        ServerNetwork.getInstance();
        ServerNetwork.getApi().deleteBasket(App.model.token).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ServerRun.getInstance().deleteBasket(context, response);
                        }
                    });
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

    public static void order(final Context context, String comment){
        handler = new Handler(Looper.getMainLooper());
        ServerNetwork.getInstance();
        ServerNetwork.getApi().order(App.model.token, comment).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ServerRun.getInstance().order(context, response);
                        }
                    });
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

    public static void unconfirmedList(final Context context) {
        handler = new Handler(Looper.getMainLooper());
        ServerNetwork.getInstance();
        ServerNetwork.getApi().unconfirmedList(App.model.token).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ServerRun.getInstance().invoiceList(context, response);
                        }
                    });
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

    public static void reservedList(final Context context) {
        handler = new Handler(Looper.getMainLooper());
        ServerNetwork.getInstance();
        ServerNetwork.getApi().reservedList(App.model.token).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ServerRun.getInstance().invoiceList(context, response);
                        }
                    });
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

    public static void orderedList(final Context context) {
        handler = new Handler(Looper.getMainLooper());
        ServerNetwork.getInstance();
        ServerNetwork.getApi().orderedList(App.model.token).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ServerRun.getInstance().invoiceList(context, response);
                        }
                    });
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

    public static void canceledList(final Context context) {
        handler = new Handler(Looper.getMainLooper());
        ServerNetwork.getInstance();
        ServerNetwork.getApi().canceledList(App.model.token).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ServerRun.getInstance().invoiceList(context, response);
                        }
                    });
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

    public static void shippedList(final Context context) {
        handler = new Handler(Looper.getMainLooper());
        ServerNetwork.getInstance();
        ServerNetwork.getApi().shippedList(App.model.token).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ServerRun.getInstance().invoiceList(context, response);
                        }
                    });
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

    public static void unconfirmedItem(final Context context, final int number) {
        handler = new Handler(Looper.getMainLooper());
        ServerNetwork.getInstance();
        ServerNetwork.getApi().unconfirmedItem(App.model.token, number).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ServerRun.getInstance().invoiceDetails(context, response, number);
                        }
                    });
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

    public static void reservedItem(final Context context, final int number) {
        handler = new Handler(Looper.getMainLooper());
        ServerNetwork.getInstance();
        ServerNetwork.getApi().reservedItem(App.model.token, number).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ServerRun.getInstance().invoiceDetails(context, response, number);
                        }
                    });
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


    public static void orderedItem(final Context context, final int number) {
        handler = new Handler(Looper.getMainLooper());
        ServerNetwork.getInstance();
        ServerNetwork.getApi().orderedItem(App.model.token, number).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ServerRun.getInstance().invoiceDetails(context, response, number);
                        }
                    });
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


    public static void canceledItem(final Context context, final int number) {
        handler = new Handler(Looper.getMainLooper());
        ServerNetwork.getInstance();
        ServerNetwork.getApi().canceledItem(App.model.token, number).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ServerRun.getInstance().invoiceDetails(context, response, number);
                        }
                    });
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


    public static void shippedItem(final Context context, final int number) {
        handler = new Handler(Looper.getMainLooper());
        ServerNetwork.getInstance();
        ServerNetwork.getApi().shippedItem(App.model.token, number).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ServerRun.getInstance().invoiceDetails(context, response, number);
                        }
                    });
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
}
