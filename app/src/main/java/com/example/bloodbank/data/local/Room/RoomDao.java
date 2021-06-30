package com.example.bloodbank.data.local.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bloodbank.data.model.PostsData.Datum;

import java.util.List;

@Dao
public interface RoomDao {

    //WishListDao
    @Query("SELECT * FROM ArticleData")
    LiveData<List<Datum>> loadPosts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPostFav(Datum datum);


    @Delete
    void deletePostFav(Datum datum);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updatePost(Datum datum);

    @Query("SELECT * FROM ArticleData WHERE id= :id")
    LiveData<List<Datum>> loadAllِArticleById(int id);

    @Query("SELECT * FROM ArticleData WHERE title = :title")
    Datum fetchById(String title);

    @Query("DELETE FROM ArticleData")
    void deleteAll();


}
