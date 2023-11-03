package com.example.order_food.Card;

import com.example.order_food.db.entity.Food;
import com.example.order_food.db.entity.OrderDetail;
import com.example.order_food.db.entity.User;

import java.util.List;

public class OrderDetailCard {
    private String orderDate;
    private float total;
    private String status;
    private User user;
    private String shippedDate;

    private List<Food> foods;

    public OrderDetailCard() {

    }

    public String getOrderDate() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(String shippedDate) {
        this.shippedDate = shippedDate;
    }


}
