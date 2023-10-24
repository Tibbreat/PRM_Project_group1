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
    @Query("SELECT * FROM Food WHERE id= :id")
    Food getFoodById(int id);

    @Delete
    void deleteFood(Food food);

    @Query("SELECT * FROM Food order by id desc LIMIT 10")
    List<Food> getAllNewFoods();

    @Query("SELECT * FROM Food order by foodPrice desc LIMIT 10")
    List<Food> getAllPopularFoods();
    @Query("SELECT * FROM Food WHERE foodName LIKE '%' || :searchValue || '%'")
    List<Food> getAllFoodsBySearchValue(String searchValue);

    @Query("SELECT COUNT(*) FROM Food")
    int getFoodCount();

}
