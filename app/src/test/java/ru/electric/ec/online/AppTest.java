package ru.electric.ec.online;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {

    private App object;
    private Model model;

    @BeforeEach
    void setUp() {
        object = new App();
        model = new Model();
    }

    @AfterEach
    void tearDown() {
        object = null;
        model = null;
    }

    @Test
    void init() {
        App.setAppContext(null);
        App.setModel(null);
        assertNull(App.getAppContext());
        assertNull(App.getModel());
        object.onCreate();
        assertEquals(object, App.getAppContext());
        assertNotNull(App.getModel());
    }

    @Test
    void terminate() {
        App.setAppContext(object);
        App.setModel(model);
        assertEquals(object, App.getAppContext());
        assertEquals(model, App.getModel());
        object.onTerminate();
        assertNull(App.getAppContext());
        assertNull(App.getModel());
    }
}