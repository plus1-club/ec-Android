package ru.electric.ec.online.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;

import ru.electric.ec.online.models.User;

import static org.mockito.Mockito.mock;

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

}