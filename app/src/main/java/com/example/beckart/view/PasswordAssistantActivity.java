package com.example.beckart.view;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.example.beckart.R;
import com.example.beckart.ViewModel.OtpViewModel;
import com.example.beckart.databinding.ActivityPasswordAssistantBinding;

import static com.example.beckart.utils.Constant.EMAIL;
import static com.example.beckart.utils.Constant.OTP;

public class PasswordAssistantActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PasswordAssistantActivity";
    private ActivityPasswordAssistantBinding binding;
    private OtpViewModel otpViewModel;
    private String userEmail;
    private String otpCode;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_password_assistant);

        otpViewModel = ViewModelProviders.of(this).get(OtpViewModel.class);

        binding.proceed.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.proceed) {
            checkUserEmail();
        }
    }

    private void checkUserEmail() {
        userEmail = binding.emailAddress.getText().toString();
        if(userEmail.isEmpty()){
            binding.emailAddress.setError(getString(R.string.enter_a_valid_email_address));
            binding.emailAddress.requestFocus();
            return;
        }

        otpViewModel.isEmailExist(userEmail).observe((LifecycleOwner) this, responseBody -> {
            if (!responseBody.isError()) {
                userId = responseBody.getUserId();
                otpCode = responseBody.getOtp();
                goToAuthenticationActivity();
            } else {
                binding.emailAddress.setError(responseBody.getMessage());
            }
        });
    }

    private void goToAuthenticationActivity() {
        Intent intent = new Intent(this, AuthenticationActivity.class);
        intent.putExtra(EMAIL, userEmail);
        intent.putExtra(OTP, otpCode);
        intent.putExtra("userId", userId);
        intent.putExtra("activity","PasswordAssistant");
        startActivity(intent);
    }
}
