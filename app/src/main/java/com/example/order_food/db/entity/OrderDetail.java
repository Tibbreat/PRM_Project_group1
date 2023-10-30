package com.example.order_food.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class OrderDetail {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int quantity;
    private int orderId;
    private int foodId;

    public OrderDetail() {
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
