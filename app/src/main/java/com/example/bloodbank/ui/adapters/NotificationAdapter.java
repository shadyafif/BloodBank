package com.example.bloodbank.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbank.data.model.NotificationData.Datum;
import com.example.bloodbank.databinding.NotificationCardviewLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    Context context;
    List<Datum> notificationList;

    public NotificationAdapter(Context context) {
        this.context = context;
        notificationList = new ArrayList<>();
    }

    public void addAll(List<Datum> items) {
        for (int i = 0; i < items.size(); i++) {
            add(items.get(i));
        }
    }

    private void add(Datum datum) {
        notificationList.add(datum);
        notifyItemInserted(notificationList.size() - 1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(context);
        return new ViewHolder(NotificationCardviewLayoutBinding.inflate(li));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        Datum currentItem = notificationList.get(position);
        holder.binding.tvNotificationTitle.setText(currentItem.getTitle());
        holder.binding.tvNotificationContent.setText(currentItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    static public class ViewHolder extends RecyclerView.ViewHolder {
        NotificationCardviewLayoutBinding binding;

        public ViewHolder(NotificationCardviewLayoutBinding layoutBinding) {
            super(layoutBinding.getRoot());
            binding = layoutBinding;
        }
    }
}
