package ru.electric.ec.online.server;

import android.content.Context;

import retrofit2.Response;

public interface ServerRunInterface {

    void run(Context context, final Response<ServerData> response, final int number);

}
