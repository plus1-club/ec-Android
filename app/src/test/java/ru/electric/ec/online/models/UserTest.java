package ru.electric.ec.online.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("models.User")
class UserTest {

    private User object;

    @BeforeEach
    void setUp() {
        object = new User();
    }

    @AfterEach
    void tearDown() {
        object = null;
    }

    @Test
    @DisplayName("new User(): Создание пользователя")
    void newUser() {
        object.login = "1770";
        object.password = "As12345";
        object.token = "ccb303d9-69f8-4902-96e6-331a6b18d7f0";

        assertEquals("1770", object.login);
        assertEquals("As12345", object.password);
        assertEquals("ccb303d9-69f8-4902-96e6-331a6b18d7f0", object.token);
    }

}