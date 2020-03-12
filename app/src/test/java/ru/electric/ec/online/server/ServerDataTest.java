package ru.electric.ec.online.server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.electric.ec.online.R;
import ru.electric.ec.online.common.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("server.ServerData")
class ServerDataTest {

    private ServerData object;

    @BeforeEach
    void setUp() {
        object = new ServerData();
    }

    @AfterEach
    void tearDown() {
        object = null;
    }

    @Test
    @DisplayName("getStr(): Строка с успешным сообщением")
    void toString_success() {
        object.success = true;
        object.data = "data";
        String expected = Service.getStr(R.string.text_server_data_success, "data");
        assertEquals(expected, object.toString());
    }

    @Test
    @DisplayName("getStr(): Строка с ошибкой")
    void toString_error() {
        object.success = false;
        object.message = "message";
        String expected = Service.getStr(R.string.text_invoice, "message");
        assertEquals(expected, object.toString());
    }
}