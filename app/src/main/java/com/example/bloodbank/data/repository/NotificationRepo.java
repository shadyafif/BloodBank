package com.example.bloodbank.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.bloodbank.data.model.NotificationCountData.NotificationCount;
import com.example.bloodbank.data.model.NotificationData.Datum;
import com.example.bloodbank.data.model.NotificationData.NotificationDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationRepo extends GlobalRepo {

    private MutableLiveData<List<Datum>> notificationList = new MutableLiveData<>();
    private List<Datum> notificationLst;

    public MutableLiveData<Integer> notificationCount = new MutableLiveData<Integer>();

    public NotificationRepo() {
    }


    public MutableLiveData<List<Datum>> getNotificationList() {
        return notificationList;
    }

    public MutableLiveData<Integer> getNotificationCount() {
        return notificationCount;
    }

    public void getNotificationData(String token, int page) {
        getIRetrofitApi().getNotification(token, page).enqueue(new Callback<NotificationDetails>() {
            @Override
            public void onResponse(@NonNull Call<NotificationDetails> call, @NonNull Response<NotificationDetails> response) {
                assert response.body() != null;
                notificationLst = response.body().getData().getData();
                notificationList.setValue(notificationLst);
            }

            @Override
            public void onFailure(@NonNull Call<NotificationDetails> call, @NonNull Throwable t) {

            }
        });
    }

    public void notificationCount(String token) {
        getIRetrofitApi().getNotificationCount(token).enqueue(new Callback<NotificationCount>() {
            @Override
            public void onResponse(@NonNull Call<NotificationCount> call, @NonNull Response<NotificationCount> response) {
                assert response.body() != null;
                notificationCount.setValue(response.body().getData().getNotificationsCount());
            }

            @Override
            public void onFailure(@NonNull Call<NotificationCount> call, @NonNull Throwable t) {

            }
        });
    }
}
