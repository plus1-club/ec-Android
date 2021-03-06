package ru.electric.ec.online.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("models.Detail")
class DetailTest {

    private Detail object;

    @BeforeEach
    void setUp() {
        object = new Detail("test", 2, "шт", 2.0, 4.0, "old", "old2");
    }

    @AfterEach
    void tearDown() {
        object = null;
    }

    @Test
    @DisplayName("new Detail(): Создание пустой строки деталей счета")
    void newDetail_empty() {
        assertEquals("test", object.product);
        assertEquals(2, object.count);
        assertEquals(2.0, object.price, 0.1);
        assertEquals(4.0, object.sum, 0.1);
        assertEquals("old", object.available);
        assertEquals("old2", object.delivery);

        object = new Detail();

        assertNull(object.product);
        assertEquals(0, object.count);
        assertEquals(0.0, object.price, 0.1);
        assertEquals(0.0, object.sum, 0.1);
        assertNull(object.available);
        assertNull(object.delivery);
    }

    @Test
    @DisplayName("new Detail(): Создание строки деталей счета c доставкой")
    void newDetail_delivery() {
        assertEquals("test", object.product);
        assertEquals(2, object.count);
        assertEquals(2.0, object.price, 0.1);
        assertEquals(4.0, object.sum, 0.1);
        assertEquals("old", object.available);
        assertEquals("old2", object.delivery);

        object = new Detail("test2", 3, "шт",5.0, 15.0, "new", "new2");

        assertEquals("test2", object.product);
        assertEquals(3, object.count);
        assertEquals(5.0, object.price, 0.1);
        assertEquals(15.0, object.sum, 0.1);
        assertEquals("new", object.available);
        assertEquals("new2", object.delivery);
    }

    @Test
    @DisplayName("new Detail(): Создание строки деталей счета без доставки")
    void newDetail_delivery_null() {
        assertEquals("test", object.product);
        assertEquals(2, object.count);
        assertEquals(2.0, object.price, 0.1);
        assertEquals(4.0, object.sum, 0.1);
        assertEquals("old", object.available);
        assertEquals("old2", object.delivery);

        object = new Detail("test2", 3, "шт",5.0, 15.0, "new", null);

        assertEquals("test2", object.product);
        assertEquals(3, object.count);
        assertEquals(5.0, object.price, 0.1);
        assertEquals(15.0, object.sum, 0.1);
        assertEquals("new", object.available);
        assertEquals("", object.delivery);
    }
}