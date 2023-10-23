package com.example.order_food.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.order_food.util.LocalDateConverter;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@TypeConverters(LocalDateConverter.class)
public class Order implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private LocalDate orderDate;
    private float total;
    private String status;
    private int userID;
    private LocalDate shippedDate;

    public Order() {
    }

    public Order(int id, LocalDate orderDate, float total, String status, int userID, LocalDate shippedDate) {
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

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
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

    public LocalDate getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(LocalDate shippedDate) {
        this.shippedDate = shippedDate;
    }
}
