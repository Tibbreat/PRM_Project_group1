package com.example.order_food.adminFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.order_food.R;
import com.example.order_food.adapter.FoodListAdapter;
import com.example.order_food.db.entity.Food;
import com.example.order_food.service.FoodService;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewListFoodsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewListFoodsFragment extends Fragment {
    private ImageButton btnBack;

    public ViewListFoodsFragment() {
        // Required empty public constructor
    }

    public static ViewListFoodsFragment newInstance() {
        ViewListFoodsFragment fragment = new ViewListFoodsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initUI(View view) {
        btnBack = view.findViewById(R.id.buttonBackToFoodManagement);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_list_foods, container, false);
        initUI(view);
        btnBack.setOnClickListener(v -> replaceFragment(new FoodManagementFragment()));

        RecyclerView recyclerView = view.findViewById(R.id.view_list_food_manage);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        FoodListAdapter adapter = new FoodListAdapter(requireContext(), getAllFoods());
        recyclerView.setAdapter(adapter);


        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView2, fragment);
        transaction.commit();
    }

    private List<Food> getAllFoods() {
        // Retrieve all food items from the database using FoodService
        // You may want to run this on a background thread or use LiveData for better performance
        return FoodService.getInstance(requireContext()).getAllFoodItems();
    }
}