package com.example.order_food.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity

public class Order implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String orderDate;
    private float total;
    private String status;
    private int userID;
    private String shippedDate;

    public Order() {
    }

    public Order(int id, String orderDate, float total, String status, int userID, String shippedDate) {
        this.id = id;
        this.orderDate = orderDate;
        this.total = total;
        this.status = status;
        this.userID = userID;
        this.shippedDate = shippedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String  getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(String shippedDate) {
        this.shippedDate = shippedDate;
    }
}
