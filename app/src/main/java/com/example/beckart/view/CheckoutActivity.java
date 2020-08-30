package com.example.beckart.view;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.Toast;

import com.example.beckart.R;
import com.example.beckart.ViewModel.CheckoutViewModel;
import com.example.beckart.databinding.ActivityCheckoutBinding;
import com.example.beckart.model.GenericServerResponse;
import com.example.beckart.model.Order;
import com.example.beckart.storage.LoginUtils;
import com.example.beckart.utils.Validation;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener, PaymentResultWithDataListener {

    private static final String TAG = CheckoutActivity.class.getSimpleName();

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
    private String orderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Checkout.clearUserData(getApplicationContext());
        Checkout.preload(getApplicationContext());

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

        shippingAmount = getShippingAmount(orderAmount);
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

                checkoutViewModel.addOrder(new Order(orderData,quantity,orderAmount,shippingAmount,totalAmount,shippingAddress,userId,userEmail)).observe((LifecycleOwner) this, responseBody -> {
            try {
                String s = responseBody.string();
                s.trim();
                Gson gson = new Gson();
                GenericServerResponse genericServerResponse = gson.fromJson(s,GenericServerResponse.class);
                Log.d("onResponse:",s);
                 if(genericServerResponse.isError()){

                 }
                 else{
                     Toast.makeText(getApplicationContext(),genericServerResponse.getMessage(),Toast.LENGTH_LONG);
                     orderId = genericServerResponse.getOrder_id();
                     startPayment();
        //             goToProductActivity();
                 }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void startPayment() {

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_live_hoMnK33thW7a9k");
        final Activity activity = this;

        try{
            JSONObject options = new JSONObject();
            String amount = String.valueOf((long)(totalAmount)*100);
            options.put("name", "Wholesale Home India");
            options.put("description", "Order Id:"+ orderId);
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", orderId);
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            //pass amount in paise not rupees
            options.put("amount", amount);//pass amount in currency subunits
            options.put("prefill.email", userEmail);
            options.put("notes.amount", amount);
//            options.put("prefill.contact",);
            checkout.open(activity, options);
        }
        catch (Exception e){
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }




    }

    private float getShippingAmount(float orderAmount){
        if(orderAmount>999){
            return 0;
        }
        return (float)50;
    }

    private void goToProductActivity(){
        Intent intent = new Intent(this, ProductActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    @Override
    public void onPaymentSuccess(String paymentId, PaymentData paymentData) {


        Log.d("payment",paymentId +" "+paymentData.getUserEmail()+" "+paymentData.getPaymentId());
        binding.checkoutBox.setVisibility(View.GONE);
        binding.processing.setVisibility(View.VISIBLE);
        checkoutViewModel.updatePaymentInfo(paymentData.getOrderId(),paymentData.getPaymentId(),LoginUtils.getInstance(this).getUserInfo().getEmail(),totalAmount).observe((LifecycleOwner) this, responseBody -> {
            try {
                String s = responseBody.string();
                s.trim();
                Gson gson = new Gson();
                GenericServerResponse genericServerResponse = gson.fromJson(s,GenericServerResponse.class);
                Log.d("onResponse:",s);
                if(genericServerResponse.isError()){
                    binding.processing.setVisibility(View.GONE);
                    binding.fail.setVisibility(View.VISIBLE);
                    goToProductActivity();
                }
                else{

                    binding.processing.setVisibility(View.GONE);
                    binding.success.setVisibility(View.VISIBLE);
                    goToProductActivity();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void onPaymentError(int i, String description, PaymentData paymentData) {

        binding.processing.setVisibility(View.GONE);
        binding.fail.setVisibility(View.VISIBLE);
        goToProductActivity();
    }
}
