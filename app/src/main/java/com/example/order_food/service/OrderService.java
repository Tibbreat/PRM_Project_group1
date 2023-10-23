package com.example.order_food.service;

import android.content.Context;

import com.example.order_food.db.AppDatabase;

import java.util.List;

public class OrderService {
    private static OrderService instance;
    private AppDatabase appDatabase;
    private OrderService(Context context) {
        appDatabase = AppDatabase.getInstance(context);
    }
    public static OrderService getInstance(Context context) {
        if (instance == null) {
            instance = new OrderService(context);
        }
        return instance;
    }

    public int getOrderCount() {
        return appDatabase.orderDao().getOrderCount();
    }

}
