package com.example.bloodbank.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bloodbank.data.model.ProfileData.Data;
import com.example.bloodbank.data.repository.ProfileRepo;

public class ProfileViewModel extends AndroidViewModel {
    private ProfileRepo profileRepo;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        profileRepo = new ProfileRepo();
    }

    public MutableLiveData<Data> profileData() {
        return profileRepo.getProfileData();
    }

    public void getProfileDetails(String api_token) {
        profileRepo.profileDetails(api_token);
    }

    public MutableLiveData<String> profileUpdateData() {
        return profileRepo.getUpdateData();
    }

    public void getProfileUpdate(String api_token, String name, String email, String birthday,
                                 String phone, String City_id, String blood_type_id, String donation_date) {
        profileRepo.profileUpdateData(api_token, name, email, birthday,
                phone, City_id, blood_type_id, donation_date);
    }
}
