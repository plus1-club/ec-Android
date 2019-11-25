package club.plus1.ec_online;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.google.gson.internal.LinkedTreeMap;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import club.plus1.ec_online.domains.Invoice;
import club.plus1.ec_online.domains.Request;
import club.plus1.ec_online.models.ServerData;
import club.plus1.ec_online.models.ServerNetwork;
import club.plus1.ec_online.viewadapters.InvoiceTableAdapter;
import club.plus1.ec_online.viewmodels.RequestViewModel;
import club.plus1.ec_online.views.EnterActivity;
import club.plus1.ec_online.views.InvoiceTableActivity;
import club.plus1.ec_online.views.MenuActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Server {

    private static Handler handler;
    static String message;

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
                            Intent intent = new Intent(context, EnterActivity.class);
                            context.startActivity(intent);
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
                            RequestViewModel.getInstance().notifyAdapter();
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
                            Server.getBasket(context);
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
                            Server.getBasket(context);
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
                            Server.getBasket(context);
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
                            Intent intent = new Intent(context, InvoiceTableActivity.class);
                            intent.putExtra("title", context.getString(R.string.text_list_unconfirmed));
                            context.startActivity(intent);
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
                            App.model.invoices.clear();
                            if (response.body() != null && response.body().getError().isEmpty()) {
                                DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                                dfs.setDecimalSeparator(',');
                                DecimalFormat df = new DecimalFormat();
                                df.setDecimalFormatSymbols(dfs);
                                List<?> data = (List<?>) response.body().getData();
                                for (Object element : data) {
                                    @SuppressWarnings("unchecked")
                                    Map<String, String> el = (LinkedTreeMap<String, String>) element;
                                    Invoice invoice = new Invoice(
                                        Integer.parseInt(Objects.requireNonNull(el.get("number"))),
                                        el.get("date"),
                                        el.get("status"),
                                        Double.parseDouble(Objects.requireNonNull(el.get("sum"))
                                            .replaceAll(" ","").replace(',','.')));
                                    App.model.invoices.add(invoice);
                                }
                                InvoiceTableAdapter adapter = new InvoiceTableAdapter();
                                adapter.setItems(App.model.invoices);
                                ((InvoiceTableActivity)context).binding.list.setAdapter(adapter);
                            }
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
                            App.model.invoices.clear();
                            if (response.body() != null && response.body().getError().isEmpty()) {
                                DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                                dfs.setDecimalSeparator(',');
                                DecimalFormat df = new DecimalFormat();
                                df.setDecimalFormatSymbols(dfs);
                                List<?> data = (List<?>) response.body().getData();
                                for (Object element : data) {
                                    @SuppressWarnings("unchecked")
                                    Map<String, String> el = (LinkedTreeMap<String, String>) element;
                                    Invoice invoice = new Invoice(
                                            Integer.parseInt(Objects.requireNonNull(el.get("number"))),
                                            el.get("date"),
                                            el.get("status"),
                                            Double.parseDouble(Objects.requireNonNull(el.get("sum"))
                                                    .replaceAll(" ","").replace(',','.')));
                                    App.model.invoices.add(invoice);
                                }
                                InvoiceTableAdapter adapter = new InvoiceTableAdapter();
                                adapter.setItems(App.model.invoices);
                                ((InvoiceTableActivity)context).binding.list.setAdapter(adapter);
                            }
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
                            App.model.invoices.clear();
                            if (response.body() != null && response.body().getError().isEmpty()) {
                                DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                                dfs.setDecimalSeparator(',');
                                DecimalFormat df = new DecimalFormat();
                                df.setDecimalFormatSymbols(dfs);
                                List<?> data = (List<?>) response.body().getData();
                                for (Object element : data) {
                                    @SuppressWarnings("unchecked")
                                    Map<String, String> el = (LinkedTreeMap<String, String>) element;
                                    Invoice invoice = new Invoice(
                                            Integer.parseInt(Objects.requireNonNull(el.get("number"))),
                                            el.get("date"),
                                            el.get("status"),
                                            Double.parseDouble(Objects.requireNonNull(el.get("sum"))
                                                    .replaceAll(" ","").replace(',','.')));
                                    App.model.invoices.add(invoice);
                                }
                                InvoiceTableAdapter adapter = new InvoiceTableAdapter();
                                adapter.setItems(App.model.invoices);
                                ((InvoiceTableActivity)context).binding.list.setAdapter(adapter);
                            }
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
                            App.model.invoices.clear();
                            if (response.body() != null && response.body().getError().isEmpty()) {
                                DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                                dfs.setDecimalSeparator(',');
                                DecimalFormat df = new DecimalFormat();
                                df.setDecimalFormatSymbols(dfs);
                                List<?> data = (List<?>) response.body().getData();
                                for (Object element : data) {
                                    @SuppressWarnings("unchecked")
                                    Map<String, String> el = (LinkedTreeMap<String, String>) element;
                                    Invoice invoice = new Invoice(
                                            Integer.parseInt(Objects.requireNonNull(el.get("number"))),
                                            el.get("date"),
                                            el.get("status"),
                                            Double.parseDouble(Objects.requireNonNull(el.get("sum"))
                                                    .replaceAll(" ","").replace(',','.')));
                                    App.model.invoices.add(invoice);
                                }
                                InvoiceTableAdapter adapter = new InvoiceTableAdapter();
                                adapter.setItems(App.model.invoices);
                                ((InvoiceTableActivity)context).binding.list.setAdapter(adapter);
                            }
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
                            App.model.invoices.clear();
                            if (response.body() != null && response.body().getError().isEmpty()) {
                                DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                                dfs.setDecimalSeparator(',');
                                DecimalFormat df = new DecimalFormat();
                                df.setDecimalFormatSymbols(dfs);
                                List<?> data = (List<?>) response.body().getData();
                                for (Object element : data) {
                                    @SuppressWarnings("unchecked")
                                    Map<String, String> el = (LinkedTreeMap<String, String>) element;
                                    Invoice invoice = new Invoice(
                                            Integer.parseInt(Objects.requireNonNull(el.get("number"))),
                                            el.get("date"),
                                            el.get("status"),
                                            Integer.parseInt(Objects.requireNonNull(el.get("waybill"))));
                                    App.model.invoices.add(invoice);
                                }
                                InvoiceTableAdapter adapter = new InvoiceTableAdapter();
                                adapter.setItems(App.model.invoices);
                                ((InvoiceTableActivity)context).binding.list.setAdapter(adapter);
                            }
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
