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
import com.example.bloodbank.databinding.FragmentNotificationBinding;
import com.example.bloodbank.ui.adapters.NotificationAdapter;
import com.example.bloodbank.ui.viewmodels.NotificationViewModel;
import com.example.bloodbank.utlies.OnEndless;

import static android.content.Context.MODE_PRIVATE;

public class NotificationFragment extends Fragment {

    private FragmentNotificationBinding binding;
    private NotificationViewModel notificationViewModel;
    private NotificationAdapter notificationAdapter;
    private int max_page = 1;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        initViews();
        notificationViewModel.getNotificationDataList().observe(requireActivity(), data -> {
            notificationAdapter.addAll(data);
            max_page = data.size();
        });
        return binding.getRoot();
    }

    private void initViews() {
        binding.toolbar.setTitle(this.getResources().getString(R.string.notification));
        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        notificationAdapter = new NotificationAdapter(requireContext());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        binding.rvNotifications.setLayoutManager(layoutManager);
        binding.rvNotifications.setAdapter(notificationAdapter);
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

        binding.rvNotifications.addOnScrollListener(onEndlesslistener);
        getRecyclerView(1);
    }

    private void getRecyclerView(int current_page) {
        SharedPreferences pref = requireActivity().getSharedPreferences("login", MODE_PRIVATE);
        String token = pref.getString("token", "");
        notificationViewModel.getNotificationDetails("W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl", current_page);
    }
}