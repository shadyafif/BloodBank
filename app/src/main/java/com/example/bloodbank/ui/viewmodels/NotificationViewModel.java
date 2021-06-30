package com.example.bloodbank.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bloodbank.data.model.NotificationData.Datum;
import com.example.bloodbank.data.repository.NotificationRepo;

import java.util.List;

public class NotificationViewModel extends AndroidViewModel {
    private NotificationRepo notificationRepo;

    public NotificationViewModel(@NonNull Application application) {
        super(application);
        notificationRepo = new NotificationRepo();
    }

    public MutableLiveData<List<Datum>> getNotificationDataList() {
        return notificationRepo.getNotificationList();
    }

    public void getNotificationDetails(String token, int page) {
        notificationRepo.getNotificationData(token, page);
    }

    public MutableLiveData<Integer> NotificationNumber() {
        return notificationRepo.notificationCount;
    }

    public void getNotificationNumber(String api_token) {
        notificationRepo.notificationCount(api_token);
    }
}
