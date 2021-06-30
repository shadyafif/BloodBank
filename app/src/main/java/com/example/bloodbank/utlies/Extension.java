package com.example.bloodbank.utlies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bloodbank.R;
import com.example.bloodbank.data.local.Room.AppDatabase;
import com.example.bloodbank.data.local.Room.AppExecutors;
import com.example.bloodbank.data.model.PostsData.Datum;
import com.google.android.material.snackbar.Snackbar;

import static androidx.core.content.ContextCompat.startActivity;

public class Extension {
    public static void Replace(Fragment fragment, int id, FragmentTransaction fragmentTransaction) {
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static void Add(Fragment fragment, int id, FragmentTransaction fragmentTransaction) {
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static ImageView loadImage(Context context, String url, ImageView image) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().placeholder(R.drawable.progress_animation))
                .into(image);
        return image;
    }

    public static ImageView loadImageSize(Context context, String url, ImageView image, int width, int height) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().placeholder(R.drawable.progress_animation).override(width, height))
                .into(image);
        return image;
    }

    public static void showSnake(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();

    }

    public static void makeCall(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(context, intent, null);
    }

    public static void insertInRoom(AppDatabase mDb, Datum datum) {
        AppExecutors.getInstance().diskIO().execute(() -> mDb.PostDao().insertPostFav(datum));
    }

    public static void deleteFromRoom(AppDatabase mDb, Datum datum) {
        AppExecutors.getInstance().diskIO().execute(() -> mDb.PostDao().deletePostFav(datum));
    }

    public static void deleteAllFromRoom(AppDatabase mDb) {
        AppExecutors.getInstance().diskIO().execute(() -> mDb.PostDao().deleteAll());
    }



}
