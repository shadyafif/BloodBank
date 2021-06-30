package com.example.bloodbank.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.bloodbank.data.model.BloodTypesDetails.BloodType;
import com.example.bloodbank.data.model.BloodTypesDetails.Datum;
import com.example.bloodbank.data.model.CitiesDetails.Cities;
import com.example.bloodbank.data.model.CitiesDetails.DatumCities;
import com.example.bloodbank.data.model.GovernoratesDetails.DatumGovernorates;
import com.example.bloodbank.data.model.GovernoratesDetails.Governorates;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedTheSpinnerRepo extends GlobalRepo {
    private MutableLiveData<List<Datum>> BloodTypeList = new MutableLiveData<>();
    private MutableLiveData<List<DatumGovernorates>> GovernoratesList = new MutableLiveData<>();
    private MutableLiveData<List<DatumCities>> CitiesList = new MutableLiveData<>();
    List<Datum> BloodLst;
    List<DatumGovernorates> GovernoratesLst;
    List<DatumCities> CitiesLst;

    public FeedTheSpinnerRepo() {
    }

    public MutableLiveData<List<Datum>> getBloodTypeList() {
        return BloodTypeList;
    }

    public MutableLiveData<List<DatumGovernorates>> getGovernoratesList() {
        return GovernoratesList;
    }

    public MutableLiveData<List<DatumCities>> getCitiesList() {
        return CitiesList;
    }

    public void getBloodType() {
        getIRetrofitApi().getBloodType().enqueue(new Callback<BloodType>() {
            @Override
            public void onResponse(@NonNull Call<BloodType> call, @NonNull Response<BloodType> response) {
                assert response.body() != null;
                BloodLst = response.body().getData();
                BloodTypeList.setValue(BloodLst);
            }

            @Override
            public void onFailure(@NonNull Call<BloodType> call, @NonNull Throwable t) {

            }
        });
    }

    public void getGovernorates() {
        getIRetrofitApi().getGovernorates().enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(@NonNull Call<Governorates> call, @NonNull Response<Governorates> response) {
                GovernoratesLst = response.body().getData();
                GovernoratesList.setValue(GovernoratesLst);
            }

            @Override
            public void onFailure(@NonNull Call<Governorates> call, @NonNull Throwable t) {

            }
        });
    }

    public void getCitiesById(int governorate_id) {
        getIRetrofitApi().getCitiesById(governorate_id).enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(@NonNull Call<Cities> call, @NonNull Response<Cities> response) {
                CitiesLst = response.body().getData();
                CitiesList.setValue(CitiesLst);

            }

            @Override
            public void onFailure(@NonNull Call<Cities> call, @NonNull Throwable t) {

            }
        });
    }

    public void getCities() {
        getIRetrofitApi().getCities().enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(@NonNull Call<Cities> call, @NonNull Response<Cities> response) {
                CitiesLst = response.body().getData();
                CitiesList.setValue(CitiesLst);

            }

            @Override
            public void onFailure(@NonNull Call<Cities> call, @NonNull Throwable t) {

            }
        });
    }


}
