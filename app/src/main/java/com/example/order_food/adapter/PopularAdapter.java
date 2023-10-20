package com.example.order_food.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order_food.Card.PopularFoodCard;
import com.example.order_food.R;
import com.example.order_food.db.entity.Food;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularHolder> {


    List<Food> foods;
    private Context context;

    public PopularAdapter(List<Food> foods) {
        this.foods = foods;
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
        holder.food_p_name.setText(foods.get(position).getFoodName());
        holder.food_p_price.setText(foods.get(position).getFoodPrice() + "");
        holder.food_p_txt_remain.setText("remain: " + foods.get(position).getFoodQuantity());

        try {
            String imageFileName = foods.get(position).getImageUri(); // This should be the file path
            Bitmap bitmap = BitmapFactory.decodeFile(context.getFilesDir() + "/" + imageFileName);
            holder.food_p_view.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public static class PopularHolder extends RecyclerView.ViewHolder {

        ImageView food_p_view;
        TextView food_p_name;
        TextView food_p_price;

        TextView food_p_txt_remain;

        public PopularHolder(@NonNull View itemView) {
            super(itemView);
            food_p_view = itemView.findViewById(R.id.food_popular_image);
            food_p_name = itemView.findViewById(R.id.food_popular_name);
            food_p_price = itemView.findViewById(R.id.food_popular_price);
            food_p_txt_remain = itemView.findViewById(R.id.txt_remain);
        }
    }
}
