package ru.electric.ec.online.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ru.electric.ec.online.R;

/**
 * Класс полученного ответа от сервера
 * @author Сергей Лавров
 * @version 0.5
 */
public class ServerData {

    /** Запрос на сервер прошёл успешно */
    @SerializedName("success")
    @Expose
    public boolean success;

    /** Данные, возвращённые сервером */
    @SerializedName("data")
    @Expose
    public Object data;

    /** Код ошибки */
    @SerializedName("error")
    @Expose
    public String error;

    /** Текст сообщения ошибки */
    @SerializedName("message")
    @Expose
    public String message;

    /**
     * Вывод ответа сервера для польозователя или отладки
     * @return ответ с сервера в виде строки (для вывода на экран)
     */
    @NonNull
    @Override
    public String toString() {
        if (this.success) {
            return Service.getStr(R.string.text_server_data_success, this.data);
        } else {
            return Service.getStr(R.string.text_server_data_error, this.error, this.message);
        }
    }

}
