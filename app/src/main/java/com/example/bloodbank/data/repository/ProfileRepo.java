package com.example.bloodbank.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.bloodbank.data.model.ProfileData.Data;
import com.example.bloodbank.data.model.ProfileData.ProfileDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepo extends GlobalRepo {

    private MutableLiveData<Data> profileData = new MutableLiveData<>();
    private MutableLiveData<String> updateData = new MutableLiveData<>();
    private Data data;
    private String profileUpdate;

    public ProfileRepo() {
    }

    public MutableLiveData<Data> getProfileData() {
        return profileData;
    }

    public MutableLiveData<String> getUpdateData() {
        return updateData;
    }

    public void profileDetails(String api_token) {
        getIRetrofitApi().getProfileData(api_token).enqueue(new Callback<ProfileDetails>() {
            @Override
            public void onResponse(@NonNull Call<ProfileDetails> call, @NonNull Response<ProfileDetails> response) {
                assert response.body() != null;
                data = response.body().getData();
                profileData.setValue(data);
            }

            @Override
            public void onFailure(@NonNull Call<ProfileDetails> call, @NonNull Throwable t) {

            }
        });
    }

    public void profileUpdateData(String api_token, String name, String email, String birthday,
                                  String phone, String City_id, String blood_type_id, String donation_date) {
        getIRetrofitApi().updateProfileData(api_token, name, email, birthday,
                phone, City_id, blood_type_id, donation_date).enqueue(new Callback<ProfileDetails>() {
            @Override
            public void onResponse(@NonNull Call<ProfileDetails> call, @NonNull Response<ProfileDetails> response) {
                assert response.body() != null;
                profileUpdate = response.body().getMsg();
                updateData.setValue(profileUpdate);
            }

            @Override
            public void onFailure(@NonNull Call<ProfileDetails> call, @NonNull Throwable t) {

            }
        });
    }
}
