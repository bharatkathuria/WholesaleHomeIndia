package com.example.beckart.model;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import java.util.List;


public class StatusApiResponse {

        @SerializedName("orderproducts")
        private List<Product> products;

        public List<Product> getProducts() {
            Log.d("sfdgh", "onResponse: aggggggggggggggggggggggggggggggggggggggggggggggaaaaaaaaaaa");
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }
    }
