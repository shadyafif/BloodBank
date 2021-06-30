package com.example.bloodbank.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbank.data.model.DonationDetails.Datum;
import com.example.bloodbank.databinding.DonationCardviewLayoutBinding;
import com.example.bloodbank.utlies.IItemClick;

import java.util.ArrayList;
import java.util.List;

public class DonationsAdapter extends RecyclerView.Adapter<DonationsAdapter.viewHolder> {
    private final Context context;
    private final ArrayList<Datum> donationsList;
    private final IItemClick iItemClick;

    public DonationsAdapter(Context context, IItemClick iItemClick) {
        this.context = context;
        this.iItemClick = iItemClick;
        donationsList = new ArrayList<>();
    }


    public ArrayList<Datum> getDonationsList() {
        return donationsList;
    }

    public void addAll(List<Datum> items) {
        for (int i = 0; i < items.size(); i++) {
            add(items.get(i));
        }
    }

    private void add(Datum datum) {
        donationsList.add(datum);
        notifyItemInserted(donationsList.size() - 1);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(context);
        return new viewHolder(DonationCardviewLayoutBinding.inflate(li), iItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Datum currentItem = donationsList.get(position);
        holder.binding.tvDonationPatient.setText(currentItem.getPatientName());
        holder.binding.tvDonationHospital.setText(currentItem.getHospitalName());
        holder.binding.tvDonationCity.setText(currentItem.getCity().getName());
        holder.binding.tvBloodType.setText(currentItem.getBloodType().getName());
    }

    @Override
    public int getItemCount() {
        return donationsList.size();
    }

    static public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final IItemClick iItemClick;
        private final DonationCardviewLayoutBinding binding;

        public viewHolder(DonationCardviewLayoutBinding layoutBinding, IItemClick itemClickListener) {
            super(layoutBinding.getRoot());
            binding = layoutBinding;
            iItemClick = itemClickListener;
            binding.ivDonationCall.setOnClickListener(this);
            binding.ivDonationInfo.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iItemClick.itemClick(view, getAdapterPosition());
        }
    }
}
