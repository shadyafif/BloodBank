package com.example.bloodbank.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bloodbank.data.model.DonationDetails.Datum;
import com.example.bloodbank.data.model.DonationDetails.DonationsDetails;
import com.example.bloodbank.data.repository.DonationRequestsRepo;

import java.util.List;

public class DonationViewModel extends AndroidViewModel {
    private DonationRequestsRepo donationRequestsRepo;

    public DonationViewModel(@NonNull Application application) {
        super(application);
        donationRequestsRepo = new DonationRequestsRepo();
    }

    public MutableLiveData<List<Datum>> getDatumList() {
        return donationRequestsRepo.getDonationList();
    }

    public void getAllDonations(String token, int page) {
        donationRequestsRepo.GetRequestsDetailsLst(token, page);
    }
}
