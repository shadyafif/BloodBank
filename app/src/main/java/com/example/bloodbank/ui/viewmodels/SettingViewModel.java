package com.example.bloodbank.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bloodbank.data.model.SettingDetails.Data;
import com.example.bloodbank.data.repository.SettingRepo;

public class SettingViewModel extends AndroidViewModel {
    private SettingRepo settingRepo;

    public SettingViewModel(@NonNull Application application) {
        super(application);
        settingRepo = new SettingRepo();
    }

    public MutableLiveData<Data> getData() {
        return settingRepo.getSettingData();
    }

    public void getUrls(String api_token) {
        settingRepo.getSettingUrls(api_token);
    }

    public MutableLiveData<String> getDataContact() {
        return settingRepo.getContactResponse();
    }

    public void getContactUs(String api_token, String title, String message) {
        settingRepo.getContactDataResponse(api_token, title, message);
    }
}
