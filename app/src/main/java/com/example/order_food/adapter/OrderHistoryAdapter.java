package com.example.order_food.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order_food.Card.OrderCard;
import com.example.order_food.Card.PopularFoodCard;
import com.example.order_food.Config.StaticDefineForSystem;
import com.example.order_food.R;
import com.example.order_food.service.OrderService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryHolder> {
    List<OrderCard> OrderHistory;
    Context context;

    public OrderHistoryAdapter(List<OrderCard> OrderHistory, Context context) {
        this.OrderHistory = OrderHistory;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_item, parent, false);

        return new OrderHistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryHolder holder, int position) {
        if (!OrderHistory.isEmpty()) {
            int orderID = OrderHistory.get(position).getId();
            holder.order_c_order_id.setText(String.valueOf(orderID));
            holder.order_c_order_date.setText(OrderHistory.get(position).getOrderDate());
            holder.order_c_order_shipped.setText(OrderHistory.get(position).getShippedDate());
            holder.order_c_order_total.setText(String.valueOf(OrderHistory.get(position).getTotal()));
            if (OrderHistory.get(position).getStatus().equals(StaticDefineForSystem.ORDER_PENDING)) {
                holder.order_c_order_complete.setVisibility(View.GONE);
                holder.order_c_order_cancel.setVisibility(View.GONE);
            }
            if (OrderHistory.get(position).getStatus().equals(StaticDefineForSystem.ORDER_CANCEL)) {
                holder.order_c_order_change_status_cancel.setVisibility(View.GONE);
                holder.order_c_order_change_status_complete.setVisibility(View.GONE);
                holder.order_c_order_complete.setVisibility(View.GONE);
                holder.order_c_order_pending.setVisibility(View.GONE);

                holder.order_c_order_cancel.setVisibility(View.VISIBLE);
            }
            if (OrderHistory.get(position).getStatus().equals(StaticDefineForSystem.ORDER_COMPLETE)) {
                holder.order_c_order_change_status_cancel.setVisibility(View.GONE);
                holder.order_c_order_change_status_complete.setVisibility(View.GONE);
                holder.order_c_order_cancel.setVisibility(View.GONE);
                holder.order_c_order_pending.setVisibility(View.GONE);

                holder.order_c_order_complete.setVisibility(View.VISIBLE);
            }
            List<PopularFoodCard> popularFoodCards = OrderService.getInstance(context).getListOfOrderDetailByOrderID(orderID);
            if (holder.recyclerView != null) {
                holder.recyclerView.setAdapter(new OrderDetailAdapter(popularFoodCards, orderID));
            }
        }
    }

    @Override
    public int getItemCount() {
        return OrderHistory.size();
    }

    public class OrderHistoryHolder extends RecyclerView.ViewHolder {
        TextView order_c_order_date;
        TextView order_c_order_shipped;
        TextView order_c_order_total;
        TextView order_c_order_pending;
        TextView order_c_order_cancel;
        TextView order_c_order_complete;
        TextView order_c_order_id;
        RecyclerView recyclerView;

        TextView order_c_order_change_status_cancel;
        TextView order_c_order_change_status_complete;

        @SuppressLint("NotifyDataSetChanged")
        public OrderHistoryHolder(@NonNull View itemView) {
            super(itemView);
            order_c_order_id = itemView.findViewById(R.id.food_order_history_id);
            order_c_order_shipped = itemView.findViewById(R.id.food_order_history_shipped_date_value);
            order_c_order_total = itemView.findViewById(R.id.food_order_history_total_value);
            order_c_order_date = itemView.findViewById(R.id.food_order_history_order_date_value);
            order_c_order_pending = itemView.findViewById(R.id.food_order_status_PENDING);
            order_c_order_cancel = itemView.findViewById(R.id.food_order_status_Cancel);
            order_c_order_complete = itemView.findViewById(R.id.food_order_status_COMPLETE);
            order_c_order_change_status_cancel = itemView.findViewById(R.id.food_order_history_cancel);
            order_c_order_change_status_complete = itemView.findViewById(R.id.food_order_history_complete);

            recyclerView = itemView.findViewById(R.id.rec_order_history_listobject);
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));

            order_c_order_change_status_cancel.setOnClickListener(view -> {
                OrderCard order = OrderHistory.get(getAdapterPosition());
                boolean checkCreate = OrderService.getInstance(context).updateStatusOfStatus(order.getId(), StaticDefineForSystem.ORDER_CANCEL, context);
                if (!checkCreate) {
                    return;
                }
                OrderHistory.get(getAdapterPosition()).setStatus(StaticDefineForSystem.ORDER_CANCEL);
                Toast.makeText(context, "Cancel order successfully", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            });
            order_c_order_change_status_complete.setOnClickListener(view -> {
                OrderCard order = OrderHistory.get(getAdapterPosition());
                boolean checkCreate = OrderService.getInstance(context).updateStatusOfStatus(order.getId(), StaticDefineForSystem.ORDER_COMPLETE, context);
                if (!checkCreate) {
                    Toast.makeText(context, "Complete order Fail, please try again", Toast.LENGTH_SHORT).show();
                    return;
                }
                Calendar calendar = Calendar.getInstance();
                Date currentTime = calendar.getTime();
                OrderHistory.get(getAdapterPosition()).setShippedDate(String.valueOf(currentTime));
                OrderHistory.get(getAdapterPosition()).setStatus(StaticDefineForSystem.ORDER_COMPLETE);
                Toast.makeText(context, "Complete order successfully", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            });
        }
    }
}
