package com.example.bloodbank.data.repository;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import com.example.bloodbank.data.model.resetPassword.ResetPasswordData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordRepo extends GlobalRepo{
    MutableLiveData<String> passwordData = new MutableLiveData<>();

    public MutableLiveData<String> getPasswordData() {
        return passwordData;
    }

    public void sendCode(String phone) {
    getIRetrofitApi().PasswordReset(phone).enqueue(new Callback<ResetPasswordData>() {
        @Override
        public void onResponse(@Nullable Call<ResetPasswordData> call,@Nullable Response<ResetPasswordData> response) {
            assert response != null;
            ResetPasswordData resetPasswordData = response.body();
            String Code = String.valueOf(resetPasswordData.getData().getPinCodeForTest());
            passwordData.setValue(Code);
        }

        @Override
        public void onFailure(@Nullable Call<ResetPasswordData> call,@Nullable Throwable t) {

        }
    });
    }


}
