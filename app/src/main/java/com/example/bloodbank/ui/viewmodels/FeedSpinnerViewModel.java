package com.example.bloodbank.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bloodbank.data.model.BloodTypesDetails.Datum;
import com.example.bloodbank.data.model.CitiesDetails.DatumCities;
import com.example.bloodbank.data.model.GovernoratesDetails.DatumGovernorates;
import com.example.bloodbank.data.repository.FeedTheSpinnerRepo;

import java.util.List;

public class FeedSpinnerViewModel extends AndroidViewModel {
    private FeedTheSpinnerRepo feedTheSpinnerRepo;

    public FeedSpinnerViewModel(@NonNull Application application) {
        super(application);
        feedTheSpinnerRepo = new FeedTheSpinnerRepo();
    }
    public MutableLiveData<List<Datum>> getDatumList() {
        return feedTheSpinnerRepo.getBloodTypeList();
    }

    public void getBloodTypeList() {
        feedTheSpinnerRepo.getBloodType();
    }

    public MutableLiveData<List<DatumGovernorates>> getDatumGovernoratesList() {
        return feedTheSpinnerRepo.getGovernoratesList();
    }

    public void getGovernoratesList() {
        feedTheSpinnerRepo.getGovernorates();
    }

    public MutableLiveData<List<DatumCities>> getDatumCitiesListById() {
        return feedTheSpinnerRepo.getCitiesList();
    }

    public void getCitiesListById(int governorate_id) {
        feedTheSpinnerRepo.getCitiesById(governorate_id);
    }

    public void getCitiesList() {
        feedTheSpinnerRepo.getCities();
    }
}
