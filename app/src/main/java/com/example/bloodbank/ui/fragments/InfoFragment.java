package com.example.bloodbank.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bloodbank.R;
import com.example.bloodbank.databinding.FragmentInfoBinding;

import static com.example.bloodbank.utlies.Extension.Replace;


public class InfoFragment extends Fragment implements View.OnClickListener {

    private FragmentInfoBinding binding;

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        initViews();

        return binding.getRoot();
    }

    private void initViews() {
        binding.toolbar.setTitle(this.getResources().getString(R.string.Info));
        binding.txtInfoUpdateAccount.setOnClickListener(this);
        binding.txtInfoFavorite.setOnClickListener(this);
        binding.txtInfoContactUs.setOnClickListener(this);
        binding.txtInfoReport.setOnClickListener(this);
        binding.txtInfoAboutUs.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_Info_UpdateAccount:
                ProfileFragment profileFragment = new ProfileFragment();
                Replace(profileFragment, R.id.frame_layout, requireActivity().getSupportFragmentManager().beginTransaction());
                break;

            case R.id.txt_Info_Favorite:
                FavoriteListFragment favoriteListFragment = new FavoriteListFragment();
                Replace(favoriteListFragment, R.id.frame_layout, requireActivity().getSupportFragmentManager().beginTransaction());

            case R.id.txt_Info_Report:

                break;

            case R.id.txt_Info_ContactUs:
                ContactUsFragment contactUsFragment = new ContactUsFragment();
                Replace(contactUsFragment, R.id.frame_layout, requireActivity().getSupportFragmentManager().beginTransaction());
                break;

            case R.id.txt_Info_AboutUs:
                AboutMeFragment aboutMeFragment = new AboutMeFragment();
                Replace(aboutMeFragment, R.id.frame_layout, requireActivity().getSupportFragmentManager().beginTransaction());
                break;
            default:
                break;
        }
    }
}