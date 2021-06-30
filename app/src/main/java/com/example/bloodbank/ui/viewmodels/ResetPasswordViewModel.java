package com.example.bloodbank.ui.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.bloodbank.data.repository.ResetPasswordRepo;

public class ResetPasswordViewModel extends AndroidViewModel {
    private ResetPasswordRepo resetPasswordRepo;

    public ResetPasswordViewModel(@NonNull Application application) {
        super(application);
        resetPasswordRepo = new ResetPasswordRepo();
    }

    public MutableLiveData<String> getDatumResetPassword() {
        return resetPasswordRepo.getPasswordData();
    }

    public void getUserResetPassword(String phone) {
        resetPasswordRepo.sendCode(phone);
    }
}
