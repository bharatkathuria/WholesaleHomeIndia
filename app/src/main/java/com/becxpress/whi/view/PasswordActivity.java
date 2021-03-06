package com.becxpress.whi.view;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.becxpress.whi.R;
import com.becxpress.whi.ViewModel.PasswordViewModel;
import com.becxpress.whi.databinding.ActivityPasswordBinding;
import com.becxpress.whi.storage.LoginUtils;
import com.becxpress.whi.utils.Validation;

import java.io.IOException;

import static com.becxpress.whi.view.AuthenticationActivity.isActivityRunning;

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "PasswordActivity";
    private ActivityPasswordBinding binding;
    private PasswordViewModel passwordViewModel;
    private String previousActivity;
    private Intent intent;
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_password);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.change_password));
        intent = getIntent();
        passwordViewModel = ViewModelProviders.of(this).get(PasswordViewModel.class);
        previousActivity = intent.getStringExtra("activity");

        binding.saveChanges.setOnClickListener(this);
        binding.cancel.setOnClickListener(this);

        if(isActivityRunning){
            binding.currentPassword.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveChanges:
                updatePassword();
                break;
            case R.id.cancel:
                finish();
                break;
        }
    }

    private void updatePassword() {

        String currentPassword = binding.currentPassword.getText().toString();
        String newPassword = binding.newPassword.getText().toString();
        String retypePassword =binding.retypePassword.getText().toString();

        if(previousActivity.equals("PasswordAssistant")){
            userId = intent.getIntExtra("userId",0);
        }
        else{
            userId = LoginUtils.getInstance(this).getUserInfo().getId();
            String oldPassword = LoginUtils.getInstance(this).getUserInfo().getPassword();
            if(!currentPassword.equals(oldPassword)){
                binding.currentPassword.setError(getString(R.string.enter_current_password));
                binding.currentPassword.requestFocus();
                return;
            }
        }



        if (!Validation.isValidPassword(newPassword)) {
            binding.newPassword.setError(getString(R.string.password__at_least_8_characters));
            binding.newPassword.requestFocus();
            return;
        }

        if (!Validation.isValidPassword(newPassword) || !(retypePassword.equals(newPassword))) {
            binding.retypePassword.setError(getString(R.string.password_not_match));
            binding.retypePassword.requestFocus();
            return;
        }

        passwordViewModel.updatePassword(newPassword, userId).observe((LifecycleOwner) this, responseBody -> {
            try {
                LoginUtils.getInstance(this).updatePassword(newPassword);
                Toast.makeText(PasswordActivity.this, responseBody.string(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
