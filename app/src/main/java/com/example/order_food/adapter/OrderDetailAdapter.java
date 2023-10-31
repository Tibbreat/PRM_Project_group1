package com.example.order_food.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order_food.Card.PopularFoodCard;
import com.example.order_food.R;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailHolder> {
    List<PopularFoodCard> orderDetailCards;
    @NonNull
    @Override
    public OrderDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_item, parent, false);
        return new OrderDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return orderDetailCards.size();
    }

    public class OrderDetailHolder extends RecyclerView.ViewHolder {

        public OrderDetailHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
