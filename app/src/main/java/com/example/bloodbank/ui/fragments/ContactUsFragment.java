package com.example.bloodbank.ui.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.SettingDetails.Data;
import com.example.bloodbank.databinding.FragmentContectUsBinding;
import com.example.bloodbank.ui.viewmodels.SettingViewModel;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static com.example.bloodbank.utlies.Extension.showSnake;

public class ContactUsFragment extends Fragment implements View.OnClickListener {
    private FragmentContectUsBinding binding;
    private SettingViewModel settingViewModel;
    String face, twitter, youtube, insta, whats, google;

    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContectUsBinding.inflate(inflater, container, false);
        initViews();
        SharedPreferences pref = requireActivity().getSharedPreferences("login", MODE_PRIVATE);
        String token = pref.getString("token", "");
        settingViewModel.getUrls(token);
        settingViewModel.getData().observe(requireActivity(), this::feedUrls);
        settingViewModel.getDataContact().observe(requireActivity(), s -> {
            showSnake(requireView(), s);
        });
        return binding.getRoot();
    }


    private void initViews() {
        binding.toolbar.setTitle(getResources().getString(R.string.contact_us));
        settingViewModel = new ViewModelProvider(this).get(SettingViewModel.class);
        binding.ivFacebook.setOnClickListener(this);
        binding.ivTwitter.setOnClickListener(this);
        binding.ivWhats.setOnClickListener(this);
        binding.ivYoutube.setOnClickListener(this);
        binding.ivGoogle.setOnClickListener(this);
        binding.ivInsta.setOnClickListener(this);
        binding.btnContactUsSend.setOnClickListener(this);
    }

    private void feedUrls(Data data) {
        face = data.getFacebookUrl();
        twitter = data.getTwitterUrl();
        youtube = data.getYoutubeUrl();
        insta = data.getInstagramUrl();
        google = data.getGoogleUrl();
        whats = data.getWhatsapp();
    }

    public void socialContact(String content) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(content));
        startActivity(browserIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.iv_facebook):
                socialContact(face);
                break;
            case (R.id.iv_twitter):
                socialContact(twitter);
                break;
            case (R.id.iv_youtube):
                socialContact(youtube);
                break;
            case (R.id.iv_whats):
                textWhats();
                break;
            case (R.id.iv_google):
                socialContact(google);
                break;
            case (R.id.iv_insta):
                socialContact(insta);
                break;
            case (R.id.btn_ContactUs_Send):
                contactUs();
                break;
        }
    }

    private void textWhats() {
        PackageManager pm = requireActivity().getPackageManager();
        try {
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "YOUR TEXT HERE";
            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(requireActivity(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void contactUs() {
        SharedPreferences pref = requireActivity().getSharedPreferences("login", MODE_PRIVATE);
        String token = pref.getString("token", "");
        String title = Objects.requireNonNull(binding.etContactUsTitle.getText()).toString().trim();
        String message = Objects.requireNonNull(binding.etContactUsMessage.getText()).toString().trim();
        settingViewModel.getContactUs(token, title, message);
        binding.etContactUsTitle.setText("");
        binding.etContactUsMessage.setText("");
    }
}