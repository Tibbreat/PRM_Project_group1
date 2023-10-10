package com.example.order_food.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.order_food.db.DAO.UserDao;
import com.example.order_food.db.entity.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}