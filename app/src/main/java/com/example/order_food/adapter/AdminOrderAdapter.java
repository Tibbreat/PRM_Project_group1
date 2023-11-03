package com.example.order_food.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order_food.R;
import com.example.order_food.db.entity.Order;

import java.util.List;

public class AdminOrderAdapter extends RecyclerView.Adapter<AdminOrderAdapter.OrderViewHolder> {

    private List<Order> orderList;

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_order_manage, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        // Bind order data to the view
        holder.textViewOrderDate.setText(order.getOrderDate());
        holder.textViewShipDate.setText(order.getShippedDate());
        holder.textViewTotal.setText("$" + String.valueOf(order.getTotal()));
        holder.textViewStatus.setText(order.getStatus());
        int color = android.R.color.holo_green_light;
        if(order.getStatus().equals("COMPLETE")){
            color = R.color.textColor;
        } else if (order.getStatus().equals("CANCEL")){
            color = android.R.color.holo_red_dark;
        }
        holder.textViewStatus.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), color));
    }

    @Override
    public int getItemCount() {
        return orderList != null ? orderList.size() : 0;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView textViewOrderDate;
        TextView textViewShipDate;
        TextView textViewTotal;
        TextView textViewStatus;

        public OrderViewHolder(View itemView) {
            super(itemView);
            textViewOrderDate = itemView.findViewById(R.id.order_date_value);
            textViewShipDate = itemView.findViewById(R.id.ship_date_value);
            textViewTotal = itemView.findViewById(R.id.total_value);
            textViewStatus = itemView.findViewById(R.id.status_value);
        }
    }
}

