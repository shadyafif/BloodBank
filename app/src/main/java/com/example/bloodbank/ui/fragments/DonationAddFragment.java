package com.example.bloodbank.ui.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.BloodTypesDetails.Datum;
import com.example.bloodbank.data.model.CitiesDetails.DatumCities;
import com.example.bloodbank.data.model.GovernoratesDetails.DatumGovernorates;
import com.example.bloodbank.databinding.FragmentDonationAddBinding;
import com.example.bloodbank.ui.viewmodels.DonationAddViewModel;
import com.example.bloodbank.ui.viewmodels.FeedSpinnerViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static com.example.bloodbank.utlies.Extension.Replace;

public class DonationAddFragment extends Fragment implements View.OnClickListener {

    private FragmentDonationAddBinding binding;
    private DonationAddViewModel donationAddViewModel;
    private FeedSpinnerViewModel feedSpinnerViewModel;
    private FusedLocationProviderClient fusedLocationClient;
    Geocoder geocoder;
    List<Address> addressList;
    String FullAddress;
    double lat;
    double lng;
    public String city_Id, blood_Id;

    public DonationAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDonationAddBinding.inflate(inflater, container, false);
        initViews();
        feedSpinnerViewModel.getBloodTypeList();
        feedSpinnerViewModel.getGovernoratesList();
        feedSpinnerViewModel.getDatumList().observe(requireActivity(), this::fillBloodSpinner);
        feedSpinnerViewModel.getDatumGovernoratesList().observe(requireActivity(), this::fillGovernoratesSpinner);
        feedSpinnerViewModel.getDatumCitiesListById().observe(requireActivity(), this::fillCitiesSpinner);
        donationAddViewModel.getDonationAdd().observe(requireActivity(), this::responseSuccess);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        return binding.getRoot();
    }


    private void initViews() {
        binding.toolbar.setTitle(this.getResources().getString(R.string.add));
        donationAddViewModel = new ViewModelProvider(this).get(DonationAddViewModel.class);
        feedSpinnerViewModel = new ViewModelProvider(this).get(FeedSpinnerViewModel.class);
        binding.btnMap.setOnClickListener(this);
        binding.btnDonationAdd.setOnClickListener(this);
    }

    private void responseSuccess(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        DonationFragment donationFragment = new DonationFragment();
        Replace(donationFragment, R.id.frame_layout, requireActivity().getSupportFragmentManager().beginTransaction());
    }


    public void fillBloodSpinner(List<Datum> datum) {
        List<String> listSpinner = new ArrayList<String>();
        String BloodTypeName = getResources().getString(R.string.ChooseBloodType);
        listSpinner.add(BloodTypeName);
        final List<Integer> listIds = new ArrayList<>();

        for (int i = 0; i < datum.size(); i++) {
            listSpinner.add(datum.get(i).getName());
            listIds.add(datum.get(i).getId());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, listSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        binding.spnDonationAddBloodType.setAdapter(adapter);
        binding.spnDonationAddBloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                if (position != 0) {
                    int id = (listIds.get(position)) - 1;
                    blood_Id = String.valueOf(id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void fillGovernoratesSpinner(List<DatumGovernorates> datumGovernorates) {
        List<String> listGovernoratesSpinner = new ArrayList<>();
        final List<Integer> listGovernoratesIds = new ArrayList<>();
        String Governorate = getResources().getString(R.string.ChooseGovernorate);
        listGovernoratesSpinner.add(Governorate);
        for (int i = 0; i < datumGovernorates.size(); i++) {
            listGovernoratesSpinner.add(datumGovernorates.get(i).getName());
            listGovernoratesIds.add(datumGovernorates.get(i).getId());
        }
        final int[] id = {-1};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, listGovernoratesSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        binding.spnDonationAddCountry.setAdapter(adapter);
        binding.spnDonationAddCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                if (position != 0) {
                    id[0] = listGovernoratesIds.get(position - 1);
                    feedSpinnerViewModel.getCitiesListById(id[0]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void fillCitiesSpinner(List<DatumCities> datumCities) {
        List<String> listCitiesSpinner = new ArrayList<>();
        final List<Integer> listCitiesIds = new ArrayList<>();
        for (int i = 0; i < datumCities.size(); i++) {
            listCitiesSpinner.add(datumCities.get(i).getName());
            listCitiesIds.add(datumCities.get(i).getId());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, listCitiesSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        binding.spnDonationAddCity.setAdapter(adapter);
        binding.spnDonationAddCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                for (int i = 0; i < datumCities.size(); i++) {
                    if (datumCities.get(i).getName().equals(binding.spnDonationAddCity.getSelectedItem().toString())) {
                        city_Id = String.valueOf(datumCities.get(i).getId());
                        break;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.btn_map):
                checkLocation();
                break;
            case (R.id.btn_donation_add):
                createDonation();
                break;
        }
    }

    private void createDonation() {
        String patientName = Objects.requireNonNull(binding.etAddName.getText()).toString().trim();
        String patientAge = Objects.requireNonNull(binding.etAddAge.getText()).toString().trim();
        String bagsNumber = Objects.requireNonNull(binding.etAddBegsNumber.getText()).toString().trim();
        String hospitalName = Objects.requireNonNull(binding.etAddHospitalName.getText()).toString().trim();
        String hospitalAddress = Objects.requireNonNull(binding.tvHospitalAddress.getText()).toString().trim();
        String patientPhone = Objects.requireNonNull(binding.etDonationAddPhone.getText()).toString().trim();
        String patientNotes = Objects.requireNonNull(binding.etDonationAddNotes.getText()).toString().trim();
        String Lat = String.valueOf(lat);
        String Log = String.valueOf(lng);
        if (patientName.isEmpty()) {
            binding.etAddName.setError("error_field_required");
            binding.etAddName.requestFocus();
            return;
        }
        if (patientAge.isEmpty()) {
            binding.etAddAge.setError("error_field_required");
            binding.etAddAge.requestFocus();
            return;
        }

        if (bagsNumber.isEmpty()) {
            binding.etAddBegsNumber.setError("error_field_required");
            binding.etAddBegsNumber.requestFocus();
            return;
        }
        if (hospitalAddress.isEmpty()) {
            binding.tvHospitalAddress.setError("error_field_required");
            binding.tvHospitalAddress.requestFocus();
            return;
        }

        if (patientPhone.isEmpty()) {
            binding.etDonationAddPhone.setError("error_field_required");
            binding.etDonationAddPhone.requestFocus();
            return;
        }
        SharedPreferences pref = requireActivity().getSharedPreferences("login", MODE_PRIVATE);
        String token = pref.getString("token", "");
        donationAddViewModel.getDonationAddResponse(token, patientName, patientAge, blood_Id, bagsNumber, hospitalName, hospitalAddress
                , city_Id, patientPhone, patientNotes, Lat, Log);
    }

    private void getMyLocation() {

        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationClient.getCurrentLocation(100, new CancellationToken() {
            @Override
            public boolean isCancellationRequested() {
                return false;
            }

            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }
        }).addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                geocoder = new Geocoder(requireActivity(), Locale.getDefault());
                if (location != null) {
                    try {
                        lat = location.getLatitude();
                        lng = location.getLongitude();
                        addressList = geocoder.getFromLocation(lat, lng, 1);
                        String Address = addressList.get(0).getAddressLine(0);
                        String City = addressList.get(0).getAdminArea();
                        FullAddress = Address + ", " + City;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    binding.tvHospitalAddress.setText(FullAddress);
                } else {
                    Toast.makeText(requireActivity(), "location not detected", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        else {
            getMyLocation();

        }
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(requireActivity());
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Urls is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Urls", (paramDialogInterface, paramInt) -> {

                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                })
                .setNegativeButton("Cancel", (paramDialogInterface, paramInt) -> {

                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


}

