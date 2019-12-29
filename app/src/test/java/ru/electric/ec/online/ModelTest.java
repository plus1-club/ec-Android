package ru.electric.ec.online;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ModelTest {

    private Model object;

    @BeforeEach
    void setUp() {
        object = new Model();
    }

    @AfterEach
    void tearDown() {
        object = null;
    }

    @Test
    void init() {
        object.token = "1";
        object.request = null;
        object.invoice = null;
        assertEquals("1", object.token);
        assertNull(object.request);
        assertNull(object.invoice);
        object = new Model();
        assertEquals("", object.token);
        assertNotNull(object.request);
        assertNotNull(object.invoice);
    }
}