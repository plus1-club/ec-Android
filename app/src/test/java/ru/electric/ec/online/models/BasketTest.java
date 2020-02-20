package ru.electric.ec.online.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("models.Basket")
class BasketTest {

    private Basket object;

    @BeforeEach
    void setUp() {
        object = new Basket();
    }

    @AfterEach
    void tearDown() {
        object = null;
    }

    @Test
    @DisplayName("new Basket(): Проверка заполнения элемента корзины")
    void newBasket() {
        object.product = "new";
        object.requestCount = 4;
        object.stockCount = 3;
        object.price = 3.0;
        object.sum = 12.0;
        object.multiplicity = 2;
        object.unit = "м";
        object.check = true;

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