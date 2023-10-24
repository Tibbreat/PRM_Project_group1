package com.example.order_food.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
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
    private static final String TAG = "MyActivity";
    private String userID;
    private IclickItemFood ilickItemFood;

    public interface IclickItemFood {
        void getFoodDetail(Food food, String userID);
    }

    List<Food> foods;
    private Context context;

    public PopularAdapter(Context context, String userID, List<Food> foods, IclickItemFood iclickItemFood) {
        this.context = context;
        this.foods = foods;
        this.ilickItemFood = iclickItemFood;
        this.userID = userID;
    }

    @NonNull
    @Override
    public PopularAdapter.PopularHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.popular_item, parent, false);
        return new PopularHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.PopularHolder holder, int position) {

        final Food food = foods.get(position);
        holder.food_p_name.setText(food.getFoodName());
        holder.food_p_price.setText(food.getFoodPrice() + "");
        try {
            String imageFileName = foods.get(position).getImageUri(); // This should be the file path
            Bitmap bitmap = BitmapFactory.decodeFile(context.getFilesDir() + "/" + imageFileName);
            holder.food_p_view.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.btn_viewDetail.setOnClickListener(view -> ilickItemFood.getFoodDetail(food, userID));
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

        TextView btn_viewDetail;

        public PopularHolder(@NonNull View itemView) {
            super(itemView);
            food_p_view = itemView.findViewById(R.id.food_popular_image);
            food_p_name = itemView.findViewById(R.id.food_popular_name);
            food_p_price = itemView.findViewById(R.id.food_popular_price);
            btn_viewDetail = itemView.findViewById(R.id.add_to_cart_popular);
        }
    }
}
