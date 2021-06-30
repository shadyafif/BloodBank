package com.example.bloodbank.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.bloodbank.data.model.DonationDetails.DonationsDetails;
import com.example.bloodbank.data.model.PostsData.Datum;
import com.example.bloodbank.data.model.PostsData.Posts;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsRepo extends GlobalRepo {
    MutableLiveData<List<Datum>> postList = new MutableLiveData<>();
    List<Datum> datumList;

    public PostsRepo() {
    }

    public MutableLiveData<List<Datum>> getPostList() {
        return postList;
    }

    public void GetPostsLst(String token, int page) {
        getIRetrofitApi().getPosts(token, page).enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(@NonNull Call<Posts> call, @NonNull Response<Posts> response) {
                assert response.body() != null;
                datumList = response.body().getData().getData();
                postList.postValue(datumList);
            }

            @Override
            public void onFailure(@NonNull Call<Posts> call, @NonNull Throwable t) {

            }
        });
    }
}
