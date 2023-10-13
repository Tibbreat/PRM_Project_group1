package com.example.order_food.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order_food.Card.OrderCartCard;
import com.example.order_food.R;

import java.util.List;
import java.util.logging.Logger;

public class OrderCartAdapter extends RecyclerView.Adapter<OrderCartAdapter.OrderCartHolder> {
    List<OrderCartCard> orderCartCards;

    public OrderCartAdapter(List<OrderCartCard> orderCart){
        orderCartCards = orderCart;
    }

    @NonNull
    @Override
    public OrderCartAdapter.OrderCartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_cart_item, parent, false);
        return new OrderCartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderCartAdapter.OrderCartHolder holder, int position) {
        holder.food_c_view.setImageResource(orderCartCards.get(position).getFoodImage());
        holder.food_c_name.setText(orderCartCards.get(position).getFoodName());
        holder.food_c_price.setText(orderCartCards.get(position).getFoodPrice() + "");
        holder.food_c_quantity.setText(orderCartCards.get(position).getQuantity()+"");

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
        public OrderCartHolder(@NonNull View itemView) {
            super(itemView);
            food_c_view = itemView.findViewById(R.id.food_order_cart_image);
            food_c_name = itemView.findViewById(R.id.food_order_cart_name);
            food_c_price = itemView.findViewById(R.id.food_order_cart_price);
            food_c_quantity = itemView.findViewById(R.id.food_order_cart_quantity);

            ((TextView)itemView.findViewById(R.id.add_quantity)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        int quantity = Integer.parseInt(food_c_quantity.getText().toString());
                        orderCartCards.get(getAdapterPosition()).setQuantity(quantity+1);
                        notifyDataSetChanged();
                    }catch (Exception e){
                        Log.d("quantity_change_error",e.getMessage());
                    }
                }
            });
            ((TextView)itemView.findViewById(R.id.minus_quantity)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        int quantity = Integer.parseInt(food_c_quantity.getText().toString());
                        if(quantity>1){
                            orderCartCards.get(getAdapterPosition()).setQuantity(quantity-1);
                            notifyDataSetChanged();
                        }
                    }catch (Exception e){
                        Log.d("quantity_change_error",e.getMessage());
                    }
                }
            });
            ((TextView)itemView.findViewById(R.id.remove_from_cart)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orderCartCards.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }

    }
}
