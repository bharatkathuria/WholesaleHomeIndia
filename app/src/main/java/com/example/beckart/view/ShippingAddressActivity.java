package com.example.beckart.view;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.beckart.R;
import com.example.beckart.ViewModel.ShippingViewModel;
import com.example.beckart.databinding.ActivityShippingAddressBinding;
import com.example.beckart.model.Shipping;
import com.example.beckart.storage.LoginUtils;
import com.example.beckart.utils.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.example.beckart.utils.Constant.PRODUCTID;

public class ShippingAddressActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "ShippingAddressActivity";
    private ActivityShippingAddressBinding binding;

    private ShippingViewModel shippingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shipping_address);

        shippingViewModel = ViewModelProviders.of(this).get(ShippingViewModel.class);

        binding.proceed.setOnClickListener(this);

        binding.txtName.setText(LoginUtils.getInstance(this).getUserInfo().getName());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.proceed) {
            addShippingAddress();
        }
    }

    private void addShippingAddress() {
        String address = binding.address.getText().toString().trim();
        String city = binding.city.getText().toString().trim();
        String country = binding.country.getText().toString().trim();
        String zip = binding.zip.getText().toString().trim();
        String phone = binding.phone.getText().toString().trim();

        if (address.isEmpty()) {
            binding.address.setError(getString(R.string.address_required));
            binding.address.requestFocus();
            return;
        }

        if (city.isEmpty()) {
            binding.city.setError(getString(R.string.city_required));
            binding.city.requestFocus();
            return;
        }
        if (country.isEmpty()) {
            binding.country.setError(getString(R.string.country_required));
            binding.country.requestFocus();
            return;
        }
        if (zip.isEmpty() || Validation.isValidZip(zip)) {
            binding.zip.setError(getString(R.string.enter_a_valid_zip));
            binding.zip.requestFocus();
            return;
        }
        if (phone.isEmpty()  || Validation.isValidPhone(phone)) {
            binding.phone.setError(getString(R.string.enter_a_valid_phoneno));
            binding.phone.requestFocus();
            return;
        }

        String shippingAddress = address +" "+city+" "+country+" "+zip;

        Intent checkoutIntent = new Intent(ShippingAddressActivity.this,CheckoutActivity.class);
        checkoutIntent.putExtras(getIntent());
        checkoutIntent.putExtra("shippingAddress",shippingAddress);
        checkoutIntent.putExtra("phoneNo",phone);
//        Toast.makeText(ShippingAddressActivity.this, orderData, Toast.LENGTH_SHORT).show();
        startActivity(checkoutIntent);



//        Shipping shipping = new Shipping(address, city, country, zip, phone,userId, productId);
//
//        shippingViewModel.addShippingAddress(shipping).observe((LifecycleOwner) this, responseBody -> {
//            try {
//                JSONObject obj = new JSONObject(responseBody.string());
//                if(obj.getBoolean("error")){
//                    Toast.makeText(ShippingAddressActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(ShippingAddressActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
//                    Intent orderProductIntent = new Intent(ShippingAddressActivity.this, OrderProductActivity.class);
//                    orderProductIntent.putExtra(PRODUCTID,productId);
//                    startActivity(orderProductIntent);
//                }
//            } catch (IOException | JSONException e) {
//                e.printStackTrace();
//            }
//        });
    }
}
