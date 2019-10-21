package club.plus1.ec_online.model;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServerApi {

    @GET("user/enter")
    Call<ServerModel> enter();

    @GET("user/exit")
    Call<ServerModel> exit();
}
