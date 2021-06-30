package com.example.bloodbank.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.DonationDetails.Datum;
import com.example.bloodbank.databinding.FragmentDonationBinding;
import com.example.bloodbank.ui.adapters.DonationsAdapter;
import com.example.bloodbank.ui.viewmodels.DonationViewModel;
import com.example.bloodbank.utlies.IItemClick;
import com.example.bloodbank.utlies.OnEndless;

import static android.content.Context.MODE_PRIVATE;
import static com.example.bloodbank.ui.fragments.DonationDetailsFragment.donationDetailsFragment;
import static com.example.bloodbank.utlies.Extension.Replace;
import static com.example.bloodbank.utlies.Extension.makeCall;


public class DonationFragment extends Fragment implements IItemClick, View.OnClickListener {

    private FragmentDonationBinding binding;
    private DonationViewModel donationViewModel;
    private DonationsAdapter donationsAdapter;
    private int max_page = 1;

    public DonationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDonationBinding.inflate(inflater, container, false);
        initViews();

        donationViewModel.getDatumList().observe(requireActivity(), donationsDetails -> {
            donationsAdapter.addAll(donationsDetails);
            max_page = donationsDetails.size();

        });
        return binding.getRoot();
    }

    public void initViews() {
        binding.toolbar.setTitle(this.getResources().getString(R.string.donation));
        donationViewModel = new ViewModelProvider(this).get(DonationViewModel.class);
        donationsAdapter = new DonationsAdapter(requireContext(), this);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        binding.rvDonations.setLayoutManager(layoutManager);
        binding.rvDonations.setAdapter(donationsAdapter);
        OnEndless onEndlesslistener = new OnEndless(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page < max_page) {
                    getRecyclerView(current_page);
                } else {
                    Toast.makeText(getActivity(), "إنتهت القائمة ", Toast.LENGTH_SHORT).show();
                }
            }
        };

        binding.rvDonations.addOnScrollListener(onEndlesslistener);
        getRecyclerView(1);
        binding.ivDonationAdd.setOnClickListener(this);
    }

    private void getRecyclerView(int current_page) {
        SharedPreferences pref = requireActivity().getSharedPreferences("login", MODE_PRIVATE);
        String token = pref.getString("token", "");
        donationViewModel.getAllDonations(token, current_page);
    }


    @Override
    public void itemClick(View view, int itemId) {
        Datum datum = donationsAdapter.getDonationsList().get(itemId);
        int id = view.getId();
        switch (id) {
            case (R.id.iv_donation_call):
                makeCall(requireContext(), datum.getPhone());
                break;
            case (R.id.iv_donation_info):
                Replace(donationDetailsFragment(datum), R.id.frame_layout, requireActivity().getSupportFragmentManager().beginTransaction());
                break;
        }
    }

    @Override
    public void onClick(View v) {
        DonationAddFragment donationAddFragment = new DonationAddFragment();
        Replace(donationAddFragment, R.id.frame_layout, requireActivity().getSupportFragmentManager().beginTransaction());
    }
}