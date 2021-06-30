package com.example.bloodbank.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.bloodbank.data.model.DonationDetails.Datum;
import com.example.bloodbank.data.model.DonationDetails.DonationsDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonationRequestsRepo extends GlobalRepo {

    MutableLiveData<List<Datum>> donationList = new MutableLiveData<>();
    List<Datum> datumList;

    public DonationRequestsRepo() {

    }

    public MutableLiveData<List<Datum>> getDonationList() {
        return donationList;
    }

    public void GetRequestsDetailsLst(String token, int page) {
        getIRetrofitApi().GetDonationRequests(token, page).enqueue(new Callback<DonationsDetails>() {
            @Override
            public void onResponse(@NonNull Call<DonationsDetails> call, @NonNull Response<DonationsDetails> response) {
                assert response.body() != null;
                datumList = response.body().getData().getData();
                donationList.postValue(datumList);
            }

            @Override
            public void onFailure(@NonNull Call<DonationsDetails> call, @NonNull Throwable t) {

            }
        });
    }
}
