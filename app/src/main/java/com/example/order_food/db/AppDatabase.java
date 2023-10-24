package com.example.order_food.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.order_food.db.DAO.FoodDao;
import com.example.order_food.db.DAO.OrderDao;
import com.example.order_food.db.DAO.UserDao;
import com.example.order_food.db.entity.Food;
import com.example.order_food.db.entity.Order;
import com.example.order_food.db.entity.User;
import com.example.order_food.util.LocalDateConverter;

@Database(entities = {User.class, Food.class, Order.class}, version = 4)
@TypeConverters(LocalDateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app_database")
                    .fallbackToDestructiveMigration().allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract UserDao userDao();

    public abstract FoodDao foodDao();

    public abstract OrderDao orderDao();
}