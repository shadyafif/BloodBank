package com.example.bloodbank.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bloodbank.data.model.Login.login;
import com.example.bloodbank.data.repository.LoginRepo;

public class LoginViewModel extends AndroidViewModel {
    private LoginRepo loginRepo;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        loginRepo = new LoginRepo(application);
    }

    public MutableLiveData<login> getDatumLogin() {
        return loginRepo.getLogin();
    }

    public void getUserLogin(String email, String password) {
        loginRepo.userLogin(email, password);
    }
}
