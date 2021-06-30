package com.example.bloodbank.ui.fragments;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbank.R;
import com.example.bloodbank.data.local.Room.AppDatabase;
import com.example.bloodbank.data.local.Room.AppExecutors;
import com.example.bloodbank.data.local.Room.MainViewModel;
import com.example.bloodbank.data.model.PostsData.Datum;
import com.example.bloodbank.databinding.FragmentFavoriteListBinding;
import com.example.bloodbank.ui.adapters.FavoriteAdapter;
import com.example.bloodbank.utlies.IItemClick;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

import static com.example.bloodbank.utlies.Extension.deleteAllFromRoom;
import static com.example.bloodbank.utlies.Extension.deleteFromRoom;

public class FavoriteListFragment extends Fragment implements IItemClick, View.OnClickListener {
    private FragmentFavoriteListBinding binding;
    private FavoriteAdapter favoriteAdapter;
    private AppDatabase mDb;

    public FavoriteListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteListBinding.inflate(inflater, container, false);
        initViews();
        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getDatumList().observe((LifecycleOwner) requireContext(), productData -> {
            binding.pbFavorite.setVisibility(View.INVISIBLE);
            favoriteAdapter.setLstPosts(productData);
        });
        return binding.getRoot();
    }

    private void initViews() {
        binding.toolbar.setTitle(this.getResources().getString(R.string.favorite));
        binding.pbFavorite.setVisibility(View.VISIBLE);
        mDb = AppDatabase.getInstance(requireActivity());
        favoriteAdapter = new FavoriteAdapter(requireContext(), this);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        binding.RecFavorite.setLayoutManager(layoutManager);
        binding.RecFavorite.setAdapter(favoriteAdapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                List<Datum> dataList = favoriteAdapter.getLstPosts();
                deleteFromRoom(mDb, dataList.get(position));
            }
        }).attachToRecyclerView(binding.RecFavorite);
        binding.ivDeleteFavorite.setOnClickListener(this);
    }

    @Override
    public void itemClick(View view, int itemId) {

    }
    public void showDialog() {
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.newcustom_layout);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        FloatingActionButton floatingActionButtonNo = dialog.findViewById(R.id.no);
        floatingActionButtonNo.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

//        FrameLayout mDialogOk = dialog.findViewById(R.id.frmOk);
        FloatingActionButton floatingActionButton= dialog.findViewById(R.id.ok);
        floatingActionButton.setOnClickListener(v -> {
            deleteAllFromRoom(mDb);
            dialog.dismiss();
        });



        dialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_delete_favorite) {
            showDialog();
        }
    }


}