package com.example.order_food.adminFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.order_food.R;
import com.example.order_food.adapter.AdminOrderAdapter;
import com.example.order_food.db.entity.Order;
import com.example.order_food.service.OrderService;

import java.util.List;

public class OrderManagementFragment extends Fragment {

    private AdminOrderAdapter adminOrderAdapter;
    private RecyclerView recyclerView;

    public OrderManagementFragment() {
        // Required empty public constructor
    }

    public static OrderManagementFragment newInstance(String param1, String param2) {
        OrderManagementFragment fragment = new OrderManagementFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_management, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.view_list_order_manage);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize and set the adapter
        adminOrderAdapter = new AdminOrderAdapter();
        recyclerView.setAdapter(adminOrderAdapter);

        // Fetch order data from your database using OrderService
        List<Order> orders = OrderService.getInstance(requireContext()).getAllOrders();

        // Update the adapter with order data
        adminOrderAdapter.setOrderList(orders);

        return view;
    }
}