package com.example.order_food.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order_food.Card.PopularFoodCard;
import com.example.order_food.R;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailHolder> {
    List<PopularFoodCard> orderDetailCards;
    int orderID;
    public OrderDetailAdapter(List<PopularFoodCard> orderDetailCards, int orderID){
        this.orderDetailCards = orderDetailCards;
        this.orderID = orderID;
    }
    @NonNull
    @Override
    public OrderDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_item, parent, false);
        return new OrderDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailHolder holder, int position) {
        holder.order_detail_name.setText(orderDetailCards.get(position).getFoodName());
        holder.order_detail_quantity.setText(String.valueOf(orderDetailCards.get(position).getQuantity()));
    }

    @Override
    public int getItemCount() {
        return orderDetailCards.size();
    }

    public class OrderDetailHolder extends RecyclerView.ViewHolder {
        TextView order_detail_name;
        TextView order_detail_quantity;
        public OrderDetailHolder(@NonNull View itemView) {
            super(itemView);
            order_detail_name = itemView.findViewById(R.id.order_detail_name);
            order_detail_quantity = itemView.findViewById(R.id.order_detail_quantity_value);
        }
    }
}
