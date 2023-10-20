package com.example.order_food.service;

import android.content.Context;

import com.example.order_food.db.AppDatabase;
import com.example.order_food.db.entity.Food;
import com.example.order_food.db.entity.User;

public class UserService {
    private static UserService instance;
    private AppDatabase appDatabase;
    private UserService(Context context) {
        appDatabase = AppDatabase.getInstance(context);
    }
    public static UserService getInstance(Context context) {
        if (instance == null) {
            instance = new UserService(context);
        }
        return instance;
    }
    public boolean insert(User user) {
        try {
            appDatabase.userDao().insertUser(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
