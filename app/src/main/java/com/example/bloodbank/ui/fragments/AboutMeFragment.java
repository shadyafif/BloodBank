package com.example.bloodbank.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.SettingDetails.Data;
import com.example.bloodbank.databinding.FragmentAboutMeBinding;
import com.example.bloodbank.ui.viewmodels.SettingViewModel;

import static android.content.Context.MODE_PRIVATE;


public class AboutMeFragment extends Fragment {

    private FragmentAboutMeBinding binding;
    private SettingViewModel settingViewModel;

    public AboutMeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAboutMeBinding.inflate(inflater, container, false);
        initViews();
        SharedPreferences pref = requireActivity().getSharedPreferences("login", MODE_PRIVATE);
        String token = pref.getString("token", "");
        settingViewModel.getUrls(token);
        settingViewModel.getData().observe(requireActivity(), data -> binding.tvAboutUs.setText(data.getAboutApp()));
        return binding.getRoot();
    }

    private void initViews() {
        binding.toolbar.setTitle(getResources().getString(R.string.about_us));
        settingViewModel = new ViewModelProvider(this).get(SettingViewModel.class);
    }
}