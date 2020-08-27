package com.example.beckart.view;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.beckart.R;
import com.example.beckart.databinding.ActivityAccountBinding;
import com.example.beckart.storage.CartUtils;
import com.example.beckart.storage.LoginUtils;

import static com.example.beckart.utils.CommunicateUtils.rateAppOnGooglePlay;
import static com.example.beckart.utils.CommunicateUtils.shareApp;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener, LifecycleOwner {

    private static final String TAG = "AccountActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAccountBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_account);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.my_account));


        binding.nameOfUser.setText(LoginUtils.getInstance(this).getUserInfo().getName());
        binding.emailOfUser.setText(LoginUtils.getInstance(this).getUserInfo().getEmail());

        binding.myOrders.setOnClickListener(this);
        binding.myWishList.setOnClickListener(this);
        binding.helpCenter.setOnClickListener(this);
        binding.shareWithFriends.setOnClickListener(this);
        binding.rateUs.setOnClickListener(this);
        binding.changePassword.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_signOut) {
            signOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        LoginUtils.getInstance(this).clearAll();
        CartUtils.getInstance(this).clearAll();
        Intent intent = new Intent(this, com.example.beckart.view.LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myOrders:
                Intent ordersIntent = new Intent(this, com.example.beckart.view.OrdersActivity.class);
                ordersIntent.putExtra("track",false);
                startActivity(ordersIntent);
                break;
            case R.id.myWishList:
                Intent wishListIntent = new Intent(this, com.example.beckart.view.WishListActivity.class);
                startActivity(wishListIntent);
                break;
            case R.id.helpCenter:
                Intent helpCenterIntent = new Intent(this, com.example.beckart.view.HelpActivity.class);
                startActivity(helpCenterIntent);
                break;
            case R.id.shareWithFriends:
                shareApp(this);
                break;
            case R.id.rateUs:
                rateAppOnGooglePlay(this);
                break;
            case R.id.changePassword:
                Intent passwordIntent = new Intent(this, com.example.beckart.view.PasswordActivity.class);
                passwordIntent.putExtra("activity","accountActivity");
                startActivity(passwordIntent);
                break;
        }
    }
}