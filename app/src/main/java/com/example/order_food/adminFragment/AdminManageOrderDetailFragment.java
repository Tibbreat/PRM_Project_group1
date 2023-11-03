package com.example.order_food.adminFragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.order_food.Card.PopularFoodCard;
import com.example.order_food.R;
import com.example.order_food.adapter.OrderDetailAdapter;
import com.example.order_food.db.entity.Order;
import com.example.order_food.db.entity.User;
import com.example.order_food.service.OrderService;
import com.example.order_food.service.UserService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdminManageOrderDetailFragment extends Fragment {

    private TextView orderDateTextView;
    private TextView shipDateTextView;
    private TextView totalTextView;
    private TextView statusTextView;
    private TextView addressTextView;
    private TextView phoneNumberTextView;
    private TextView customerNameTextView;
    private ImageButton btnBack;

    private AppCompatButton btnCancel, btnComplete;

    private RecyclerView orderDetailRecyclerView;
    private OrderDetailAdapter orderDetailAdapter;

    public AdminManageOrderDetailFragment() {
        // Required empty public constructor
    }

    public static AdminManageOrderDetailFragment newInstance(int orderId) {
        AdminManageOrderDetailFragment fragment = new AdminManageOrderDetailFragment();
        Bundle args = new Bundle();
        args.putInt("orderId", orderId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int orderId = getArguments().getInt("orderId", -1);
        View view = inflater.inflate(R.layout.fragment_admin_manage_order_detail, container, false);
        initUI(view);

        // Check if orderId is valid
        if (orderId != -1) {
            Order order = OrderService.getInstance(requireContext()).getOrderByOrderId(orderId);
            User user = UserService.getInstance(requireContext()).getUserById(order.getUserID());

            // Set order details
            orderDateTextView.setText("Order Date: " + order.getOrderDate());
            shipDateTextView.setText("Ship Date: " + order.getShippedDate());
            totalTextView.setText("Total: $" + String.valueOf(order.getTotal()));
            statusTextView.setText("Status: " + order.getStatus());
            addressTextView.setText("Address: " + user.getAddress());
            phoneNumberTextView.setText("Phone number: " + user.getPhone());
            customerNameTextView.setText("Customer name: " + user.getName());

            // Initialize and set up the order detail RecyclerView
            orderDetailRecyclerView = view.findViewById(R.id.order_detail_item);
            LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
            orderDetailRecyclerView.setLayoutManager(layoutManager);

            // Replace 'orderDetailList' with your list of PopularFoodCard objects
            List<PopularFoodCard> orderDetailList = OrderService.getInstance(requireContext()).getListOfOrderDetailByOrderID(orderId);
            orderDetailAdapter = new OrderDetailAdapter(orderDetailList, orderId);
            orderDetailRecyclerView.setAdapter(orderDetailAdapter);

            // Hide buttons if order status is not 'PENDING'
            if (!order.getStatus().equals("PENDING")) {
                btnCancel.setVisibility(View.INVISIBLE);
                btnComplete.setVisibility(View.INVISIBLE);
            }
        }

        btnBack.setOnClickListener(v -> replaceFragment(new OrderManagementFragment()));
        btnCancel.setOnClickListener(v -> handleOrderCancellation(orderId));
        btnComplete.setOnClickListener(v -> handleOrderCompletion(orderId));

        return view;
    }

    private void initUI(View view) {
        orderDateTextView = view.findViewById(R.id.order_date);
        shipDateTextView = view.findViewById(R.id.ship_date);
        totalTextView = view.findViewById(R.id.total);
        statusTextView = view.findViewById(R.id.status);
        addressTextView = view.findViewById(R.id.address);
        phoneNumberTextView = view.findViewById(R.id.phone_number);
        customerNameTextView = view.findViewById(R.id.customer_name);
        btnBack = view.findViewById(R.id.button_back);
        btnCancel = view.findViewById(R.id.button_cancel);
        btnComplete = view.findViewById(R.id.button_complete);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView2, fragment);
        transaction.commit();
    }

    private void handleOrderCancellation(int orderId){
        if (statusTextView.getText().toString().equals("Status: PENDING")) {
            if (orderId != -1) {
                boolean checkCancel = OrderService.getInstance(requireContext()).updateStatusOfStatus(orderId, "CANCEL", requireContext());
                if (checkCancel) {
                    statusTextView.setText("Status: CANCEL");
                    btnCancel.setVisibility(View.INVISIBLE);
                    btnComplete.setVisibility(View.INVISIBLE);
                    Toast.makeText(requireContext(), "Order canceled successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Failed to cancel order. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void handleOrderCompletion(int orderId) {
        if (statusTextView.getText().toString().equals("Status: PENDING")) {
            if (orderId != -1) {
                boolean checkComplete = OrderService.getInstance(requireContext()).updateStatusOfStatus(orderId, "COMPLETE", requireContext());
                if (checkComplete) {
                    statusTextView.setText("Status: COMPLETE");
                    Calendar calendar = Calendar.getInstance();
                    Date currentTime = calendar.getTime();
                    shipDateTextView.setText("Ship Date: " + currentTime);
                    btnCancel.setVisibility(View.INVISIBLE);
                    btnComplete.setVisibility(View.INVISIBLE);
                    Toast.makeText(requireContext(), "Order completed successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Failed to complete order. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
