package com.example.bloodbank.data.repository;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.bloodbank.data.model.Login.login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepo extends GlobalRepo{
    MutableLiveData<login> loginData = new MutableLiveData<>();
    Context context;

    public LoginRepo(Context context) {

    }

    public MutableLiveData<login> getLogin() {
        return loginData;
    }

    public void userLogin(String email, String password) {
        getIRetrofitApi().userLogin(email, password).enqueue(new Callback<login>() {
            @Override
            public void onResponse(@NonNull Call<login> call, @NonNull Response<login> response) {
                login registerModel = response.body();
                loginData.setValue(registerModel);
            }

            @Override
            public void onFailure(@NonNull Call<login> call, @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
