package com.example.order_food.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order_food.R;
import com.example.order_food.adminFragment.AdminManageOrderDetailFragment;
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
        holder.buttonViewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int orderId = order.getId();
                AdminManageOrderDetailFragment fragment = AdminManageOrderDetailFragment.newInstance(orderId);

                FragmentManager fragmentManager = ((FragmentActivity) holder.itemView.getContext()).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView2, fragment)
                        .commit();
            }
        });

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

        Button buttonViewDetail;

        public OrderViewHolder(View itemView) {
            super(itemView);
            textViewOrderDate = itemView.findViewById(R.id.order_date_value);
            textViewShipDate = itemView.findViewById(R.id.ship_date_value);
            textViewTotal = itemView.findViewById(R.id.total_value);
            textViewStatus = itemView.findViewById(R.id.status_value);
            buttonViewDetail = itemView.findViewById(R.id.button_view_detail);
        }
    }
}

