package ru.electric.ec.online.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerData {

    @SerializedName("success")
    @Expose
    public boolean success;

    @SerializedName("data")
    @Expose
    public Object data;

    @SerializedName("error")
    @Expose
    public String error;

    @SerializedName("message")
    @Expose
    public String message;

    @NonNull
    @Override
    public String toString() {
        if (this.success) {
            return "Успешно.\nДанные: " + this.data;
        } else {
            return "Ошибка!\n" + this.error + "\nСообщение: " + this.message;
        }
    }

}
