package com.example.order_food.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.example.order_food.Card.OrderCard;
import com.example.order_food.Card.PopularFoodCard;
import com.example.order_food.Config.StaticDefineForSystem;
import com.example.order_food.db.AppDatabase;
import com.example.order_food.db.entity.Food;
import com.example.order_food.db.entity.Order;
import com.example.order_food.db.entity.OrderDetail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

        long newOrderId;
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
    public List<OrderCard> getListOfOrder(int userID){
        List<OrderCard> ordersCards = new ArrayList<>();
        List<Order>  orders;
        try {
            orders= appDatabase.orderDao().getOrderByUserID(userID);
        } catch (Exception e) {
            e.printStackTrace();
            return ordersCards;
        }

        OrderCard orderCard;
        for(Order order: orders){
            orderCard = new OrderCard();
            orderCard.setId(order.getId());
            orderCard.setOrderDate(order.getOrderDate());
            orderCard.setStatus(order.getStatus());
            orderCard.setTotal(order.getTotal());
            orderCard.setShippedDate(order.getShippedDate());
            ordersCards.add(orderCard);
        }
        return ordersCards;
    }
    public boolean updateStatusOfStatus(int orderID, String status, Context context) {
        Order order;
        try {
            order= appDatabase.orderDao().getOrderByOrderID(orderID);
        } catch (Exception e) {
            Toast.makeText(context, "Fail to find order", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }
        order.setStatus(status);
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        if(status.equals(StaticDefineForSystem.ORDER_COMPLETE)){
            order.setShippedDate(String.valueOf(currentTime));
        }
        if(status.equals(StaticDefineForSystem.ORDER_CANCEL)){
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
            try {
                Date orderDate= formatter.parse(order.getOrderDate());
                long duration = currentTime.getTime()- Objects.requireNonNull(orderDate).getTime();
                if(duration>( 10 * 60 * 1000 )){
                    Toast.makeText(context, "You can only cancel order if the duration from the order moment to present is under 10 min", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        try {
            appDatabase.orderDao().updateOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Cancel order Fail, please try again", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public List<PopularFoodCard> getListOfOrderDetailByOrderID (int orderID){
        List<PopularFoodCard> popularFoodCards = new ArrayList<>();
        List<OrderDetail> orderDetails;
        try {
            orderDetails= appDatabase.orderDetailDao().getOrderDetailByOrderID(orderID);
        } catch (Exception e) {
            e.printStackTrace();
            return popularFoodCards;
        }
        PopularFoodCard popularFoodCard;
        Food food;
        for(OrderDetail orderDetail: orderDetails){
            popularFoodCard = new PopularFoodCard();
            try {
                food= appDatabase.foodDao().getFoodById(orderDetail.getFoodId());
            } catch (Exception e) {
                e.printStackTrace();
                return popularFoodCards;
            }
            popularFoodCard.setFoodName(food.getFoodName());
            popularFoodCard.setId(food.getId());
            popularFoodCard.setFoodPrice(food.getFoodPrice());
            popularFoodCard.setFoodImage(food.getImageUri());
            popularFoodCard.setQuantity(orderDetail.getQuantity());
            popularFoodCards.add(popularFoodCard);
        }
        return popularFoodCards;
    }

    public List<Order> getAllOrders () {
        return appDatabase.orderDao().getAllOrders();
    }

    public Order getOrderByOrderId(int id){return appDatabase.orderDao().getOrderByOrderID(id);}
}
