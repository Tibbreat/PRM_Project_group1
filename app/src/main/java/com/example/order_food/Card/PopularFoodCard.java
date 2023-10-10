package com.example.order_food.Card;

import android.net.Uri;

public class PopularFoodCard {
    private int id;
    private int foodImage;
    private String foodName;
    private float foodPrice;

    public PopularFoodCard(int id, int foodImage, String foodName, float foodPrice) {
        this.id = id;
        this.foodImage = foodImage;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
    }

    public PopularFoodCard() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(int foodImage) {
        this.foodImage = foodImage;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public float getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(float foodPrice) {
        this.foodPrice = foodPrice;
    }
}
