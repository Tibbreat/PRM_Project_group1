package com.example.order_food.adapter;

import android.annotation.SuppressLint;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order_food.Card.PopularFoodCard;
import com.example.order_food.Config.PathDataForPreferences;
import com.example.order_food.Fragment.CartFragment;
import com.example.order_food.R;

import java.util.List;

public class FavoriteCartAdapter extends RecyclerView.Adapter<FavoriteCartAdapter.FavoriteCartHolder> {

    List<PopularFoodCard> favoriteFoodCart;
    FragmentManager fragmentManager;
    String userId;
    Context context;
    public FavoriteCartAdapter(List<PopularFoodCard> favoriteFoodCart, String id, Context context, FragmentManager fragmentManager){
        Log.d("favourite_fragment", "getInto FragmentCartAdapter ");
        this.favoriteFoodCart = favoriteFoodCart;
        this.userId = id;
        this.context = context;
        this.fragmentManager = fragmentManager;
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
            try {
                String imageFileName = favoriteFoodCart.get(position).getFoodImage(); // This should be the file path
                Bitmap bitmap = BitmapFactory.decodeFile(context.getFilesDir() + "/" + imageFileName);
                holder.food_c_view.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("image", "onCreateView: " + e);
            }
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
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragmentContainerView, CartFragment.newInstance());
                transaction.commit();
            });
        }
    }
}
