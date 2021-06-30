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
import com.example.bloodbank.data.model.PostsData.Datum;
import com.example.bloodbank.databinding.FragmentPostsBinding;
import com.example.bloodbank.ui.adapters.PostsAdapter;
import com.example.bloodbank.ui.viewmodels.PostsViewModel;
import com.example.bloodbank.utlies.IItemClick;
import com.example.bloodbank.utlies.OnEndless;

import static android.content.Context.MODE_PRIVATE;
import static com.example.bloodbank.ui.fragments.PostDetailsFragment.postDetailsFragment;
import static com.example.bloodbank.utlies.Extension.Replace;


public class PostsFragment extends Fragment implements IItemClick {

    private FragmentPostsBinding binding;
    private PostsViewModel postsViewModel;
    private PostsAdapter postsAdapter;
    private int max_page = 1;
    private SharedPreferences pref;
    private Datum datum;

    public PostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostsBinding.inflate(inflater, container, false);
        initViews();
        postsViewModel.getDatumList();
        postsViewModel.getDatumList().observe(requireActivity(), data -> {
            binding.pbPosts.setVisibility(View.GONE);
            postsAdapter.addAll(data);
        });
        return binding.getRoot();
    }

    private void initViews() {
        binding.toolbar.setTitle(this.getResources().getString(R.string.post));
        binding.pbPosts.setVisibility(View.VISIBLE);
        postsViewModel = new ViewModelProvider(this).get(PostsViewModel.class);
        postsAdapter = new PostsAdapter(requireContext(), this);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        binding.RecPosts.setLayoutManager(layoutManager);
        binding.RecPosts.setAdapter(postsAdapter);
        OnEndless onEndlessListener = new OnEndless(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page < max_page) {
                    getRecyclerView(current_page);
                } else {
                    Toast.makeText(getActivity(), "إنتهت القائمة ", Toast.LENGTH_SHORT).show();
                }
            }
        };

        binding.RecPosts.addOnScrollListener(onEndlessListener);
        getRecyclerView(1);

    }


    private void getRecyclerView(int current_page) {
        pref = requireActivity().getSharedPreferences("login", MODE_PRIVATE);
        String token = pref.getString("token", "");
        postsViewModel.getAllPosts(token, current_page);
    }

    @Override
    public void itemClick(View view, int itemId) {
        datum = postsAdapter.getPostsList().get(itemId);
        Replace(postDetailsFragment(datum), R.id.frame_layout, requireActivity().getSupportFragmentManager().beginTransaction());
    }
}