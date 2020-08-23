package com.example.beckart.view;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.beckart.R;
import com.example.beckart.ViewModel.CheckoutViewModel;
import com.example.beckart.databinding.ActivityCheckoutBinding;
import com.example.beckart.model.Order;
import com.example.beckart.storage.LoginUtils;
import com.example.beckart.utils.Validation;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CheckoutActivity";
    private ActivityCheckoutBinding binding;
    private CheckoutViewModel checkoutViewModel;
    private String shippingAddress;
    private String phoneNo;
    private int quantity;
    private int userId;
    private String userEmail;
    private String userName;
    private String orderData;
    private float orderAmount;
    private float shippingAmount;
    private float totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_checkout);

        checkoutViewModel = ViewModelProviders.of(this).get(CheckoutViewModel.class);

        Intent intent = getIntent();
        shippingAddress = intent.getStringExtra("shippingAddress");
        phoneNo = intent.getStringExtra("phoneNo");
        orderAmount = intent.getFloatExtra("orderAmount", (float) 0.0);
        quantity = intent.getIntExtra("quantity",0);
        orderData = intent.getStringExtra("orderData");
        userId = LoginUtils.getInstance(this).getUserInfo().getId();
        userEmail = LoginUtils.getInstance(this).getUserInfo().getEmail();

        binding.userName.setText(LoginUtils.getInstance(this).getUserInfo().getName());
        binding.userAddress.setText(shippingAddress);
        binding.userPhone.setText(phoneNo);

        shippingAmount = getShippingAmount(quantity);
        totalAmount+=shippingAmount+orderAmount;

        binding.itemCount.setText(String.valueOf(quantity));
        binding.orderAmount.setText(String.valueOf(orderAmount));
        binding.shippingAmount.setText(String.valueOf(shippingAmount));
        binding.totalAmount.setText(String.valueOf(totalAmount));

        binding.payButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.payButton) {
                payForOrder();
        }
    }

    private String generateOrderId(){

        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmss");
        String uid = String.valueOf(userId);
        StringBuilder sb = new StringBuilder();
        while(sb.length()+uid.length()<6){
            sb.append("0");
        }
        sb.append(uid);
        sb.append(ft.format(dNow));
        return sb.toString();
    }
    private void payForOrder(){
        Toast.makeText(CheckoutActivity.this,"Proceeding to Payment gateway..", Toast.LENGTH_SHORT);

                checkoutViewModel.addOrder(new Order(generateOrderId(),orderData,quantity,orderAmount,shippingAmount,totalAmount,shippingAddress,userId,userEmail)).observe((LifecycleOwner) this, responseBody -> {
//            try {
//                String s = responseBody.string();
//                Log.d("onResponse: ",s);
//                JSONObject obj = new JSONObject(s);
//                if(obj.getBoolean("error")){
//
//                }
//                else{
                    goToProductActivity();
//                }
//            } catch (JSONException | IOException e) {
//                e.printStackTrace();
//            }
        });

    }

    private float getShippingAmount(int quantity){
        return (float)100;
    }

    private void goToProductActivity(){
        Intent intent = new Intent(this, ProductActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}
