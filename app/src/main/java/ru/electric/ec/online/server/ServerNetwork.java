package ru.electric.ec.online.server;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.electric.ec.online.R;
import ru.electric.ec.online.Service;
import ru.electric.ec.online.models.ServerApi;
import ru.electric.ec.online.models.ServerData;
import ru.electric.ec.online.viewmodels.InfoViewModel;

public class ServerNetwork {

    private static ServerNetwork mInstance;
    private static ServerApi serverApi;
    private Retrofit retrofit;

    private static Handler handler;

    // Получение единственного экземпляра класса
    public static ServerNetwork getInstance() {
        if (mInstance == null) {
            mInstance = new ServerNetwork();
        }
        return mInstance;
    }

    private ServerNetwork() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                //Базовая часть адреса
                .baseUrl("https://www.ec-electric.ru/api/v1/")
                //Конвертер, необходимый для преобразования JSON'а в объекты
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getUnsafeOkHttpClient())
                .build();

        //Создаем объект, при помощи которого будем выполнять запросы
        serverApi = retrofit.create(ServerApi.class);

        handler = new Handler(Looper.getMainLooper());
    }

    public static ServerApi getApi() {
        return serverApi;
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkClientTrusted(
                                X509Certificate[] chain, String authType) {
                        }

                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkServerTrusted(
                                X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

            return builder.addInterceptor(interceptor).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static Callback<ServerData> callback(final Context context, ServerRunInterface func, int number){
        return new Callback<ServerData>() {
            @Override
            public void onResponse(@NonNull Call<ServerData> call, @NonNull final Response<ServerData> response) {
                if (response.isSuccessful()) {
                    ServerNetwork.handler.post(() -> func.run(context, response, number));
                } else {
                    InfoViewModel.log(context, false, true,
                            Service.getStr(R.string.text_response_error, response.code(), response.message()));
                }
            }
            @Override
            public void onFailure(@NonNull Call<ServerData> call, @NonNull Throwable t) {
                InfoViewModel.log(context, true, true,
                        Service.getStr(R.string.text_response_failure, t));
            }
        };
    }


}