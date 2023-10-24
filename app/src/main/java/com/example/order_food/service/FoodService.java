package com.example.order_food.service;

import android.content.Context;
import android.util.Log;

import com.example.order_food.Card.PopularFoodCard;
import com.example.order_food.db.AppDatabase;
import com.example.order_food.db.entity.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FoodService {
    private static FoodService instance;
    private AppDatabase appDatabase;

    private FoodService(Context context) {
        appDatabase = AppDatabase.getInstance(context);
    }

    public static FoodService getInstance(Context context) {
        if (instance == null) {
            instance = new FoodService(context);
        }
        return instance;
    }

    public boolean insert(Food food) {
        try {
            appDatabase.foodDao().insertFood(food);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteFood(Food food) {
        try {
            appDatabase.foodDao().deleteFood(food);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Food> getAllFoodItems() {
        return appDatabase.foodDao().getAllFoods();
    }

    public List<Food> getAllNewFoods() {return appDatabase.foodDao().getAllNewFoods();}
    public List<Food> getAllPopularFoods() {return appDatabase.foodDao().getAllPopularFoods();}

    public List<Food> getSearchFoods(String searchValue){
        return appDatabase.foodDao().getAllFoodsBySearchValue(searchValue);
    }

    public int getFoodCount() {
        return appDatabase.foodDao().getFoodCount();
    }
    public List<PopularFoodCard>  getFoodItemsByListOfID(List<PopularFoodCard> ids){
        Food food;
        for(PopularFoodCard id: ids){
            try {
                food = appDatabase.foodDao().getFoodById(id.getId());
            } catch (Exception e) {
                Log.d("getFoodBuId_fromDB", Objects.requireNonNull(e.getMessage()));
                return new ArrayList<>();
            }
            id.setFoodName(food.getFoodName());
            id.setFoodImage(food.getImageUri());
            try {
                id.setFoodPrice(Float.parseFloat(food.getFoodPrice()));
            } catch (Exception e) {
                Log.d("getFoodBuId_fromDB", Objects.requireNonNull(e.getMessage()));
                return new ArrayList<>();
            }
        }
        return ids;
    }
}

