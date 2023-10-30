package com.example.order_food.db.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.order_food.db.entity.Order;

@Dao
public  interface OrderDao {
    @Query("SELECT COUNT(*) FROM `Order`")
    int getOrderCount();

    @Insert
    long insert(Order order);
}
