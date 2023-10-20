package com.example.order_food.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.order_food.Card.PopularFoodCard;
import com.example.order_food.R;
import com.example.order_food.adapter.PopularAdapter;
import com.example.order_food.db.entity.Food;
import com.example.order_food.service.FoodService;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<Food> foods = new ArrayList<>();

    boolean isScrolling = false;
    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
//        // Inflate the layout for this fragment
//        PopularFoodCard food1 = new PopularFoodCard(1,R.drawable.discoun1,"Food 1",12);
//        PopularFoodCard food2 = new PopularFoodCard(1,R.drawable.discount,"Food 2",15);
//        PopularFoodCard food3 = new PopularFoodCard(1,R.drawable.discount2,"Food 3",20);
//
//        foods.clear();

        RecyclerView recView = view.findViewById(R.id.rec_food_search);
        recView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recView.setAdapter(new PopularAdapter(requireContext(),getAllFoods()));

        recView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    isScrolling = false;
                } else {
                    isScrolling = true;
                }
            }
        });

        recView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if (!isScrolling && e.getAction() == MotionEvent.ACTION_UP) {
                    // Thực hiện điều hướng sang FoodDetailFragment khi một mục được chạm vào
                    FoodDetailFragment foodDetailFragment = FoodDetailFragment.newInstance();
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainerView, foodDetailFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                    return true; // Đánh dấu rằng sự kiện đã được xử lý
                }
                return false;
            }
        });
        return view;
    }
    private List<Food> getAllFoods() {
        // Retrieve all food items from the database using FoodService
        // You may want to run this on a background thread or use LiveData for better performance
        return FoodService.getInstance(requireContext()).getAllFoodItems();
    }
}