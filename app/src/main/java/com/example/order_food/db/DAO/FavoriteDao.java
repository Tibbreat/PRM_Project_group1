package com.example.order_food.db.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.order_food.db.entity.FavoriteFood;

@Dao
public interface FavoriteDao {
    @Insert
    void insertFood(FavoriteFood favoriteFood);
    @Query("SELECT * FROM FavoriteFood WHERE id= :id")
    FavoriteFood getFavFoodById(int id);
    @Query("SELECT * FROM FavoriteFood WHERE userID = :uid AND productID = :pid")
    FavoriteFood getFavFood(int uid, int pid);
}
