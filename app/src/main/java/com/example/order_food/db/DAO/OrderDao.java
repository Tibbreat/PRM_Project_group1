package com.example.order_food.db.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.order_food.db.entity.Order;

import java.util.List;

@Dao
public  interface OrderDao {
    @Query("SELECT COUNT(*) FROM `Order`")
    int getOrderCount();

    @Insert
    long insert(Order order);
    @Query("SELECT * FROM `Order` WHERE userID = :id ORDER BY id DESC")
    List<Order> getOrderByUserID(int id);
    @Update
    int updateOrder(Order order);
    @Query("SELECT * FROM `Order` WHERE id= :id")
    Order getOrderByOrderID(int id);
}
