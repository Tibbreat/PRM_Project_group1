package com.example.order_food.adapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order_food.Card.PopularFoodCard;
import com.example.order_food.Config.PathDataForPreferences;
import com.example.order_food.Config.StaticDefineForSystem;
import com.example.order_food.Fragment.CartFragment;
import com.example.order_food.Fragment.FavoriteFragment;
import com.example.order_food.R;

import java.util.List;
import java.util.Objects;

public class FavoriteCartAdapter extends RecyclerView.Adapter<FavoriteCartAdapter.FavoriteCartHolder> {

    List<PopularFoodCard> favoriteFoodCart;
    String userId;
    public FavoriteCartAdapter(List<PopularFoodCard> favoriteCart, String id){
        favoriteFoodCart = favoriteCart;
        this.userId = id;
    }

    @NonNull
    @Override
    public FavoriteCartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_item, parent, false);
        return new FavoriteCartHolder(view);
    }

    @SuppressLint({"SetTextI18n", "ResourceType"})
    @Override
    public void onBindViewHolder(@NonNull FavoriteCartHolder holder, int position) {
        if(!favoriteFoodCart.isEmpty()){
            holder.food_c_id.setText(favoriteFoodCart.get(position).getId()+"");
            holder.food_c_view.setImageResource(1);
            holder.food_c_name.setText(favoriteFoodCart.get(position).getFoodName());
            holder.food_c_price.setText(favoriteFoodCart.get(position).getFoodPrice() + "");
        }
    }

    @Override
    public int getItemCount() {
        return favoriteFoodCart.size();
    }
    public class FavoriteCartHolder extends RecyclerView.ViewHolder {
        TextView food_c_id;
        ImageView food_c_view;
        TextView food_c_name;
        TextView food_c_price;
        @SuppressLint("NotifyDataSetChanged")
        public FavoriteCartHolder(@NonNull View itemView) {
            super(itemView);
            food_c_id = itemView.findViewById(R.id.food_favorite_id);
            food_c_view = itemView.findViewById(R.id.food_favorite_image);
            food_c_name = itemView.findViewById(R.id.food_favorite_name);
            food_c_price = itemView.findViewById(R.id.food_favorite_price);
            (itemView.findViewById(R.id.btn_favorite_icons)).setOnClickListener(view -> {
                favoriteFoodCart.remove(getAdapterPosition());
                notifyDataSetChanged();
            });
            (itemView.findViewById(R.id.add_to_cart_favorite)).setOnClickListener(view -> {
                int foodId = favoriteFoodCart.get(getAdapterPosition()).getId();
                PathDataForPreferences.addNewOrderCart(userId, foodId);
            });
        }
    }
}
