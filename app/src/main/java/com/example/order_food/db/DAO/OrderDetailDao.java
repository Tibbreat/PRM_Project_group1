package com.example.order_food.db.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.order_food.db.entity.Order;
import com.example.order_food.db.entity.OrderDetail;

import java.util.List;

@Dao
public interface OrderDetailDao {
    @Insert
    void insertAll(List<OrderDetail> orderDetails);
    @Query("SELECT * FROM `OrderDetail` WHERE orderId= :id")
    List<OrderDetail> getOrderDetailByOrderID(int id);
    @Query("SELECT * FROM `OrderDetail` WHERE orderId = :orderId")
    List<OrderDetail> getAllOrderDetailByOrderId(int orderId);
}
