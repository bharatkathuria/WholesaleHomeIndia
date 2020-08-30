package com.becxpress.whi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartApiResponse {

    @SerializedName("carts")
    private List<com.becxpress.whi.model.Product> carts;

    public List<com.becxpress.whi.model.Product> getProductsInCart() {
        return carts;
    }

    public void setProductsInCart(List<com.becxpress.whi.model.Product> carts) {
        this.carts = carts;
    }
}
