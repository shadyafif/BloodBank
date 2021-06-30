package com.example.bloodbank.ui.fragments;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.BloodTypesDetails.Datum;
import com.example.bloodbank.data.model.CitiesDetails.DatumCities;
import com.example.bloodbank.data.model.GovernoratesDetails.DatumGovernorates;
import com.example.bloodbank.data.model.ProfileData.Data;
import com.example.bloodbank.databinding.FragmentProfileBinding;
import com.example.bloodbank.ui.viewmodels.FeedSpinnerViewModel;
import com.example.bloodbank.ui.viewmodels.ProfileViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static com.example.bloodbank.utlies.Extension.showSnake;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;
    private FeedSpinnerViewModel feedSpinnerViewModel;
    private Calendar calendar, calendarLastDonation;
    DatePickerDialog.OnDateSetListener dateSetListener, dateSetListenerLastDonation;
    public String city_Id, blood_Id;
    private SharedPreferences pref;
    private String token;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedSpinnerViewModel = new ViewModelProvider(this).get(FeedSpinnerViewModel.class);
        feedSpinnerViewModel.getBloodTypeList();
        feedSpinnerViewModel.getGovernoratesList();
        feedSpinnerViewModel.getCitiesList();
        feedSpinnerViewModel.getDatumList().observe(requireActivity(), this::fillBloodSpinner);
        feedSpinnerViewModel.getDatumGovernoratesList().observe(requireActivity(), this::fillGovernoratesSpinner);
        feedSpinnerViewModel.getDatumCitiesListById().observe(requireActivity(), this::fillCitiesSpinner);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        initViews();
        profileViewModel.profileData().observe(requireActivity(), this::getProfileDetails);
        profileViewModel.profileUpdateData().observe(requireActivity(), this::updateProfileResponse);
        return binding.getRoot();
    }


    private void initViews() {
        binding.toolbar.setTitle(this.getResources().getString(R.string.update_profile));
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        pref = requireActivity().getSharedPreferences("login", MODE_PRIVATE);
        token = pref.getString("token", "");
        profileViewModel.getProfileDetails(token);
        setDatetimeBirthday();
        setDatetimeLastDonation();
        binding.tvProfileBirthday.setOnClickListener(this);
        binding.tvProfileLastDonation.setOnClickListener(this);
        binding.btnProfileUpdate.setOnClickListener(this);
    }

    private void getProfileDetails(Data data) {
        binding.etProfileName.setText(data.getClient().getName());
        binding.etProfileEmail.setText(data.getClient().getEmail());
        binding.etProfilePhone.setText(data.getClient().getPhone());
        binding.tvProfileBirthday.setText(data.getClient().getBirthDate());
        binding.tvProfileLastDonation.setText(data.getClient().getDonationLastDate());
        binding.spnProfileBloodType.setSelection(Integer.parseInt(data.getClient().getBloodTypeId()) - 1);
        binding.spnProfileCountry.setSelection(Integer.parseInt(data.getClient().getCity().getGovernorateId()) - 1);
        binding.spnProfileCity.setSelection(Integer.parseInt(data.getClient().getCityId()) - 1);


    }

    public void fillBloodSpinner(List<Datum> datum) {
        final List<Integer> listIds = new ArrayList<>();
        List<String> listBloodSpinner = new ArrayList<>();
        for (int i = 0; i < datum.size(); i++) {
            listBloodSpinner.add(datum.get(i).getName());
            listIds.add(datum.get(i).getId());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, listBloodSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        binding.spnProfileBloodType.setAdapter(adapter);
        binding.spnProfileBloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        List<String> listSpinner = new ArrayList<>();
        String GovernorateName = getResources().getString(R.string.ChooseGovernorate);
        listSpinner.add(GovernorateName);
        for (int i = 0; i < datumGovernorates.size(); i++) {
            listGovernoratesSpinner.add(datumGovernorates.get(i).getName());
            listGovernoratesIds.add(datumGovernorates.get(i).getId());
        }
        final int[] id = {1};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, listGovernoratesSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        binding.spnProfileCountry.setAdapter(adapter);
        binding.spnProfileCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                if (position != 0) {
                    id[0] = listGovernoratesIds.get(position);
                }
//                feedSpinnerViewModel.getCitiesListById(id[0]);
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
        binding.spnProfileCity.setAdapter(adapter);
        binding.spnProfileCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                for (int i = 0; i < datumCities.size(); i++) {
                    if (datumCities.get(i).getName().equals(binding.spnProfileCity.getSelectedItem().toString())) {
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


    public void setDatetimeBirthday() {
        calendar = Calendar.getInstance();
        dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelBirthday();
        };

    }

    private void updateLabelBirthday() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        binding.tvProfileBirthday.setText(sdf.format(calendar.getTime()));

    }

    public void setDatetimeLastDonation() {
        calendarLastDonation = Calendar.getInstance();
        dateSetListenerLastDonation = (view, year, month, dayOfMonth) -> {
            calendarLastDonation.set(Calendar.YEAR, year);
            calendarLastDonation.set(Calendar.MONTH, month);
            calendarLastDonation.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelLastDonation();
        };

    }

    private void updateLabelLastDonation() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        binding.tvProfileLastDonation.setText(sdf.format(calendarLastDonation.getTime()));
    }

    public void datePickerClick() {
        new DatePickerDialog(requireActivity(), dateSetListener, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void datePickerClickLastDonation() {
        new DatePickerDialog(requireActivity(), dateSetListenerLastDonation, calendarLastDonation
                .get(Calendar.YEAR), calendarLastDonation.get(Calendar.MONTH),
                calendarLastDonation.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.tv_Profile_Birthday):
                datePickerClick();
                break;
            case (R.id.tv_Profile_LastDonation):
                datePickerClickLastDonation();
                break;
            case (R.id.btn_Profile_Update):
                updateProfile();
                break;
        }
    }

    private void updateProfile() {
        String name = Objects.requireNonNull(binding.etProfileName.getText()).toString().trim();
        String phone = Objects.requireNonNull(binding.etProfilePhone.getText()).toString().trim();
        String email = Objects.requireNonNull(binding.etProfileEmail.getText()).toString().trim();
        String CityId = city_Id;
        String birthday = binding.tvProfileBirthday.getText().toString().trim();
        String lastDonation = binding.tvProfileLastDonation.getText().toString().trim();

        if (email.isEmpty()) {
            binding.etProfileEmail.setError("Email is required");
            binding.etProfileEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etProfileEmail.setError("Enter a valid email");
            binding.etProfileEmail.requestFocus();
            return;
        }

        profileViewModel.getProfileUpdate(token, name, email, birthday, phone, CityId, blood_Id, lastDonation);
    }

    private void updateProfileResponse(String s) {
        showSnake(requireView(), s);
    }
}