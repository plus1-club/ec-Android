package ru.electric.ec.online.server;

import android.content.Context;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@DisplayName("server.ServerNetwork")
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
    @DisplayName("getApi(): Получение API сервера")
    void getApi() {
        assertNotNull(ServerNetwork.getApi());
    }

    @Test
    @DisplayName("init(): Исключение NoSuchAlgorithmException при создании подключения к API")
    void init_NoSuchAlgorithmException() {
        Throwable thrown = assertThrows(RuntimeException.class, () -> {
            object = spy(ServerNetwork.getInstance());
            when(object.getUnsafeOkHttpClient()).thenThrow(new NoSuchAlgorithmException());
            object.init();
        });
        assertTrue(Objects.requireNonNull(thrown.getMessage()).contains(".NoSuchAlgorithmException"));
    }

    @Test
    @DisplayName("init(): Исключение KeyManagementException при создании подключения к API")
    void init_KeyManagementException() {
        Throwable thrown = assertThrows(RuntimeException.class, () -> {
            object = spy(ServerNetwork.getInstance());
            when(object.getUnsafeOkHttpClient()).thenThrow(new KeyManagementException());
            object.init();
        });
        assertTrue(Objects.requireNonNull(thrown.getMessage()).contains(".KeyManagementException"));
    }
}
