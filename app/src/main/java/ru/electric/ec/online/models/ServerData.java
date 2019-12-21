package ru.electric.ec.online.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ru.electric.ec.online.R;

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
            return Service.getStr(R.string.text_server_data_success, this.data);
        } else {
            return Service.getStr(R.string.text_server_data_error, this.error, this.message);
        }
    }

}
