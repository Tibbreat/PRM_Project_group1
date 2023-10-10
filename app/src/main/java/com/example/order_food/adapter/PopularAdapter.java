package com.example.order_food.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order_food.Card.PopularFoodCard;
import com.example.order_food.R;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularHolder> {


    List<PopularFoodCard> popularFoods;

    public PopularAdapter(List<PopularFoodCard> foods) {
        popularFoods = foods;
    }

    @NonNull
    @Override
    public PopularAdapter.PopularHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.popular_item,parent,false);
        return new PopularHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.PopularHolder holder, int position) {
        holder.food_p_view.setImageResource(popularFoods.get(position).getFoodImage());
        holder.food_p_name.setText(popularFoods.get(position).getFoodName());
        holder.food_p_price.setText(popularFoods.get(position).getFoodPrice() + "");
    }

    @Override
    public int getItemCount() {
        return popularFoods.size();
    }

    public class PopularHolder extends RecyclerView.ViewHolder {

        ImageView food_p_view;
        TextView food_p_name;
        TextView food_p_price;

        public PopularHolder(@NonNull View itemView) {
            super(itemView);
            food_p_view = itemView.findViewById(R.id.food_popular_image);
            food_p_name = itemView.findViewById(R.id.food_popular_name);
            food_p_price = itemView.findViewById(R.id.food_popular_price);
        }
    }
}
