package com.example.bloodbank.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.bloodbank.data.model.ContactData.ContactUs;
import com.example.bloodbank.data.model.SettingDetails.Data;
import com.example.bloodbank.data.model.SettingDetails.SettingData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingRepo extends GlobalRepo {
    private final MutableLiveData<Data> settingData = new MutableLiveData<>();
    private MutableLiveData<String> contactResponse = new MutableLiveData<>();
    private Data data;

    public SettingRepo() {
    }

    public MutableLiveData<Data> getSettingData() {
        return settingData;
    }

    public MutableLiveData<String> getContactResponse() {
        return contactResponse;
    }

    public void getSettingUrls(String api_token) {
        getIRetrofitApi().getSetting(api_token).enqueue(new Callback<SettingData>() {
            @Override
            public void onResponse(@NonNull Call<SettingData> call, @NonNull Response<SettingData> response) {
                assert response.body() != null;
                data = response.body().getData();
                settingData.setValue(data);
            }

            @Override
            public void onFailure(@NonNull Call<SettingData> call, @NonNull Throwable t) {

            }
        });
    }

    public void getContactDataResponse(String api_token, String title, String message) {
        getIRetrofitApi().getContact(api_token, title, message).enqueue(new Callback<ContactUs>() {
            @Override
            public void onResponse(@NonNull Call<ContactUs> call, @NonNull Response<ContactUs> response) {
                assert response.body() != null;
                contactResponse.setValue(response.body().getMsg());
            }

            @Override
            public void onFailure(@NonNull Call<ContactUs> call, @NonNull Throwable t) {

            }
        });
    }
}
