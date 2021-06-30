package com.example.bloodbank.ui.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.BloodTypesDetails.Datum;
import com.example.bloodbank.data.model.CitiesDetails.DatumCities;
import com.example.bloodbank.data.model.GovernoratesDetails.DatumGovernorates;
import com.example.bloodbank.databinding.ActivitySignUpBinding;
import com.example.bloodbank.ui.viewmodels.FeedSpinnerViewModel;
import com.example.bloodbank.ui.viewmodels.SignUpViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private FeedSpinnerViewModel feedSpinnerViewModel;
    private SignUpViewModel signUpViewModel;
    private ActivitySignUpBinding binding;
    private Calendar calendar, calendarLastDonation;
    DatePickerDialog.OnDateSetListener dateSetListener, dateSetListenerLastDonation;
    public String city_Id, blood_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        initViews();
        feedSpinnerViewModel.getBloodTypeList();
        feedSpinnerViewModel.getGovernoratesList();
        feedSpinnerViewModel.getDatumList().observe(this, this::fillBloodSpinner);
        feedSpinnerViewModel.getDatumGovernoratesList().observe(this, this::fillGovernoratesSpinner);
        feedSpinnerViewModel.getDatumCitiesListById().observe(this, this::fillCitiesSpinner);
        signUpViewModel.getSignUpAdd().observe(this, this::responseSuccess);
        setContentView(binding.getRoot());
    }


    private void initViews() {
        binding.toolbar.setTitle(this.getResources().getString(R.string.sign_up));
        setDatetimeBirthday();
        setDatetimeLastDonation();
        feedSpinnerViewModel = new ViewModelProvider(this).get(FeedSpinnerViewModel.class);
        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        binding.tvSignUpBirthday.setOnClickListener(this);
        binding.tvSignUpLastDonation.setOnClickListener(this);
        binding.btnSignUp.setOnClickListener(this);
    }

    public void fillBloodSpinner(List<Datum> datum) {
        List<String> listSpinner = new ArrayList<>();
        String BloodTypeName = getResources().getString(R.string.ChooseBloodType);
        listSpinner.add(BloodTypeName);
        final List<Integer> listIds = new ArrayList<>();

        for (int i = 0; i < datum.size(); i++) {
            listSpinner.add(datum.get(i).getName());
            listIds.add(datum.get(i).getId());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        binding.spnSignUpBloodType.setAdapter(adapter);
        binding.spnSignUpBloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listGovernoratesSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        binding.spnSignUpCountry.setAdapter(adapter);
        binding.spnSignUpCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listCitiesSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        binding.spnSignUpCity.setAdapter(adapter);
        binding.spnSignUpCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                for (int i = 0; i < datumCities.size(); i++) {
                    if (datumCities.get(i).getName().equals(binding.spnSignUpCity.getSelectedItem().toString())) {
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
            case (R.id.tv_SignUp_Birthday):
                datePickerClick();
                break;
            case (R.id.tv_SignUp_LastDonation):
                datePickerClickLastDonation();
                break;
            case (R.id.btn_SignUp):
                createUser();
                break;
        }
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
        binding.tvSignUpBirthday.setText(sdf.format(calendar.getTime()));

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
        binding.tvSignUpLastDonation.setText(sdf.format(calendarLastDonation.getTime()));
    }

    public void datePickerClick() {
        new DatePickerDialog(SignUpActivity.this, dateSetListener, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void datePickerClickLastDonation() {
        new DatePickerDialog(SignUpActivity.this, dateSetListenerLastDonation, calendarLastDonation
                .get(Calendar.YEAR), calendarLastDonation.get(Calendar.MONTH),
                calendarLastDonation.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void createUser() {
        String name = Objects.requireNonNull(binding.etSignUpName.getText()).toString().trim();
        String phone = Objects.requireNonNull(binding.etSignUpPhone.getText()).toString().trim();
        String email = Objects.requireNonNull(binding.etSignUpEmail.getText()).toString().trim();
        String birthday = binding.tvSignUpBirthday.getText().toString().trim();
        String password = Objects.requireNonNull(binding.etSignUpPassword.getText()).toString().trim();
        String confirmPassword = Objects.requireNonNull(binding.etSignUpConfirmPassword.getText()).toString().trim();
        String lastDonation = binding.tvSignUpLastDonation.getText().toString().trim();

        if (name.isEmpty()) {
            binding.etSignUpName.setError("name is required");
            binding.etSignUpName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            binding.etSignUpEmail.setError("Email is required");
            binding.etSignUpEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etSignUpEmail.setError("Enter a valid email");
            binding.etSignUpEmail.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            binding.etSignUpPhone.setError("Email is required");
            binding.etSignUpPhone.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            binding.etSignUpPassword.setError("password is required");
            binding.etSignUpPassword.requestFocus();
            return;
        }
        if (!password.equals(confirmPassword)) {
            binding.etSignUpConfirmPassword.setError("password and confirm password is not match");
            binding.etSignUpConfirmPassword.requestFocus();
        }
        signUpViewModel.getCreateUserResponse(name, email, birthday, city_Id, phone,
                lastDonation, password, confirmPassword, blood_Id);

    }

    private void responseSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
    }
}