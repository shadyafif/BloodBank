package com.example.bloodbank.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.bloodbank.R;
import com.example.bloodbank.databinding.ActivityMainBinding;
import com.example.bloodbank.ui.fragments.DonationFragment;
import com.example.bloodbank.ui.fragments.InfoFragment;
import com.example.bloodbank.ui.fragments.NotificationFragment;
import com.example.bloodbank.ui.fragments.PostsFragment;
import com.example.bloodbank.ui.viewmodels.NotificationViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.bloodbank.utlies.Extension.Add;
import static com.example.bloodbank.utlies.Extension.Replace;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    ActivityMainBinding binding;
    NotificationViewModel notificationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        initViews();
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this);
        notificationViewModel.NotificationNumber().observe(this, this::getCount);
        setContentView(binding.getRoot());
    }

    private void getCount(Integer integer) {
        binding.badge.setNumber(integer);
    }


    public void initViews() {
        DonationFragment donationFragment = new DonationFragment();
        Add(donationFragment, R.id.frame_layout, getSupportFragmentManager().beginTransaction());
        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        notificationViewModel.getNotificationNumber("W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl");

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_donation:
                DonationFragment donationFragment = new DonationFragment();
                Replace(donationFragment, R.id.frame_layout, getSupportFragmentManager().beginTransaction());
                break;

            case R.id.ic_article:
                PostsFragment postsFragment = new PostsFragment();
                Replace(postsFragment, R.id.frame_layout, getSupportFragmentManager().beginTransaction());
                break;

            case R.id.ic_info:
                InfoFragment infoFragment = new InfoFragment();
                Replace(infoFragment, R.id.frame_layout, getSupportFragmentManager().beginTransaction());
                break;
            case R.id.ic_notification:
                binding.badge.setNumber(0);
                NotificationFragment notificationFragment = new NotificationFragment();
                Replace(notificationFragment, R.id.frame_layout, getSupportFragmentManager().beginTransaction());
                break;
        }

        return true;
    }


}