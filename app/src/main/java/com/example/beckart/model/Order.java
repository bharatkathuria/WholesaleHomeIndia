package com.example.beckart.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order implements Serializable {

    @SerializedName("orderNumber")
    private String orderNumber;
    @SerializedName("orderData")
    private String orderData;
    @SerializedName("totalQuantity")
    private int totalQuantity;
    @SerializedName("orderAmount")
    private Float orderAmount;
    @SerializedName("shippingAmount")
    private Float shippingAmount;
    @SerializedName("totalAmount")
    private Float totalAmount;
    @SerializedName("shippingAddress")
    private String shippingAddress;
    @SerializedName("userId")
    private int userId;
    @SerializedName("userEmail")
    private String userEmail;

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getOrderData() {
        return orderData;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public Float getOrderAmount() {
        return orderAmount;
    }

    public Float getShippingAmount() {
        return shippingAmount;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Order(String orderData, int totalQuantity, Float orderAmount, Float shippingAmount, Float totalAmount, String shippingAddress, int userId, String userEmail) {
        this.orderData = orderData;
        this.totalQuantity = totalQuantity;
        this.orderAmount = orderAmount;
        this.shippingAmount = shippingAmount;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
        this.userId = userId;
        this.userEmail = userEmail;
    }

    public Order(String orderNumber, String orderData, int totalQuantity, Float orderAmount, Float shippingAmount, Float totalAmount, String shippingAddress, int userId, String userEmail) {
        this.orderNumber = orderNumber;
        this.orderData = orderData;
        this.totalQuantity = totalQuantity;
        this.orderAmount = orderAmount;
        this.shippingAmount = shippingAmount;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
        this.userId = userId;
        this.userEmail = userEmail;
    }
}
