package com.example.order_food.Config;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.order_food.Card.PopularFoodCard;
import com.example.order_food.MainApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PathDataForPreferences {
    private final static String mapInfor = "-";
    private final static String seperateInfor = ";";

    public static List<PopularFoodCard> getOrderCart(String userId) {
        List<PopularFoodCard> orderCart = new ArrayList<>();
        SharedPreferences preferences = MainApplication.getSharedPreferences();
        String orderCartString = preferences.getString(userId, "");
        if (orderCartString.isEmpty()) {
            return orderCart;
        }
        Log.d("image", "addnew Cart: "+ orderCartString+ "dddddd");
        PopularFoodCard orderCartEach;
        String[] object = orderCartString.split(seperateInfor);
        String[] subObject;
        for (String s : object) {
            orderCartEach = new PopularFoodCard();
            subObject = s.split(mapInfor);
            try {
                orderCartEach.setId(Integer.parseInt(subObject[0]));
                orderCartEach.setQuantity(Integer.parseInt(subObject[1]));
            } catch (Exception e) {
                Log.d("getOrderCart_error", Objects.requireNonNull(e.getMessage()) + ": in getOrderCart");
                return new ArrayList<>();
            }
            orderCart.add(orderCartEach);
        }

        return orderCart;
    }

    public static void addNewOrderCart(String userId, int foodId) {
        if (userId.isEmpty() || foodId==0) {
            return ;
        }
        Log.d("image", "addnew Cart: "+ userId);
        List<PopularFoodCard> orderCart = PathDataForPreferences.getOrderCart(userId);
        boolean findOrder = true;
        for(PopularFoodCard card: orderCart){
            if(card.getId()==foodId){
                card.setQuantity(card.getQuantity()+1);
                findOrder = false;
                break;
            }
        }
        Log.d("image", "addnew Cart: "+ orderCart);
        if(findOrder){
            orderCart.add(new PopularFoodCard(foodId, 1));
        }
        Log.d("image", "addnew Cart: "+ orderCart);
        PathDataForPreferences.updateOrderCart(orderCart, userId);
    }

    public static void updateOrderCart(List<PopularFoodCard> popularFoodCards, String userId) {
        SharedPreferences preferences = MainApplication.getSharedPreferences();

        Log.d("image", "addnew Cart: "+ popularFoodCards);
        StringBuilder stringBuilder = new StringBuilder();
        for(PopularFoodCard card: popularFoodCards){
            stringBuilder.append(card.getId()).append(mapInfor).append(card.getQuantity()).append(seperateInfor);
        }
        Log.d("image", "addnew Cart: "+ stringBuilder.toString());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(userId, stringBuilder.toString());
        editor.apply();
    }
}