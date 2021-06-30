package com.example.bloodbank.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bloodbank.data.repository.SignUpRepo;

public class SignUpViewModel extends AndroidViewModel {
    private SignUpRepo signUpRepo;

    public SignUpViewModel(@NonNull Application application) {
        super(application);
        signUpRepo = new SignUpRepo(application);
    }

    public MutableLiveData<String> getSignUpAdd() {
        return signUpRepo.getSignUpResponse();
    }

    public void getCreateUserResponse(String name, String email, String birth_date, String City_id, String phone,
                                      String donation_date, String password, String confirm_password, String blood_type_id) {
        signUpRepo.signUpAdd(name, email, birth_date, City_id, phone, donation_date, password,
                confirm_password, blood_type_id);
    }
}
