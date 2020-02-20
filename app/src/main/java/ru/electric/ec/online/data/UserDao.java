package ru.electric.ec.online.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.electric.ec.online.models.User;

@Dao
public interface UserDao {

    @Insert
    void create(User user);

    @Query("SELECT * FROM user")
    List<User> readAll();

    @Query("SELECT * FROM user WHERE login = :login AND password = :password ORDER BY date LIMIT 1")
    User readUser(String login, String password);

    @Query("SELECT token FROM user WHERE login = :login AND password = :password ORDER BY date LIMIT 1")
    String readToken(String login, String password);

    @Query("SELECT * FROM user ORDER BY date LIMIT 1")
    User readLast();

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user")
    void deleteAll();
}