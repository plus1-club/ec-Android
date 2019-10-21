package club.plus1.ec_online.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerModel {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("data")
    @Expose
    private String data;

    @SerializedName("error")
    @Expose
    private String error;

    @SerializedName("message")
    @Expose
    private String message;

    private boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    private String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @NonNull
    @Override
    public String toString() {
        if (this.isSuccess()) {
            return "Успешно.\n"
                    + "Данные: " + this.getData() + "\n";
        } else {
            return "Ошибка!\n"
                    + "Ошибка: " + this.getError() + "\n"
                    + "Сообщение: " + this.getMessage() + "\n";
        }
    }
}
