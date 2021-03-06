package com.becxpress.whi.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;


public class StatusApiResponse {

        @SerializedName("orderproducts")
        private List<Product> products;

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }
    }
