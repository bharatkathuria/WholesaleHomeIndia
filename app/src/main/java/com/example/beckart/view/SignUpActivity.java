package com.example.beckart.view;

import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Toast;
import com.example.beckart.R;
import com.example.beckart.ViewModel.OtpViewModel;
import com.example.beckart.ViewModel.RegisterViewModel;
import com.example.beckart.databinding.ActivitySignupBinding;
import com.example.beckart.model.User;
import com.example.beckart.storage.LoginUtils;
import com.example.beckart.utils.Validation;

import java.io.Serializable;

import static com.example.beckart.storage.LanguageUtils.loadLocale;
import static com.example.beckart.utils.Constant.EMAIL;
import static com.example.beckart.utils.Constant.OTP;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignUpActivity";
    private ActivitySignupBinding binding;
    private OtpViewModel otpViewModel;
    private String name;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        otpViewModel = ViewModelProviders.of(this).get(OtpViewModel.class);

        binding.buttonSignUp.setOnClickListener(this);
        binding.textViewLogin.setOnClickListener(this);

        setBoldStyle();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (LoginUtils.getInstance(this).isLoggedIn()) {
            goToProductActivity();
        }
    }

    private void signUpUser() {
        name = binding.userName.getText().toString();
        email = binding.userEmail.getText().toString();
        password = binding.userPassword.getText().toString();

        if (name.isEmpty()) {
            binding.userName.setError(getString(R.string.name_required));
            binding.userName.requestFocus();
            return;
        }

        if (!Validation.isValidName(name)) {
            binding.userName.setError(getString(R.string.enter_at_least_3_characters));
            binding.userName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            binding.userEmail.setError(getString(R.string.email_required));
            binding.userEmail.requestFocus();
        }

        if (Validation.isValidEmail(email)) {
            binding.userEmail.setError(getString(R.string.enter_a_valid_email_address));
            binding.userEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            binding.userPassword.setError(getString(R.string.password_required));
            binding.userPassword.requestFocus();
            return;
        }

        if (!Validation.isValidPassword(password)) {
            binding.userPassword.setError(getString(R.string.password__at_least_8_characters));
            binding.userPassword.requestFocus();
            return;
        }


        otpViewModel.isAccountExist(email).observe((LifecycleOwner) this, responseBody -> {
                if (!responseBody.isError()) {
                    goToAuthenticationActivity(email);
                } else {
                    binding.userEmail.setError(responseBody.getMessage());
                    binding.userEmail.requestFocus();
                    return;
                }
            });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSignUp:
                signUpUser();
                break;
            case R.id.textViewLogin:
                goToLoginActivity();
                break;
        }
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void goToProductActivity() {
        Intent intent = new Intent(this, ProductActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void goToAuthenticationActivity(String email) {
        Intent intent = new Intent(this, AuthenticationActivity.class);
        intent.putExtra(EMAIL, email);
        intent.putExtra(OTP, "1233333");
        intent.putExtra("activity","SignUpActivity");
        intent.putExtra("name",name);
        intent.putExtra("password",password);
        startActivity(intent);
    }

    private void setBoldStyle() {
        String boldText = getString(R.string.boldText);
        String normalText = getString(R.string.normalText);
        SpannableString str = new SpannableString(boldText + normalText);
        str.setSpan(new StyleSpan(Typeface.BOLD), 0, boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.textViewLogin.setText(str);
    }
}
