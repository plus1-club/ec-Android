package ru.electric.ec.online.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import ru.electric.ec.online.common.App;
import ru.electric.ec.online.models.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("data.DataService")
class DataServiceTest {

    private User testUser;

    @Mock
    LocalDatabase db;
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        db = mock(LocalDatabase.class);
        userDao = mock(UserDao.class);
        testUser = new User();
        testUser.login = "1";
        testUser.password = "2";
    }

    @AfterEach
    void tearDown() {
        db = null;
        userDao = null;
    }

    @Test
    @DisplayName("createUser(): База данных = null - ничего не происходит")
    void createUser_db_null() {
        App.setDb(null);
        DataService.createUser("1", "2", false);
        assertNull(App.getDb());
    }

    @Test
    @DisplayName("createUser(): В пустой базе данных, всё заполнено - пользователь создается")
    void createUser_db_empty() {
        when(db.userDao()).thenReturn(userDao);
        when(userDao.readUser("1")).thenReturn(testUser);
        App.setDb(db);
        DataService.createUser("1", "2", false);
        User newUser = App.getDb().userDao().readUser("1");
        assertNotNull(newUser);
        assertEquals("1", newUser.login);
        assertEquals("2", newUser.password);
    }

    @Test
    @DisplayName("createUser(): Всё заполнено - пользователь создается")
    void createUser_db_ok() {
        when(db.userDao()).thenReturn(userDao);
        when(userDao.readUser("1")).thenReturn(testUser);
        when(userDao.readUser("1", "2")).thenReturn(testUser);
        App.setDb(db);
        DataService.createUser("1", "2", false);
        User newUser = App.getDb().userDao().readUser("1");
        assertNotNull(newUser);
        assertEquals("1", newUser.login);
        assertEquals("2", newUser.password);
    }

    @Test
    @DisplayName("createUser(): Пользователь не найден в базе данных - пользователь создается")
    void createUser_user_null() {
        when(db.userDao()).thenReturn(userDao);
        when(userDao.readUser("3")).thenReturn(null);
        when(userDao.readUser("1")).thenReturn(testUser);
        when(userDao.readUser("1", "2")).thenReturn(testUser);
        App.setDb(db);
        DataService.createUser("3", "4", false);
        User newUser = App.getDb().userDao().readUser("1");
        assertNotNull(newUser);
        assertEquals("1", newUser.login);
        assertEquals("2", newUser.password);
    }

    @Test
    @DisplayName("createUser(): Логин = null - пользователь не создается")
    void createUser_login_null() {
        when(db.userDao()).thenReturn(userDao);
        App.setDb(db);
        DataService.createUser(null, "2", false);
        User newUser = App.getDb().userDao().readUser(null);
        assertNull(newUser);
    }

    @Test
    @DisplayName("createUser(): Логин пустой - пользователь не создается")
    void createUser_login_empty() {
        when(db.userDao()).thenReturn(userDao);
        App.setDb(db);
        DataService.createUser("", "2", false);
        User newUser = App.getDb().userDao().readUser("");
        assertNull(newUser);
    }

}