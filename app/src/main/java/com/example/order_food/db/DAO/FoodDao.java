package com.example.order_food.db.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.order_food.db.entity.Food;

import java.util.List;

@Dao
public interface FoodDao {

    @Insert
    void insertFood(Food food);

    @Query("SELECT * FROM Food")
    List<Food> getAllFoods();

    @Delete
    void deleteFood(Food food);

}
