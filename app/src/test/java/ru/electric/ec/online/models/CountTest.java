package ru.electric.ec.online.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.electric.ec.online.R;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountTest {

    private Count object;

    @BeforeEach
    void setUp() {
        object = new Count(2,1, "old", R.color.black, "black");
    }

    @AfterEach
    void tearDown() {
        object = null;
    }

    @Test
    @DisplayName("Создание объекта Count")
    void init() {
        assertEquals(2, object.count);
        assertEquals(1, object.stockCount);
        assertEquals("old", object.status);
        assertEquals("black", object.colorName);
        assertEquals(R.color.black, object.color);

        object = new Count(3, 4, "new", R.color.white, "white");

        assertEquals(3, object.count);
        assertEquals(4, object.stockCount);
        assertEquals("new", object.status);
        assertEquals("white", object.colorName);
        assertEquals(R.color.white, object.color);
    }
}