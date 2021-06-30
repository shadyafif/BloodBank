package com.example.bloodbank.data.repository;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;


import com.example.bloodbank.data.model.SignUpData.CreateUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpRepo extends GlobalRepo {
    private Context context;
    private MutableLiveData<String> signUpResponse = new MutableLiveData<>();

    public SignUpRepo(Context context) {
        this.context = context;
    }

    public MutableLiveData<String> getSignUpResponse() {
        return signUpResponse;
    }

    public void signUpAdd(String name, String email, String birth_date, String City_id, String phone,
                          String donation_date, String password, String confirm_password, String blood_type_id) {

        getIRetrofitApi().createUser(name, email, birth_date, City_id, phone, donation_date, password,
                confirm_password, blood_type_id).enqueue(new Callback<CreateUser>() {
            @Override
            public void onResponse(@NonNull Call<CreateUser> call, @NonNull Response<CreateUser> response) {
                assert response.body() != null;
                if (response.body().getStatus() != 0) {
                    signUpResponse.setValue(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CreateUser> call, @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
