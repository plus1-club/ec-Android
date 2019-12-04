package ru.electric.ec.online.models;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.electric.ec.online.App;
import ru.electric.ec.online.domains.Request;
import ru.electric.ec.online.viewmodels.ServerRun;

public class ServerResponse {

    public static void getEnter(final Context context, String login, String password) {
        ServerNetwork.getApi().enter(login, password).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    ServerNetwork.handler.post(new Runnable() {
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
        ServerNetwork.getApi().exit(App.model.token).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    ServerNetwork.handler.post(new Runnable() {
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

    public static void byCode(final Context context, String product, int count, boolean fullsearch) {
        ServerNetwork.getApi().byCode(App.model.token, product, count, fullsearch).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    ServerNetwork.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ServerRun.getInstance().getByCode(context, response);
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
        ServerNetwork.getApi().getBasket(App.model.token).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    ServerNetwork.handler.post(new Runnable() {
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
        ServerNetwork.getApi().postBasket(App.model.token, requests).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    ServerNetwork.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ServerRun.getInstance().postBasket(context, response);
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
        ServerNetwork.getApi().putBasket(App.model.token, requests).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    ServerNetwork.handler.post(new Runnable() {
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
        ServerNetwork.getApi().deleteBasket(App.model.token).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    ServerNetwork.handler.post(new Runnable() {
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
        ServerNetwork.getApi().order(App.model.token, comment).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    ServerNetwork.handler.post(new Runnable() {
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
        ServerNetwork.getApi().unconfirmedList(App.model.token).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    ServerNetwork.handler.post(new Runnable() {
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
        ServerNetwork.getApi().reservedList(App.model.token).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    ServerNetwork.handler.post(new Runnable() {
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
        ServerNetwork.getApi().orderedList(App.model.token).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    ServerNetwork.handler.post(new Runnable() {
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
        ServerNetwork.getApi().canceledList(App.model.token).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    ServerNetwork.handler.post(new Runnable() {
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
        ServerNetwork.getApi().shippedList(App.model.token).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    ServerNetwork.handler.post(new Runnable() {
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
        ServerNetwork.getApi().unconfirmedItem(App.model.token, number).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    ServerNetwork.handler.post(new Runnable() {
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
        ServerNetwork.getApi().reservedItem(App.model.token, number).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    ServerNetwork.handler.post(new Runnable() {
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
        ServerNetwork.getApi().orderedItem(App.model.token, number).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    ServerNetwork.handler.post(new Runnable() {
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
        ServerNetwork.getApi().canceledItem(App.model.token, number).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    ServerNetwork.handler.post(new Runnable() {
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
        ServerNetwork.getApi().shippedItem(App.model.token, number).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    ServerNetwork.handler.post(new Runnable() {
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

    public static void print(final Context context, final int number) {
        ServerNetwork.getApi().print(App.model.token, number).enqueue(new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    ServerNetwork.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ServerRun.getInstance().getPrint(context, response, number);
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
