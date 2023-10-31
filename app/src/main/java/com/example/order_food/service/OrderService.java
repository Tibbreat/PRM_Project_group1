package com.example.order_food.service;

import android.content.Context;

import com.example.order_food.Card.PopularFoodCard;
import com.example.order_food.Config.StaticDefineForSystem;
import com.example.order_food.db.AppDatabase;
import com.example.order_food.db.entity.Order;
import com.example.order_food.db.entity.OrderDetail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderService {
    private static OrderService instance;
    private final AppDatabase appDatabase;
    private OrderService(Context context) {
        appDatabase = AppDatabase.getInstance(context);
    }
    public static OrderService getInstance(Context context) {
        if (instance == null) {
            instance = new OrderService(context);
        }
        return instance;
    }

    public int getOrderCount() {
        return appDatabase.orderDao().getOrderCount();
    }

    public boolean insertOrder(float total, String userID, List<PopularFoodCard> popularFoodCards) {
        Order order = new Order();
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();

        order.setOrderDate(String.valueOf(currentTime));
        order.setTotal(total);
        order.setStatus(StaticDefineForSystem.ORDER_PENDING);

        long newOrderId=-1;
        try {
            order.setUserID(Integer.parseInt(userID));
            newOrderId = appDatabase.orderDao().insert(order);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        OrderDetail orderDetail;
        List<OrderDetail> orderDetails = new ArrayList<>();
        for(PopularFoodCard card: popularFoodCards){
            orderDetail = new OrderDetail();
            orderDetail.setOrderId((int) newOrderId);
            orderDetail.setFoodId(card.getId());
            orderDetail.setQuantity(card.getQuantity());
            orderDetails.add(orderDetail);
        }
        try {
            appDatabase.orderDetailDao().insertAll(orderDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
