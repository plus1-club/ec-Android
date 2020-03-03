package ru.electric.ec.online.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

import ru.electric.ec.online.R;
import ru.electric.ec.online.common.Service;
import ru.electric.ec.online.data.DateConverter;

/**
 * Класс информационных сообщений и сообщений об ошибках
 * @author Сергей Лавров
 * @version 0.7
 */
@Entity
public class Info {

    /** Дата сообщения */
    @PrimaryKey
    public long date;

    /** Это сообщение об ошибке */
    @ColumnInfo(name = "isError")
    public boolean isError;

    /** Показывать сообщение пользователю */
    @ColumnInfo(name = "show")
    public boolean show;

    /** Заголовок окна с сообщением */
    @ColumnInfo(name = "title")
    public String title;

    /** Текст сообщения */
    @ColumnInfo(name = "message")
    public String message;

    /** Имя активити, из которой было вызвано сообщение */
    @ColumnInfo(name = "activityName")
    public String activityName;

    /**
     * Создание нового пустого сообщения
     */
    public Info() {
    }

    /**
     * Создание нового сообщения по переданным параметрам
     * @param isError это сообщение об ошибке
     * @param show показывать сообщение пользователю
     * @param message текст сообщения
     * @param activityName имя активности, с которой вызвано сообщение
     */
    @Ignore
    public Info(boolean isError, boolean show, String message, String activityName) {
        init(isError, show, message, activityName);
    }

    /**
     * Создание сообщения
     * @param isError это сообщение об ошибке
     * @param show показывать сообщение пользователю
     * @param message текст сообщения
     * @param activityName имя активности, с которой вызвано сообщение
     */
    @Ignore
    public void init(boolean isError, boolean show, String message, String activityName) {
        String title = "";
        if (show && activityName != null) {
            if (isError) {
                title = Service.getStr(R.string.text_error);
            } else {
                title = Service.getStr(R.string.text_info);
            }
        }
        this.date = DateConverter.fromDate(new Date());
        this.isError = isError;
        this.show = show;
        this.title = title;
        this.message = message;
        this.activityName = activityName;
    }
}
