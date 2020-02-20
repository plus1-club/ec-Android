package ru.electric.ec.online.server;

import android.content.Context;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class ServerNetworkTest {

    private ServerNetwork object;

    @Mock
    Context mockContext;

    @BeforeEach
    void setUp() {
        object = ServerNetwork.getInstance();
        mockContext = mock(Context.class);
    }

    @AfterEach
    void tearDown() {
        object = null;
        mockContext = null;
    }

    @Test
    void getApi() {
        assertNotNull(ServerNetwork.getApi());
    }

    @Test
    void init_NoSuchAlgorithmException() {
        Throwable thrown = assertThrows(RuntimeException.class, () -> {
            object = spy(ServerNetwork.getInstance());
            when(object.getUnsafeOkHttpClient()).thenThrow(new NoSuchAlgorithmException());
            object.init();
        });
        assertTrue(Objects.requireNonNull(thrown.getMessage()).contains(".NoSuchAlgorithmException"));
    }

    @Test
    void init_KeyManagementException() {
        Throwable thrown = assertThrows(RuntimeException.class, () -> {
            object = spy(ServerNetwork.getInstance());
            when(object.getUnsafeOkHttpClient()).thenThrow(new KeyManagementException());
            object.init();
        });
        assertTrue(Objects.requireNonNull(thrown.getMessage()).contains(".KeyManagementException"));
    }

    @Test
    void callback_response_success() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        MockResponse mockResponse = new MockResponse().setBody("Test");
        mockWebServer.enqueue(mockResponse);

        Response<ServerData> response = Response.success(new ServerData());

        Callback<ServerData> callback = ServerNetwork.callback(mockContext, null, 0);
        @SuppressWarnings("unchecked")
        Call<ServerData> call = (Call<ServerData>) mock(Call.class);
        callback.onResponse(call, response);

        mockWebServer.shutdown();
    }

    @Test
    void callback_response_not_success() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        MockResponse mockResponse = new MockResponse().setBody("Test");
        mockWebServer.enqueue(mockResponse);

        MediaType contentType = MediaType.parse("application/json; charset=utf-8");
        ResponseBody body = ResponseBody.create("Test", contentType);
        Response<ServerData> response = Response.error(500, body);

        Callback<ServerData> callback = ServerNetwork.callback(mockContext, null, 0);
        @SuppressWarnings("unchecked")
        Call<ServerData> call = (Call<ServerData>) mock(Call.class);
        callback.onResponse(call, response);

        mockWebServer.shutdown();
    }

    @Test
    void callback_error() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        MockResponse mockResponse = new MockResponse().setBody("Test");
        mockWebServer.enqueue(mockResponse);

        Callback<ServerData> callback = ServerNetwork.callback(mockContext, null, 0);
        @SuppressWarnings("unchecked")
        Call<ServerData> call = (Call<ServerData>) mock(Call.class);
        callback.onFailure(call, new Throwable("Test"));

        mockWebServer.shutdown();
    }
}
