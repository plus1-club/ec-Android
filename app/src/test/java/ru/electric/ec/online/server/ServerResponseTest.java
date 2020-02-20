package ru.electric.ec.online.server;

import android.content.Context;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import ru.electric.ec.online.data.LocalDatabase;

import static org.mockito.Mockito.mock;

@DisplayName("server.ServerResponse")
class ServerResponseTest {

    private MockWebServer mockWebServer;

    @Mock
    Context mockContext;

    @Mock
    LocalDatabase db;

    @BeforeEach
    void setUp() {
        mockWebServer = new MockWebServer();
        MockResponse mockResponse = new MockResponse().setBody("Test");
        mockWebServer.enqueue(mockResponse);
        mockContext = mock(Context.class);
        db = mock(LocalDatabase.class);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
        mockContext = null;
        db = null;
    }

    @Test
    @DisplayName("user - getEnter(): GET-запрос входа на сервер")
    void getEnter() {
        ServerResponse.getEnter(mockContext, "Login", "Password", false);
    }

    @Test
    @DisplayName("user - getExit(): GET-запрос выхода с сервера")
    void getExit() {
        ServerResponse.getExit(mockContext);
    }

    @Test
    @DisplayName("request - byCode(): GET-запрос поиска товаров по коду")
    void byCode() {
        ServerResponse.byCode(mockContext, "Product", 1, false);
    }

    @Test
    @DisplayName("request - getBasket(): GET-запрос получения корзины с сервера")
    void getBasket() {
        ServerResponse.getBasket(mockContext);
    }

    @Test
    @DisplayName("request - postBasket(): POST-запрос отправки корзины на сервер")
    void postBasket() {
        ServerResponse.postBasket(mockContext, null);
    }

    @Test
    @DisplayName("request - putBasket(): PUT-запрос обновления корзины на сервер")
    void putBasket() {
        ServerResponse.putBasket(mockContext, null);
    }

    @Test
    @DisplayName("request - deleteBasket(): DELETE-запрос очистки/удаления из корзины на сервере")
    void deleteBasket() {
        ServerResponse.deleteBasket(mockContext);
    }

    @Test
    @DisplayName("request - order(): POST-запрос создания заказа из корзины")
    void order() {
        ServerResponse.order(mockContext, "Comment");
    }

    @Test
    @DisplayName("invoice - unconfirmedList(): GET-запрос получения списка неподтвержденных резервов")
   void unconfirmedList() {
        ServerResponse.unconfirmedList(mockContext);
    }

    @Test
    @DisplayName("invoice - reservedList(): GET-запрос получения списка резервов")
    void reservedList() {
        ServerResponse.reservedList(mockContext);
    }

    @Test
    @DisplayName("invoice - orderedList(): GET-запрос получения списка заказов")
    void orderedList() {
        ServerResponse.orderedList(mockContext);
    }

    @Test
    @DisplayName("invoice - canceledList(): GET-запрос получения списка отмененных и просроченных счетов")
    void canceledList() {
        ServerResponse.canceledList(mockContext);
    }

    @Test
    @DisplayName("invoice - shippedList(): GET-запрос получения списка отгрузок")
    void shippedList() {
        ServerResponse.shippedList(mockContext);
    }

    @Test
    @DisplayName("detail - unconfirmedItem(): GET-запрос получения деталей неподтвержденного резерва")
    void unconfirmedItem() {
        ServerResponse.unconfirmedItem(mockContext, 0);
    }

    @Test
    @DisplayName("detail - reservedItem(): GET-запрос получения деталей резерва")
    void reservedItem() {
        ServerResponse.reservedItem(mockContext, 0);
    }

    @Test
    @DisplayName("detail - orderedItem(): GET-запрос получения деталей заказа")
    void orderedItem() {
        ServerResponse.orderedItem(mockContext, 0);
    }

    @Test
    @DisplayName("detail - canceledItem(): GET-запрос получения деталей отмененного или просроченного счета")
    void canceledItem() {
        ServerResponse.canceledItem(mockContext, 0);
    }

    @Test
    @DisplayName("detail - shippedItem(): GET-запрос получения деталей отгрузки")
    void shippedItem() {
        ServerResponse.shippedItem(mockContext, 0);
    }

    @Test
    @DisplayName("detail - print(): GET-запрос получения файла pdf-счета для печати")
    void print() {
        ServerResponse.print(mockContext, 0);
    }
}