package com.example.order_food.db.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.order_food.db.entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE email LIKE :email AND " +
            "password LIKE :password LIMIT 1")
    User login(String email, String password);

    @Query("UPDATE user set name=:name,phone=:phone,address=:address where email=:email")
    int updateUserProfile(String email,String phone, String address,String name);

    @Query("UPDATE user set password=:newPassword where email=:email")
    int changePassword(String email,String newPassword);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM user WHERE email = :email AND " +
            "address = :address LIMIT 1")
    User reset(String email, String address);

    @Delete
    void deleteUser(User user);

    @Query("SELECT COUNT(*) FROM user")
    int getUserCount();
    @Query("SELECT * FROM user WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    @Query("SELECT * FROM user WHERE id = :id LIMIT 1")
    User getUserById(int id);

    @Query("UPDATE user set role=:role where id=:userId")
    int updateRole(int userId,String role);
}