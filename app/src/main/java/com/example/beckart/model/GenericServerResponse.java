package com.example.beckart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenericServerResponse {



    @Expose
    @SerializedName("error")
    boolean error;
    @Expose
    @SerializedName("message")
    String message;
    @Expose
    @SerializedName("order_id")
    String order_id;

    public GenericServerResponse(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public GenericServerResponse(boolean error, String message, String order_id) {
        this.error = error;
        this.message = message;
        this.order_id = order_id;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
