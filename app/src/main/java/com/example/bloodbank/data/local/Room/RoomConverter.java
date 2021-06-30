package com.example.bloodbank.data.local.Room;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Date;
import java.util.List;

public class RoomConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static List<String> restoreList(String listOfString) {
        return new Gson().fromJson(listOfString, new TypeToken<List<String>>() {}.getType());
    }

    @TypeConverter
    public static String saveList(List<String> listOfString) {
        return new Gson().toJson(listOfString);
    }



    // Two converter methods for Category Class
//    @TypeConverter
//    public static List<Category> fromStringCategory(String value) {
//        Type listType = new TypeToken<List<Category>>() {
//        }.getType();
//        return new Gson().fromJson(value, listType);
//    }
//
//    @TypeConverter
//    public static String fromClassCategory(List<Category> list) {
//        Gson gson = new Gson();
//        return gson.toJson(list);
//    }


}
