package com.example.order_food.db.DAO;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.order_food.db.entity.OrderDetail;

import java.util.List;

@Dao
public interface OrderDetailDao {
    @Insert
    void insertAll(List<OrderDetail> orderDetails);
}
