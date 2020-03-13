package ru.electric.ec.online.server;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import ru.electric.ec.online.models.Request;

/**
 * Интерфейс для вызова методов API с сервера
 * @author Сергей Лавров
 * @version 0.5
 */
public interface ServerApi {

    String BASE_URL = "https://www.ec-electric.ru/api/v1/";

    // User - пользователи

    /**
     * Авторизация пользователя в системе
     * @param login логин, выданный пользователю
     * @param password пароль для входа по логину
     * @return ответ сервера ({@link ServerData})
     * */
    @GET("user/enter")
    Observable<ServerData> enter(@Query("login") String login,
                                 @Query("password") String password);

    /**
     * Выход пользователя из системы
     * @param userToken токен, выданный пользователю после входа
     * @return ответ сервера ({@link ServerData})
     * */
    @GET("user/exit")
    Observable<ServerData> exit(@Header("user_token") String userToken);


    // Request - поиск и корзина

    /**
     * Поиск товара по коду
     * @param userToken токен, выданный пользователю после входа
     * @param product искомый код или название товара
     * @param count искомое количество товара
     * @return ответ сервера ({@link ServerData})
     * */
    @GET("request/byCode")
    Observable<ServerData> byCode(@Header("user_token") String userToken,
                            @Query("product") String product,
                            @Query("count") int count,
                            @Query("fullsearch") boolean fullsearch);

    /**
     * Поиск товара из файла Excel
     * @param userToken токен, выданный пользователю после входа
     * @param excel файл Excel с таблицей товаров и количества
     * @param productColumn номер колонки, в котором находится код или название товара
     * @param countColumn номер колонки, в котором находится количество товара
     * @return ответ сервера ({@link ServerData})
     * */
    @Multipart
    @POST("request/fromExcel")
    Observable<ServerData> fromExcel(@Header("user_token") String userToken,
                            @Part MultipartBody.Part excel,
                            @Query("productColumn") int productColumn,
                            @Query("countColumn") int countColumn,
                            @Query("fullsearch") boolean fullsearch);

    /**
     * Получение содержимого корзины
     * @param userToken токен, выданный пользователю после входа
     * @return ответ сервера ({@link ServerData})
     * */
    @GET("request/basket")
    Observable<ServerData> getBasket(@Header("user_token") String userToken);

    /**
     * Добавление товаров в корзину
     * @param userToken токен, выданный пользователю после входа
     * @param requests список новых товаров для корзины
     * @return ответ сервера ({@link ServerData})
     * */
    @POST("request/basket")
    Observable<ServerData> postBasket(@Header("user_token") String userToken,
                                @Body List<Request> requests);

    /**
     * Обновление содержимого корзины (искользуется для изменения количества или удаления позиций)
     * @param userToken токен, выданный пользователю после входа
     * @param requests новое содержимое корзины
     * @return ответ сервера ({@link ServerData})
     * */
    @PUT("request/basket")
    Observable<ServerData> putBasket(@Header("user_token") String userToken,
                               @Body List<Request> requests);

    /**
     * Очистка корзины
     * @param userToken токен, выданный пользователю после входа
     * @return ответ сервера ({@link ServerData})
     * */
    @DELETE("request/basket")
    Observable<ServerData> deleteBasket(@Header("user_token") String userToken);

    /**
     * Создание заказа из корзины
     * @param userToken токен, выданный пользователю после входа
     * @param comment комментарий к заказу
     * @return ответ сервера ({@link ServerData})
     * */
    @POST("request/order")
    Observable<ServerData> order(@Header("user_token") String userToken,
                           @Query("comment") String comment);


    // Request/download - получение файлов с сервера

    /**
     * Скачивание файла с остатком по складу
     * @param userToken токен, выданный пользователю после входа
     * @return ответ сервера ({@link ServerData})
     * */
    @GET("request/download/stock")
    Observable<ServerData> stockBalance(@Header("user_token") String userToken);

