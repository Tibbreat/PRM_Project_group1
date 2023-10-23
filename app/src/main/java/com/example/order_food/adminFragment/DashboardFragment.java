package com.example.order_food.adminFragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.order_food.Fragment.FoodDetailFragment;
import com.example.order_food.R;
import com.example.order_food.db.entity.Food;
import com.example.order_food.service.FoodService;
import com.example.order_food.service.OrderService;
import com.example.order_food.service.UserService;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    private Button btnReload;
    private TextView number_user;
    private TextView number_order;
    private TextView number_food;

    public DashboardFragment() {
        // Required empty public constructor
    }
    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        number_user = view.findViewById(R.id.txt_number_user);
        number_order = view.findViewById(R.id.txt_number_order);
        number_food = view.findViewById(R.id.txt_number_food);
        btnReload = view.findViewById(R.id.btn_reloadDashboard);

        UserService userService = UserService.getInstance(requireContext());
        OrderService orderService = OrderService.getInstance(requireContext());
        FoodService foodService = FoodService.getInstance(requireContext());

        loadNumbers(userService, orderService, foodService);

        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNumbers(userService, orderService, foodService);
            }
        });

        return view;
    }

    private void loadNumbers(UserService userService, OrderService orderService, FoodService foodService) {
        int numberUser = userService.getUserCount();
        int numberOrder = orderService.getOrderCount();
        int numberFood = foodService.getFoodCount();

        number_user.setText(String.valueOf(numberUser));
        number_order.setText(String.valueOf(numberOrder));
        number_food.setText(String.valueOf(numberFood));
    }


}