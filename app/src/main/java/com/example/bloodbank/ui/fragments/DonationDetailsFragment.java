package com.example.bloodbank.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.DonationDetails.Datum;
import com.example.bloodbank.databinding.FragmentDonationDetailsBinding;

import static com.example.bloodbank.utlies.Extension.makeCall;

public class DonationDetailsFragment extends Fragment implements View.OnClickListener {
    private FragmentDonationDetailsBinding binding;
    private Datum datum;


    public DonationDetailsFragment() {
        // Required empty public constructor
    }

    public static DonationDetailsFragment donationDetailsFragment(Datum datum) {
        DonationDetailsFragment donationDetailsFragment = new DonationDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("donationDetails", datum);
        donationDetailsFragment.setArguments(bundle);
        return donationDetailsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        datum = getArguments().getParcelable("donationDetails");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDonationDetailsBinding.inflate(inflater, container, false);
        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        binding.toolbar.setTitle(this.getResources().getString(R.string.donation_details));
        binding.tvDonationDetailsName.setText(datum.getPatientName());
        binding.tvDonationDetailsAge.setText(datum.getPatientAge());
        binding.tvDonationDetailsBloodType.setText(datum.getBloodType().getName());
        binding.tvDonationDetailsBugsNumber.setText(datum.getBagsNum());
        binding.tvDonationDetailsHospitalName.setText(datum.getHospitalName());
        binding.tvDonationDetailsHospitalAddress.setText(datum.getHospitalAddress());
        binding.tvDonationDetailsPhone.setText(datum.getPhone());
        binding.tvDonationDetailsNotes.setText(datum.getNotes());
        binding.btnMakeCall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        makeCall(requireContext(), datum.getPhone());
    }
}