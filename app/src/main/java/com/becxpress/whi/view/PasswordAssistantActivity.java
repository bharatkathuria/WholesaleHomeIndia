package com.becxpress.whi.view;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.becxpress.whi.R;
import com.becxpress.whi.ViewModel.OtpViewModel;
import com.becxpress.whi.databinding.ActivityPasswordAssistantBinding;

import static com.becxpress.whi.utils.Constant.EMAIL;
import static com.becxpress.whi.utils.Constant.OTP;

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
            if ( responseBody ==null  || responseBody.isError()) {
                binding.emailAddress.setError(responseBody.getMessage());
            } else {
                userId = responseBody.getUserId();
                otpCode = responseBody.getOtp();
                goToAuthenticationActivity();
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
