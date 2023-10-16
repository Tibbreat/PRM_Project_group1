package com.example.order_food.adapter;

import android.annotation.SuppressLint;
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

import java.util.List;
import java.util.Objects;

public class OrderCartAdapter extends RecyclerView.Adapter<OrderCartAdapter.OrderCartHolder> {
    public interface OnItemChangeListener {
        void onListChange(String variable);
    }
    List<PopularFoodCard> orderCartCards;
    private OnItemChangeListener listener;

    public OrderCartAdapter(List<PopularFoodCard> orderCart){
        orderCartCards = orderCart;
    }

    @NonNull
    @Override
    public OrderCartAdapter.OrderCartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_cart_item, parent, false);
        return new OrderCartHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderCartAdapter.OrderCartHolder holder, int position) {
        float total = 0;
        if(!orderCartCards.isEmpty()){
            holder.food_c_view.setImageResource(orderCartCards.get(position).getFoodImage());
            holder.food_c_name.setText(orderCartCards.get(position).getFoodName());
            holder.food_c_price.setText(orderCartCards.get(position).getFoodPrice() + "");
            holder.food_c_quantity.setText(orderCartCards.get(position).getQuantity()+"");

            for(PopularFoodCard orderCartCard: orderCartCards){
                total = total +(orderCartCard.getFoodPrice() * orderCartCard.getQuantity());
            }
        }
        listener.onListChange(String.valueOf(total));
    }

    @Override
    public int getItemCount() {
        return orderCartCards.size();
    }

    public class OrderCartHolder extends RecyclerView.ViewHolder {
        ImageView food_c_view;
        TextView food_c_name;
        TextView food_c_price;
        TextView food_c_quantity;
        @SuppressLint("NotifyDataSetChanged")
        public OrderCartHolder(@NonNull View itemView) {
            super(itemView);
            food_c_view = itemView.findViewById(R.id.food_order_cart_image);
            food_c_name = itemView.findViewById(R.id.food_order_cart_name);
            food_c_price = itemView.findViewById(R.id.food_order_cart_price);
            food_c_quantity = itemView.findViewById(R.id.food_order_cart_quantity);

            (itemView.findViewById(R.id.add_quantity)).setOnClickListener(view ->  {
                try {
                    int quantity = Integer.parseInt(food_c_quantity.getText().toString());
                    orderCartCards.get(getAdapterPosition()).setQuantity(quantity+1);
                    notifyDataSetChanged();
                }catch (Exception e){
                    Log.d("quantity_change_error", Objects.requireNonNull(e.getMessage()));
                }
            });
            (itemView.findViewById(R.id.minus_quantity)).setOnClickListener(view ->  {
                try {
                    int quantity = Integer.parseInt(food_c_quantity.getText().toString());
                    if(quantity>1){
                        orderCartCards.get(getAdapterPosition()).setQuantity(quantity-1);
                        notifyDataSetChanged();
                    }
                }catch (Exception e){
                    Log.d("quantity_change_error", Objects.requireNonNull(e.getMessage()));
                }
            });
            (itemView.findViewById(R.id.remove_from_cart)).setOnClickListener(view -> {
                orderCartCards.remove(getAdapterPosition());
                if(orderCartCards.isEmpty()){
                    listener.onListChange(String.valueOf(0));
                }
                notifyDataSetChanged();
            });
        }
    }
    public void setOnItemChangeListener(OnItemChangeListener listener) {
        this.listener = listener;
    }
}
