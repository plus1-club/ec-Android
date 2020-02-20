package ru.electric.ec.online.server;

import android.content.Context;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import ru.electric.ec.online.App;
import ru.electric.ec.online.data.LocalDatabase;
import ru.electric.ec.online.data.UserDao;
import ru.electric.ec.online.models.User;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    void getEnter() {
        ServerResponse.getEnter(mockContext, "Login", "Password");
    }

    @Test
    void getExit() {
        ServerResponse.getExit(mockContext);
    }

    @Test
    void byCode() {
        ServerResponse.byCode(mockContext, "Product", 1, false);
    }

    @Test
    void getBasket() {
        ServerResponse.getBasket(mockContext);
    }

    @Test
    void postBasket() {
        ServerResponse.postBasket(mockContext, null);
    }

    @Test
    void putBasket() {
        ServerResponse.putBasket(mockContext, null);
    }

    @Test
    void deleteBasket() {
        ServerResponse.deleteBasket(mockContext);
    }

    @Test
    void order() {
        ServerResponse.order(mockContext, "Comment");
    }

    @Test
    void unconfirmedList() {
        ServerResponse.unconfirmedList(mockContext);
    }

    @Test
    void reservedList() {
        ServerResponse.reservedList(mockContext);
    }

    @Test
    void orderedList() {
        ServerResponse.orderedList(mockContext);
    }

    @Test
    void canceledList() {
        ServerResponse.canceledList(mockContext);
    }

    @Test
    void shippedList() {
        ServerResponse.shippedList(mockContext);
    }

    @Test
    void unconfirmedItem() {
        ServerResponse.unconfirmedItem(mockContext, 0);
    }

    @Test
    void reservedItem() {
        ServerResponse.reservedItem(mockContext, 0);
    }

    @Test
    void orderedItem() {
        ServerResponse.orderedItem(mockContext, 0);
    }

    @Test
    void canceledItem() {
        ServerResponse.canceledItem(mockContext, 0);
    }

    @Test
    void shippedItem() {
        ServerResponse.shippedItem(mockContext, 0);
    }

    @Test
    void print() {
        ServerResponse.print(mockContext, 0);
    }

    @Test
    void createUser_null() {
        App.setDb(null);
        ServerResponse.createUser("1", "2");
    }

    @Test
    void createUser_empty() {
        when(db.userDao()).thenReturn(mock(UserDao.class));
        App.setDb(db);
        ServerResponse.createUser("1", "2");
    }

    @Test
    void createUser_mock() {
        when(db.userDao()).thenReturn(mock(UserDao.class));
        when(db.userDao().readUser("1", "2")).thenReturn(mock(User.class));
        App.setDb(db);
        ServerResponse.createUser("1", "2");
    }
}