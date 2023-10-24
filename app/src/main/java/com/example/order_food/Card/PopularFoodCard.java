package com.example.order_food.Card;

public class PopularFoodCard {
    private int id;
    private String foodImage;
    private String foodName;
    private float foodPrice;
    private int quantity;

    public PopularFoodCard(int id, String foodImage, String foodName, float foodPrice) {
        this.id = id;
        this.foodImage = foodImage;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
    }

    public PopularFoodCard(int id, String foodImage, String foodName, float foodPrice, int quantity) {
        this.id = id;
        this.foodImage = foodImage;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.quantity = quantity;
    }
    public PopularFoodCard(int id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public PopularFoodCard() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