    /**
     * Скачивание Excel-файла с примером таблицы для поиска по Excel
     * @param userToken токен, выданный пользователю после входа
     * @return ответ сервера ({@link ServerData})
     * */
    @GET("request/download/example")
    Observable<ServerData> example(@Header("user_token") String userToken);


    // Invoices - счета

    /**
     * Получение списка неподтвержденных резервов
     * @param userToken токен, выданный пользователю после входа
     * @return ответ сервера ({@link ServerData})
     * */
    @GET("invoices/unconfirmed")
    Observable<ServerData> unconfirmedList(@Header("user_token") String userToken);

    /**
     * Получение списка резервов
     * @param userToken токен, выданный пользователю после входа
     * @return ответ сервера ({@link ServerData})
     * */
    @GET("invoices/reserved")
    Observable<ServerData> reservedList(@Header("user_token") String userToken);

    /**
     * Получение списка неподтвержденных заказов
     * @param userToken токен, выданный пользователю после входа
     * @return ответ сервера ({@link ServerData})
     * */
    @GET("invoices/ordered")
    Observable<ServerData> orderedList(@Header("user_token") String userToken);

    /**
     * Получение списка аннулированных и просроченных счетов
     * @param userToken токен, выданный пользователю после входа
     * @return ответ сервера ({@link ServerData})
     * */
    @GET("invoices/canceled")
    Observable<ServerData> canceledList(@Header("user_token") String userToken);

    /**
     * Получение истории отгрузок
     * @param userToken токен, выданный пользователю после входа
     * @return ответ сервера ({@link ServerData})
     * */
    @GET("invoices/shipped")
    Observable<ServerData> shippedList(@Header("user_token") String userToken);


    // Invoices/{number} - детали счета

    /**
     * Получение деталей неподтвержденного резерва
     * @param userToken токен, выданный пользователю после входа
     * @param number номер неподтвержденного резерва
     * @return ответ сервера ({@link ServerData})
     * */
    @GET("invoices/{number}/unconfirmed")
    Observable<ServerData> unconfirmedItem(@Header("user_token") String userToken, @Path("number") int number);

    /**
     * Получение деталей резерва
     * @param userToken токен, выданный пользователю после входа
     * @param number номер резерва
     * @return ответ сервера ({@link ServerData})
     * */
    @GET("invoices/{number}/reserved")
    Observable<ServerData> reservedItem(@Header("user_token") String userToken, @Path("number") int number);

    /**
     * Получение деталей заказа
     * @param userToken токен, выданный пользователю после входа
     * @param number номер заказа
     * @return ответ сервера ({@link ServerData})
     * */
    @GET("invoices/{number}/ordered")
    Observable<ServerData> orderedItem(@Header("user_token") String userToken, @Path("number") int number);

    /**
     * Получение деталей аннулированного или просроченного счета
     * @param userToken токен, выданный пользователю после входа
     * @param number номер аннулированного или просроченного счета
     * @return ответ сервера ({@link ServerData})
     * */
    @GET("invoices/{number}/canceled")
    Observable<ServerData> canceledItem(@Header("user_token") String userToken, @Path("number") int number);

    /**
     * Получение деталей отгрузки
     * @param userToken токен, выданный пользователю после входа
     * @param number номер отгрузки
     * @return ответ сервера ({@link ServerData})
     * */
    @GET("invoices/{number}/shipped")
    Observable<ServerData> shippedItem(@Header("user_token") String userToken, @Path("number") int number);

    /**
     * Получение pdf-файла для печати счета (резерва или заказа)
     * @param userToken токен, выданный пользователю после входа
     * @param number номер счета (резерва или заказа)
     * @return ответ сервера ({@link ServerData})
     * */
    @GET("invoices/{number}/print")
    Observable<ServerData> print(@Header("user_token") String userToken, @Path("number") int number);


    /**
     * Скачивание файла с сервера
     * @param fileUrl адрес для скачивания файла
     * @return ответ сервера ({@link ResponseBody})
     * */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Header("user_token") String userToken, @Url String fileUrl);
}
