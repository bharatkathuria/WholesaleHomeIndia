package com.becxpress.whi.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CustomerOrder implements Serializable {

    @SerializedName("order_number")
    private String orderNumber;
    @SerializedName("order_amount")
    private Float orderAmount;
    @SerializedName("order_date")
    private String orderDate;
    @SerializedName("payment_status")
    private String paymentStatus;
    @SerializedName("delivery_status")
    private String deliveryStatus;
    @SerializedName("shipping_amount")
    private Float shippingAmount;
    @SerializedName("shipping_address")
    private String shippingAddress;
    @SerializedName("total_amount")
    private Float totalAmount;
    @SerializedName("total_quantity")
    private int totalQuantity;
    @SerializedName("transaction_id")
    private String transactionId;
    @SerializedName("order_status")
    private String orderStatus;

    public CustomerOrder(String orderNumber, Float orderAmount, String orderDate, String paymentStatus, String deliveryStatus, Float shippingAmount, String shippingAddress, Float totalAmount, int totalQuantity, String transactionId, String orderStatus) {
        this.orderNumber = orderNumber;
        this.orderAmount = orderAmount;
        this.orderDate = orderDate;
        this.paymentStatus = paymentStatus;
        this.deliveryStatus = deliveryStatus;
        this.shippingAmount = shippingAmount;
        this.shippingAddress = shippingAddress;
        this.totalAmount = totalAmount;
        this.totalQuantity = totalQuantity;
        this.transactionId = transactionId;
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public Float getShippingAmount() {
        return shippingAmount;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public Float getOrderAmount() {
        return orderAmount;
    }

    public String getOrderDate() {
        return orderDate;
    }


}
