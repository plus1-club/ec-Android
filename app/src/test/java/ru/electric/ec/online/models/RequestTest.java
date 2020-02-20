package ru.electric.ec.online.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("models.Request")
class RequestTest {

    private Request object;

    @BeforeEach
    void setUp() {
        object = new Request("old", 3, 2, 1,
                                "шт", false);
    }

    @AfterEach
    void tearDown() {
        object = null;
    }

    @Test
    @DisplayName("new Request(): Создание пустой строки поиска")
    void newRequest_empty() {
        assertEquals("old", object.product);
        assertEquals(3, object.requestCount);
        assertEquals(2, object.stockCount);
        assertEquals(0.0, object.price, 0.1);
        assertEquals(0.0, object.sum, 0.1);
        assertEquals(1, object.multiplicity);
        assertEquals("шт", object.unit);
        assertFalse(object.check);

        object = new Request();

        assertNull(object.product);
        assertEquals(0, object.requestCount);
        assertEquals(0, object.stockCount);
        assertEquals(0.0, object.price, 0.1);
        assertEquals(0.0, object.sum, 0.1);
        assertEquals(0, object.multiplicity);
        assertNull(object.unit);
        assertFalse(object.check);
    }

    @Test
    @DisplayName("new Request(): Создание заполненной строки поиска")
    void newRequest_ok() {
        assertEquals("old", object.product);
        assertEquals(3, object.requestCount);
        assertEquals(2, object.stockCount);
        assertEquals(0.0, object.price, 0.1);
        assertEquals(0.0, object.sum, 0.1);
        assertEquals(1, object.multiplicity);
        assertEquals("шт", object.unit);
        assertFalse(object.check);

        object = new Request("new", 4, 3, 2,
                        "м", 3.0, true);

        assertEquals("new", object.product);
        assertEquals(4, object.requestCount);
        assertEquals(3, object.stockCount);
        assertEquals(3.0, object.price, 0.1);
        assertEquals(12.0, object.sum, 0.1);
        assertEquals(2, object.multiplicity);
        assertEquals("м", object.unit);
        assertTrue(object.check);
    }
}