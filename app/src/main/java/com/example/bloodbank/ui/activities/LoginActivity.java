package com.example.bloodbank.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.Login.login;
import com.example.bloodbank.databinding.ActivityLoginBinding;
import com.example.bloodbank.ui.viewmodels.LoginViewModel;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;
    private SharedPreferences.Editor edt;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        initViews();
        SharedPreferences pref = this.getSharedPreferences("login", MODE_PRIVATE);
        edt = pref.edit();
        boolean saveLogin = pref.getBoolean("saveLogin", false);
        if (saveLogin) {
            binding.etLoginPhone.setText(pref.getString("username", ""));
            binding.etLoginPassword.setText(pref.getString("password", ""));
            binding.chkRem.setChecked(true);
        }
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getDatumLogin().observe(this, this::loginSuccess);
        setContentView(binding.getRoot());
    }

    public void initViews() {
        binding.btnLogin.setOnClickListener(this);
        binding.tvForgetPassword.setOnClickListener(this);
        binding.tvSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.btn_login):
                userLogin();
                break;
            case (R.id.tv_forgetPassword):
                startActivity(new Intent(this, ChangePasswordActivity.class));
                break;
            case (R.id.tv_SignUp):
                startActivity(new Intent(this, SignUpActivity.class));
                break;
        }
    }

    private void userLogin() {
        String phone = Objects.requireNonNull(binding.etLoginPhone.getText()).toString().trim();
        String password = Objects.requireNonNull(binding.etLoginPassword.getText()).toString().trim();
        String textRequired = getResources().getString(R.string.TextRequired);
        if (binding.chkRem.isChecked()) {
            edt.putBoolean("saveLogin", true);
            edt.putString("username", phone);
            edt.putString("password", password);
        } else {
            edt.clear();
        }
        edt.commit();
        if (phone.isEmpty()) {
            binding.etLoginPhone.setError(textRequired);
            binding.etLoginPhone.requestFocus();
            return;
        }


        if (password.isEmpty()) {
            binding.etLoginPassword.setError(textRequired);
            binding.etLoginPassword.requestFocus();
            return;
        }
        loginViewModel.getUserLogin(phone, password);
    }

    public void loginSuccess(login login) {
        if (login.getStatus() == 0) {
            Toast.makeText(this, login.getMsg(), Toast.LENGTH_SHORT).show();
        } else {
            edt.putString("token", login.getData().getApiToken());
            edt.putBoolean("isLogin", true);
            edt.commit();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));

        }

    }
}