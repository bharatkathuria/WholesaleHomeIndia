package com.example.beckart.storage;

import android.content.Context;
import android.content.SharedPreferences;


public class CartUtils{

    private static final String SHARED_PREF_NAME = "cart_shared_preference";

    private static CartUtils  mInstance;
    private Context mCtx;

    private CartUtils (Context mCtx) {
        this.mCtx = mCtx;
    }


    public static synchronized CartUtils getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new CartUtils(mCtx);
        }
        return mInstance;
    }

    public void saveCartInfo(String productId,int quantity) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(productId, quantity);
        editor.apply();
    }

    public int getQuantity(String productId) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(productId, 1);
    }

    public void clearAll() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
        editor.apply();
    }

    public void clearProduct(String productId) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(productId);
        editor.apply();
    }
}
