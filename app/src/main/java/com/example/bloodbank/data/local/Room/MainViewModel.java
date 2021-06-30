package com.example.bloodbank.data.local.Room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bloodbank.data.model.PostsData.Datum;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    /**
     * Wrapping the <list<Datum> with LiveData
     * to avoid requiring the data every time
     **/
   LiveData<List<Datum>> datumList;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase dataBase = AppDatabase.getInstance(this.getApplication());
        datumList = dataBase.PostDao().loadPosts();
    }



   public LiveData<List<Datum>> getDatumList() {
       return datumList;
   }
}
