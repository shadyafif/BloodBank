package com.example.bloodbank.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bloodbank.data.model.PostsData.Datum;
import com.example.bloodbank.data.repository.PostsRepo;

import java.util.List;

public class PostsViewModel extends AndroidViewModel {
    private PostsRepo postsRepo;

    public PostsViewModel(@NonNull Application application) {
        super(application);
        postsRepo = new PostsRepo();
    }

    public MutableLiveData<List<Datum>> getDatumList() {
        return postsRepo.getPostList();
    }

    public void getAllPosts(String token, int page) {
        postsRepo.GetPostsLst(token, page);
    }
}
