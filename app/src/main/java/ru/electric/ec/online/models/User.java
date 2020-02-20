package ru.electric.ec.online.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Класс информации о пользователе
 * @author Сергей Лавров
 * @version 0.6
 */
@Entity
public class User {

    /** Имя для входа в систему */
    @NonNull
    @PrimaryKey
    public String login = "";

    /** Пароль для входа в систему */
    @ColumnInfo(name = "password")
    public String password;

    /** Токен, выданный сервером для работы с данными для этого пользователя */
    @ColumnInfo(name = "token")
    public String token;

    /** Дата получения данных с сервера */
    @ColumnInfo(name = "date")
    public long date;

    /** Нужно ли сохранять этот логин/пароль */
    @ColumnInfo(name = "save")
    public boolean save;
}
