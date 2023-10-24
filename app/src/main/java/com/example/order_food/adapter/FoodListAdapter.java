package com.example.order_food.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order_food.R;
import com.example.order_food.db.entity.Food;
import com.example.order_food.service.FoodService;

import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodViewHolder> {
    private List<Food> foodList;
    private Context context;

    public FoodListAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.view_list_food_manage, parent, false);
        return new FoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);

        holder.foodNameTextView.setText("Food's name: " + food.getFoodName());
        holder.foodPriceTextView.setText("Price: $" + food.getFoodPrice());

        try {
            String imageFileName = food.getImageUri(); // This should be the file path
            Bitmap bitmap = BitmapFactory.decodeFile(context.getFilesDir() + "/" + imageFileName);
            holder.foodImageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.btnDelete.setOnClickListener(v -> {
            boolean isDeleted = FoodService.getInstance(context).deleteFood(food);
            if (isDeleted) {
                foodList.remove(position);
                notifyItemRemoved(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return foodList.size();
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView foodNameTextView;
        TextView foodPriceTextView;
        ImageView foodImageView;

        ImageButton btnDelete;

        FoodViewHolder(View itemView) {
            super(itemView);
            foodNameTextView = itemView.findViewById(R.id.food_name);
            foodPriceTextView = itemView.findViewById(R.id.food_price);
            foodImageView = itemView.findViewById(R.id.food_popular_image);
            btnDelete = itemView.findViewById(R.id.btn_delete);

        }
    }
}


