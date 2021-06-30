package com.example.bloodbank.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbank.data.model.PostsData.Datum;
import com.example.bloodbank.databinding.FavoriteCardviewLayoutBinding;
import com.example.bloodbank.utlies.IItemClick;

import java.util.ArrayList;
import java.util.List;

import static com.example.bloodbank.utlies.Extension.loadImageSize;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private Context context;
    private List<Datum> lstPosts = new ArrayList<>();
    private IItemClick iItemClick;

    public FavoriteAdapter(Context context, IItemClick iItemClick) {
        this.context = context;
        this.iItemClick = iItemClick;
    }

    public List<Datum> getLstPosts() {
        return lstPosts;
    }

    public void setLstPosts(List<Datum> lstPosts) {

        this.lstPosts = lstPosts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(context);
        return new ViewHolder(FavoriteCardviewLayoutBinding.inflate(li), iItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        Datum currentItem = lstPosts.get(position);
        loadImageSize(context, currentItem.getThumbnailFullPath(), holder.binding.ivFavorite, 400, 400);
        holder.binding.tvFavorite.setText(currentItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return lstPosts.size();
    }

    static public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private IItemClick iItemClick;
        private FavoriteCardviewLayoutBinding binding;

        public ViewHolder(FavoriteCardviewLayoutBinding layoutBinding, IItemClick itemClickListener) {
            super(layoutBinding.getRoot());
            binding = layoutBinding;
            iItemClick = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            iItemClick.itemClick(v, getAdapterPosition());
        }
    }
}
