package com.example.order_food.service;

import android.content.Context;

import com.example.order_food.db.AppDatabase;
import com.example.order_food.db.entity.Food;
import com.example.order_food.db.entity.User;

import java.util.List;

public class UserService {
    private static UserService instance;
    private AppDatabase appDatabase;
    private UserService(Context context) {
        appDatabase = AppDatabase.getInstance(context);
    }
    public static UserService getInstance(Context context) {
        if (instance == null) {
            instance = new UserService(context);
        }
        return instance;
    }
    public boolean insert(User user) {
        try {
            appDatabase.userDao().insertUser(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public User getUser(String email,String password){
        User user = appDatabase.userDao().login(email,password);
        return user;
    }

    public int updateUserProfile(String email,String name,String address,String phone){
        return appDatabase.userDao().updateUserProfile(email,phone,address,name);
    }

    public int changePassword(String email,String newPassword){
        return appDatabase.userDao().changePassword(email, newPassword);
    }

    public User getUserReset(String email,String address){
        User user = appDatabase.userDao().reset(email,address);
        return user;
    }

    public boolean deleteUser(User user) {
        try {
            appDatabase.userDao().deleteUser(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> getAll() {
        return appDatabase.userDao().getAll();
    }

    public int getUserCount() {
        return appDatabase.userDao().getUserCount();
    }
    public User getUserByEmail(String email){
        User user = appDatabase.userDao().getUserByEmail(email);
        return user;
    }
    public User getUserById(int id){
        User user = appDatabase.userDao().getUserById(id);
        return user;
    }

    public int updateRole(int userId,String role){
        return appDatabase.userDao().updateRole(userId, role);
    }
}
