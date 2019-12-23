package ru.electric.ec.online.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvoiceTest {

    private Invoice object;

    @BeforeEach
    void setUp() {
        object = new Invoice(123, "01.01.2000", 110.0, "old");
    }

    @AfterEach
    void tearDown() {
        object = null;
    }

    @Test
    @DisplayName("Создание объекта Invoice")
    void init() {
        assertEquals(123, object.number);
        assertEquals("01.01.2000", object.date);
        assertEquals(110.0, object.sum, 0.1);
        assertEquals("old", object.status);
        assertEquals(0, object.waybill);

        object = new Invoice(321, "02.02.2000", "new", 111);

        assertEquals(321, object.number);
        assertEquals("02.02.2000", object.date);
        assertEquals(0.0, object.sum, 0.1);
        assertEquals("new", object.status);
        assertEquals(111, object.waybill);
    }
}