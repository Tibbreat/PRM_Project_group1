package com.example.order_food.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order_food.Card.OrderCard;
import com.example.order_food.Card.PopularFoodCard;
import com.example.order_food.Config.StaticDefineForSystem;
import com.example.order_food.R;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryHolder> {
    List<OrderCard> OrderHistory;
    Context context;
    public OrderHistoryAdapter(List<OrderCard> OrderHistory, Context context){
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
        if(!OrderHistory.isEmpty()){
            holder.order_c_order_id.setText(OrderHistory.get(position).getId()+"");
            holder.order_c_order_date.setText(OrderHistory.get(position).getOrderDate());
            holder.order_c_order_shipped.setText(OrderHistory.get(position).getShippedDate());
            holder.order_c_order_total.setText(String.valueOf(OrderHistory.get(position).getTotal()));
            if(OrderHistory.get(position).getStatus().equals(StaticDefineForSystem.ORDER_PENDING)){

            }
            if(OrderHistory.get(position).getStatus().equals(StaticDefineForSystem.ORDER_CANCEL)){

            }
            if(OrderHistory.get(position).getStatus().equals(StaticDefineForSystem.ORDER_COMPLETE)){

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

        Button order_c_order_change_status;
        public OrderHistoryHolder(@NonNull View itemView) {
            super(itemView);
            order_c_order_id = itemView.findViewById(R.id.food_order_history_id);
            order_c_order_shipped = itemView.findViewById(R.id.food_order_history_shipped_date_value);
            order_c_order_total = itemView.findViewById(R.id.food_order_history_total_value);
            order_c_order_date = itemView.findViewById(R.id.food_order_history_order_date_value);
            order_c_order_pending = itemView.findViewById(R.id.food_order_status_PENDING);
            order_c_order_cancel = itemView.findViewById(R.id.food_order_status_Cancel);
            order_c_order_complete = itemView.findViewById(R.id.food_order_status_COMPLETE);
            order_c_order_change_status = itemView.findViewById(R.id.food_order_history_cancel);
            recyclerView = itemView.findViewById(R.id.rec_order_history);
        }
    }
}
