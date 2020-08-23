package com.example.beckart.model;

import com.example.beckart.model.Order;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerOrderApiResponse {

    @SerializedName("orders")
    private List<CustomerOrder> orderList;

    public List<CustomerOrder> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<CustomerOrder> orderList) {
        this.orderList = orderList;
    }
}
