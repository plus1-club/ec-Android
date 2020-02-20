package ru.electric.ec.online.data;

import android.content.Context;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("data.LocalDatabase")
class LocalDatabaseTest {

    @Mock
    Context mockContext;
    private Context appContext;

    @BeforeEach
    void setUp() {
        mockContext = mock(Context.class);
        appContext = mock(Context.class);
    }

    @AfterEach
    void tearDown() {
        mockContext = null;
        appContext = null;
    }

    @Test
    @DisplayName("getLocalDatabase(): Контекст приложения = null - база данных не создается")
    void getAppDatabase_null() {
        when(mockContext.getApplicationContext()).thenReturn(null);
        LocalDatabase db = LocalDatabase.getLocalDatabase(mockContext);
        assertNull(db);
    }

    @Test
    @DisplayName("getLocalDatabase(): Есть контекст приложения - база данных создается")
    void getAppDatabase_ok() {
        when(mockContext.getApplicationContext()).thenReturn(appContext);
        LocalDatabase db = LocalDatabase.getLocalDatabase(mockContext);
        assertNotNull(db);
    }

    @Test
    @DisplayName("destroyInstance(): Уничтожение инстанса базы данных")
    void destroyInstance() {
        when(mockContext.getApplicationContext()).thenReturn(appContext);
        LocalDatabase.getLocalDatabase(mockContext);
        assertNotNull(LocalDatabase.mInstance);
        LocalDatabase.destroyInstance();
        assertNull(LocalDatabase.mInstance);
    }

}