package com.example.order_food.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class FavoriteFood {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int productID;
    private int userID;

    public FavoriteFood() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public FavoriteFood(int productID, int userID) {
        this.productID = productID;
        this.userID = userID;
    }

    public FavoriteFood(int id, int productID, int userID) {
        this.id = id;
        this.productID = productID;
        this.userID = userID;
    }
}
