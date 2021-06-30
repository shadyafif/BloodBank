package com.example.bloodbank.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.example.bloodbank.R;
import com.example.bloodbank.databinding.ActivityChangePasswordBinding;
import com.example.bloodbank.ui.viewmodels.ResetPasswordViewModel;

import java.util.Objects;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityChangePasswordBinding binding;
    private ResetPasswordViewModel resetPasswordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        initViews();
        resetPasswordViewModel = new ViewModelProvider(this).get(ResetPasswordViewModel.class);
        resetPasswordViewModel.getDatumResetPassword().observe(this, s -> {
            binding.tvCode.setText(s);
        });
        setContentView(binding.getRoot());
    }

    private void initViews() {
        binding.toolbar.setTitle(this.getResources().getString(R.string.change_password));
        binding.btnSendCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.btn_sendCode):
                String phone = Objects.requireNonNull(binding.etResetPassWordPhone.getText()).toString().trim();
                sendCode(phone);
                break;
            case (R.id.btn_resetPassword):
                break;
        }
    }

    private void sendCode(String phone) {
        resetPasswordViewModel.getUserResetPassword(phone);
    }
}