package ru.electric.ec.online.common;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("common.App")
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
    @DisplayName("onCreate(): Создание приложения")
    void onCreate() {
        App.setAppContext(null);
        App.setModel(null);
        assertNull(App.getAppContext());
        object.onCreate();
        assertEquals(object, App.getAppContext());
        assertNotNull(App.getModel());
    }

    @Test
    @DisplayName("onTerminate(): Уничтожение приложения")
    void onTerminate() {
        App.setAppContext(object);
        App.setModel(model);
        assertEquals(object, App.getAppContext());
        assertEquals(model, App.getModel());
        object.onTerminate();
        assertNull(App.getAppContext());
    }
}