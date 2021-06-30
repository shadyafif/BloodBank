package com.example.bloodbank.data.repository;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.bloodbank.data.model.BloodTypesDetails.Datum;
import com.example.bloodbank.data.model.BloodTypesDetails.BloodType;
import com.example.bloodbank.data.model.CitiesDetails.Cities;
import com.example.bloodbank.data.model.CitiesDetails.DatumCities;
import com.example.bloodbank.data.model.GovernoratesDetails.DatumGovernorates;
import com.example.bloodbank.data.model.GovernoratesDetails.Governorates;
import com.example.bloodbank.data.model.donationRequestCreate.DonationCreate;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonationAddRepo extends GlobalRepo {
    private Context context;
    private MutableLiveData<String> donationResponse = new MutableLiveData<>();

    public DonationAddRepo(Context context) {
        this.context = context;
    }

    public MutableLiveData<String> getDonationResponse() {
        return donationResponse;
    }

    public void donationAdd(String api_token, String patient_name, String patient_age, String blood_type, String bags_num,
                            String hospital_name, String hospital_address, String city_id, String phone, String notes, String latitude, String longitude) {

        getIRetrofitApi().createDonation(api_token, patient_name, patient_age, blood_type, bags_num,
                hospital_name, hospital_address, city_id, phone, notes, latitude, longitude).enqueue(new Callback<DonationCreate>() {
            @Override
            public void onResponse(@NonNull Call<DonationCreate> call, @NonNull Response<DonationCreate> response) {
                assert response.body() != null;
                if (response.body().getStatus() != 0) {
                    donationResponse.setValue(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(@NonNull Call<DonationCreate> call, @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
