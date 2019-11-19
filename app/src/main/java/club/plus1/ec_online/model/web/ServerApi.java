package club.plus1.ec_online.model.web;

import java.io.File;
import java.util.List;

import club.plus1.ec_online.domain.Request;
import retrofit2.Call;
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
    Call<Server> enter(@Query("login") String login,
                       @Query("password") String password);

    @GET("user/exit")
    Call<Server> exit(@Header("user_token") String userToken);


    // Request

    @GET("request/byCode")
    Call<Server> byCode(@Header("user_token") String userToken,
                        @Query("product") String product,
                        @Query("count") int count,
                        @Query("fullsearch") boolean fullsearch);

    @POST("request/fromExcel")
    Call<Server> byCode(@Header("user_token") String userToken,
                        @Query("excel") File excel,
                        @Query("productColumn") int productColumn,
                        @Query("countColumn") int countColumn,
                        @Query("fullsearch") boolean fullsearch);

    @GET("request/basket")
    Call<List<Server>> getBasket(@Header("user_token") String userToken);

    @POST("request/basket")
    Call<Server> postBasket(@Header("user_token") String userToken,
                            @Query("requests") List<Request> requests);

    @PUT("request/basket")
    Call<Server> putBasket(@Header("user_token") String userToken,
                           @Query("requests") List<Request> requests);

    @DELETE("request/basket")
    Call<Server> deletBasket(@Header("user_token") String userToken,
                             @Query("requests") List<Request> requests);

    @POST("request/order")
    Call<Server> order(@Header("user_token") String userToken,
                       @Query("requests") List<Request> requests,
                       @Query("comment") String comment);


    // Request/download

    @GET("request/download/stockBalance")
    Call<Server> stockBalance(@Header("user_token") String userToken);

    @GET("request/download/specifications")
    Call<Server> specifications(@Header("user_token") String userToken);

    @GET("request/download/example")
    Call<Server> example(@Header("user_token") String userToken);


    // Invoices

    @GET("invoices/unconfirmed")
    Call<Server> unconfirmed(@Header("user_token") String userToken);

    @GET("invoices/reserved")
    Call<Server> reserved(@Header("user_token") String userToken);

    @GET("invoices/ordered")
    Call<Server> ordered(@Header("user_token") String userToken);

    @GET("invoices/canceled")
    Call<Server> canceled(@Header("user_token") String userToken);

    @GET("invoices/shipped")
    Call<Server> shipped(@Header("user_token") String userToken);


    // Invoices/{number}

    @GET("invoices/{number}/unconfirmed")
    Call<Server> unconfirmed(@Header("user_token") String userToken, @Path("number") int number);

    @GET("invoices/{number}/reserved")
    Call<Server> reserved(@Header("user_token") String userToken, @Path("number") int number);

    @GET("invoices/{number}/ordered")
    Call<Server> ordered(@Header("user_token") String userToken, @Path("number") int number);

    @GET("invoices/{number}/canceled")
    Call<Server> canceled(@Header("user_token") String userToken, @Path("number") int number);

    @GET("invoices/{number}/shipped")
    Call<Server> shipped(@Header("user_token") String userToken, @Path("number") int number);

    @GET("invoices/{number}/print")
    Call<Server> print(@Header("user_token") String userToken, @Path("number") int number);

}
