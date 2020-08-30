package com.example.beckart.view;
import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beckart.R;
import com.example.beckart.ViewModel.OtpViewModel;
import com.example.beckart.ViewModel.RegisterViewModel;
import com.example.beckart.databinding.ActivityAuthenticationBinding;
import com.example.beckart.model.User;
import com.example.beckart.storage.LoginUtils;

import static com.example.beckart.utils.Constant.EMAIL;
import static com.example.beckart.utils.Constant.OTP;

public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AuthenticationActivity";
    private ActivityAuthenticationBinding binding;
    private OtpViewModel otpViewModel;
    private RegisterViewModel registerViewModel;
    private String email;
    private String correctOtpCode;
    static boolean isActivityRunning = false;
    private int clickCount = 0;
    private String previousActivity;
    private String name;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentication);

        otpViewModel = ViewModelProviders.of(this).get(OtpViewModel.class);
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        binding.proceed.setOnClickListener(this);
        binding.reSend.setOnClickListener(this);

        Intent intent = getIntent();
        email = intent.getStringExtra(EMAIL);
        correctOtpCode = intent.getStringExtra(OTP);
        previousActivity = intent.getStringExtra("activity");
        TextView authentication = findViewById(R.id.authentication);
        authentication.setText(R.string.description2);
        if(previousActivity.equals("SignUpActivity")){
            name = intent.getStringExtra("name");
            password = intent.getStringExtra("password");
            generateOtp(email);
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.proceed) {
            checkOtpCode();
        } else if (view.getId() == R.id.reSend) {
            clickCount = clickCount + 1;
            getAnotherOtpCode();
            if (clickCount >=2) {
                binding.reSend.setClickable(false);
                binding.numberOfClicks.setVisibility(View.VISIBLE);
            }
        }
    }
    private void generateOtp(String email) {

        otpViewModel.getOtpCode(email).observe((LifecycleOwner) this, responseBody -> {
            if ( responseBody ==null  || responseBody.isError()) {
                binding.otpCode.setError(getString(R.string.incorrect_email));
            } else {
                correctOtpCode = responseBody.getOtp();
                Log.d("otp",correctOtpCode);
            }
        });
    }

    private void getAnotherOtpCode() {
        otpViewModel.getOtpCode(email).observe((LifecycleOwner) this, responseBody -> {
            if ( responseBody ==null  || responseBody.isError()) {
                binding.otpCode.setError(getString(R.string.incorrect_email));
            } else {
                correctOtpCode = responseBody.getOtp();
                binding.reSend.setEnabled(false);
                binding.countDownTimer.setVisibility(View.VISIBLE);
                countDownTimer(binding.countDownTimer);
            }

        });
    }

    private void goToProductActivity() {
        Intent intent = new Intent(this, ProductActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void createAccount(){

        ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dialog);
        progressDialog.setMessage(getString(R.string.create_account));
        progressDialog.setCancelable(false);
        progressDialog.show();

        registerViewModel.getRegisterResponseLiveData(new User(name, email, password)).observe((LifecycleOwner) this, registerApiResponse -> {
            if (!registerApiResponse.isError()) {
//                Toast.makeText(this, registerApiResponse.getMessage(), Toast.LENGTH_LONG).show();
                LoginUtils.getInstance(this).saveUserInfo(registerApiResponse.getUser());
                progressDialog.dismiss();
                goToProductActivity();

            }else {
                progressDialog.cancel();
                Toast.makeText(this, registerApiResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void checkOtpCode() {
        String otpEntered = binding.otpCode.getText().toString();

        Log.d("otp",correctOtpCode);
        if (!otpEntered.equals(correctOtpCode)) {
            binding.otpCode.setError(getString(R.string.incorrect_code));
            binding.otpCode.requestFocus();
        } else if(previousActivity.equals("SignUpActivity")){
            createAccount();
        }
        else if(previousActivity.equals("PasswordAssistant")){
            Intent passwordIntent = new Intent(this, com.example.beckart.view.PasswordActivity.class);
            passwordIntent.putExtras(getIntent());
            startActivity(passwordIntent);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        isActivityRunning = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        isActivityRunning = false;
    }

    private void countDownTimer(TextView textView) {
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                textView.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                Log.d(TAG, "onFinish: " + "done!");
                binding.reSend.setEnabled(true);
                binding.countDownTimer.setVisibility(View.INVISIBLE);
            }

        }.start();
    }
}