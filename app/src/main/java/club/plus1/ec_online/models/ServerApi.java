package club.plus1.ec_online.models;

import java.io.File;
import java.util.List;

import club.plus1.ec_online.domains.Request;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServerApi {

    // User

    @GET("user/enter")
    Call<ServerData> enter(@Query("login") String login,
                           @Query("password") String password);

    @GET("user/exit")
    Call<ServerData> exit(@Header("user_token") String userToken);


    // Request

    @GET("request/byCode")
    Call<ServerData> byCode(@Header("user_token") String userToken,
                            @Query("product") String product,
                            @Query("count") int count,
                            @Query("fullsearch") boolean fullsearch);

    @POST("request/fromExcel")
    Call<ServerData> byCode(@Header("user_token") String userToken,
                            @Query("excel") File excel,
                            @Query("productColumn") int productColumn,
                            @Query("countColumn") int countColumn,
                            @Query("fullsearch") boolean fullsearch);

    @GET("request/basket")
    Call<ServerData> getBasket(@Header("user_token") String userToken);

    @POST("request/basket")
    Call<ServerData> postBasket(@Header("user_token") String userToken,
                                @Body List<Request> requests);

    @PUT("request/basket")
    Call<ServerData> putBasket(@Header("user_token") String userToken,
                               @Query("requests") List<Request> requests);

    @DELETE("request/basket")
    Call<ServerData> deletBasket(@Header("user_token") String userToken,
                                 @Query("requests") List<Request> requests);

    @POST("request/order")
    Call<ServerData> order(@Header("user_token") String userToken,
                           @Query("comment") String comment);


    // Request/download

    @GET("request/download/stockBalance")
    Call<ServerData> stockBalance(@Header("user_token") String userToken);

    @GET("request/download/specifications")
    Call<ServerData> specifications(@Header("user_token") String userToken);

    @GET("request/download/example")
    Call<ServerData> example(@Header("user_token") String userToken);


    // Invoices

    @GET("invoices/unconfirmed")
    Call<ServerData> unconfirmed(@Header("user_token") String userToken);

    @GET("invoices/reserved")
    Call<ServerData> reserved(@Header("user_token") String userToken);

    @GET("invoices/ordered")
    Call<ServerData> ordered(@Header("user_token") String userToken);

    @GET("invoices/canceled")
    Call<ServerData> canceled(@Header("user_token") String userToken);

    @GET("invoices/shipped")
    Call<ServerData> shipped(@Header("user_token") String userToken);


    // Invoices/{number}

    @GET("invoices/{number}/unconfirmed")
    Call<ServerData> unconfirmed(@Header("user_token") String userToken, @Path("number") int number);

    @GET("invoices/{number}/reserved")
    Call<ServerData> reserved(@Header("user_token") String userToken, @Path("number") int number);

    @GET("invoices/{number}/ordered")
    Call<ServerData> ordered(@Header("user_token") String userToken, @Path("number") int number);

    @GET("invoices/{number}/canceled")
    Call<ServerData> canceled(@Header("user_token") String userToken, @Path("number") int number);

    @GET("invoices/{number}/shipped")
    Call<ServerData> shipped(@Header("user_token") String userToken, @Path("number") int number);

    @GET("invoices/{number}/print")
    Call<ServerData> print(@Header("user_token") String userToken, @Path("number") int number);

}
