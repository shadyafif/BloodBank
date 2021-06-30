package com.example.bloodbank.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bloodbank.data.model.PostsData.Datum;
import com.example.bloodbank.databinding.PostCardLayoutBinding;
import com.example.bloodbank.utlies.IItemClick;
import java.util.ArrayList;
import java.util.List;
import static com.example.bloodbank.utlies.Extension.loadImageSize;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.viewHolder> {
    private final Context context;
    private final ArrayList<Datum> postsList;
    private final IItemClick iItemClick;

    public PostsAdapter(Context context, IItemClick iItemClick) {
        this.context = context;
        this.iItemClick = iItemClick;
        postsList = new ArrayList<>();
    }

    public ArrayList<Datum> getPostsList() {
        return postsList;
    }

    public void addAll(List<Datum> items) {
        for (int i = 0; i < items.size(); i++) {
            add(items.get(i));
        }
    }

    private void add(Datum datum) {
        postsList.add(datum);
        notifyItemInserted(postsList.size() - 1);
    }

    @NonNull
    @Override
    public PostsAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(context);
        return new PostsAdapter.viewHolder(PostCardLayoutBinding.inflate(li), iItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.viewHolder holder, int position) {
        Datum currentItem = postsList.get(position);
        loadImageSize(context, currentItem.getThumbnailFullPath(), holder.binding.ivPost,400,400);
        holder.binding.tvPost.setText(currentItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    static public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final IItemClick iItemClick;
        private final PostCardLayoutBinding binding;

        public viewHolder(PostCardLayoutBinding layoutBinding, IItemClick itemClickListener) {
            super(layoutBinding.getRoot());
            binding = layoutBinding;
            iItemClick = itemClickListener;
            binding.ivPost.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            iItemClick.itemClick(v, getAdapterPosition());
        }
    }
}
