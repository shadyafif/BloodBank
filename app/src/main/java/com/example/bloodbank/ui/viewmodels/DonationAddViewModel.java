package com.example.bloodbank.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bloodbank.data.model.BloodTypesDetails.Datum;
import com.example.bloodbank.data.model.CitiesDetails.DatumCities;
import com.example.bloodbank.data.model.GovernoratesDetails.DatumGovernorates;
import com.example.bloodbank.data.repository.DonationAddRepo;

import java.util.List;

public class DonationAddViewModel extends AndroidViewModel {
    private DonationAddRepo donationAddRepo;

    public DonationAddViewModel(@NonNull Application application) {
        super(application);
        donationAddRepo = new DonationAddRepo(application);
    }

    public MutableLiveData<String> getDonationAdd() {
        return donationAddRepo.getDonationResponse();
    }

    public void getDonationAddResponse(String api_token, String patient_name, String patient_age, String blood_type, String bags_num,
                                       String hospital_name, String hospital_address, String city_id, String phone, String notes, String latitude, String longitude) {
        donationAddRepo.donationAdd(api_token, patient_name, patient_age, blood_type, bags_num,
                hospital_name, hospital_address, city_id, phone, notes, latitude, longitude);
    }

}
