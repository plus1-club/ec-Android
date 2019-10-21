package club.plus1.ec_online.model.web;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServerApi {

    @GET("user/enter")
    Call<Server> enter();

    @GET("user/exit")
    Call<Server> exit();
}
