package com.becxpress.whi.model;

import com.google.gson.annotations.SerializedName;

public class Otp {

    @SerializedName("userId")
    private int userId;
    @SerializedName("otp")
    private String otp;
    @SerializedName("email")
    private String email;
    @SerializedName("error")
    private boolean error;
    @SerializedName("message")
    private String message;

    public Otp(boolean error, String message) {
        this.error = error;
        this.message = message;
    }
    public Otp(String otp, String email, boolean error, String message) {
        this.otp = otp;
        this.email = email;
        this.error = error;
        this.message = message;
    }
    public Otp(String otp, String email, boolean error, String message,int userId) {
        this.otp = otp;
        this.email = email;
        this.error = error;
        this.message = message;
        this.userId = userId;
    }

    public Otp(String message) {
        this.message = message;
        this.error = true;
    }

    public String getOtp() {
        return otp;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }

    public int getUserId() {
        return userId;
    }

    public boolean isError() {
        return error;
    }
}