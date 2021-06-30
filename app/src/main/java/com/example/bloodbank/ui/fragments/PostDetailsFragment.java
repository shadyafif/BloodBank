package com.example.bloodbank.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bloodbank.R;
import com.example.bloodbank.data.local.Room.AppDatabase;
import com.example.bloodbank.data.local.Room.AppExecutors;
import com.example.bloodbank.data.model.PostsData.Datum;
import com.example.bloodbank.databinding.FragmentPostDetailsBinding;

import static com.example.bloodbank.utlies.Extension.deleteFromRoom;
import static com.example.bloodbank.utlies.Extension.insertInRoom;
import static com.example.bloodbank.utlies.Extension.loadImage;
import static com.example.bloodbank.utlies.Extension.showSnake;


public class PostDetailsFragment extends Fragment implements View.OnClickListener {

    private FragmentPostDetailsBinding binding;
    private Datum datum;
    private AppDatabase mDb;

    public PostDetailsFragment() {
        // Required empty public constructor
    }

    public static PostDetailsFragment postDetailsFragment(Datum datum) {
        PostDetailsFragment postDetailsFragment = new PostDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("PostDetails", datum);
        postDetailsFragment.setArguments(bundle);
        return postDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        datum = getArguments().getParcelable("PostDetails");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostDetailsBinding.inflate(inflater, container, false);
        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        binding.toolbar.setTitle(datum.getTitle());
        loadImage(requireContext(), datum.getThumbnailFullPath(), binding.ivPostDetails);
        mDb = AppDatabase.getInstance(requireActivity().getApplicationContext());
        binding.tvPostDetailsTitle.setText(datum.getTitle());
        binding.tvivPostDetailsContent.setText(datum.getContent());
        String PostName = binding.tvPostDetailsTitle.getText().toString().trim();
        binding.chFavList.setOnClickListener(this);
        AppExecutors.getInstance().diskIO().execute(() -> {
            Datum datum = mDb.PostDao().fetchById(PostName);
            if (datum != null) {
                binding.chFavList.setChecked(true);
            } else {
                binding.chFavList.setChecked(false);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (binding.chFavList.isChecked()) {
            String message = requireActivity().getResources().getString(R.string.AddedSuccessfully);
            insertInRoom(mDb, datum);
            showSnake(v, message);

        } else {
            String deleteMessage = requireActivity().getResources().getString(R.string.DeletedSuccessfully);
            deleteFromRoom(mDb, datum);
            datum.setIsFavourite(false);
            showSnake(v, deleteMessage);
        }

    }
}