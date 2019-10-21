package club.plus1.ec_online.model.web;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ServerApi {

    @GET("user/enter")
    Call<Server> enter(@Query("login") String login, @Query("password") String password);

    @GET("user/exit")
    Call<Server> exit(@Header("user_token") String userToken);
}
