package com.example.order_food.service;

import android.content.Context;

import com.example.order_food.db.AppDatabase;
import com.example.order_food.db.entity.FavoriteFood;
import com.example.order_food.db.entity.Food;

public class FavoriteFoodService {
    private static FavoriteFoodService instance;
    private AppDatabase appDatabase;

    private FavoriteFoodService(Context context) {
        appDatabase = AppDatabase.getInstance(context);
    }
    public static FavoriteFoodService getInstance(Context context) {
        if (instance == null) {
            instance = new FavoriteFoodService(context);
        }
        return instance;
    }

    public boolean insert(FavoriteFood favfood) {
        try {
            appDatabase.favoriteDao().insertFood(favfood);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkFavFood(int uid, int pid){

        if (appDatabase.favoriteDao().getFavFood(uid, pid) != null){
            return true;
        }
        return false;
    }
}
