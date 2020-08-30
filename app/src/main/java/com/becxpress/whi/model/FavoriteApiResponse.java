package com.becxpress.whi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoriteApiResponse {

    @SerializedName("favorites")
    private List<com.becxpress.whi.model.Product> favorites;

    public List<com.becxpress.whi.model.Product> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<com.becxpress.whi.model.Product> favorites) {
        this.favorites = favorites;
    }
}
