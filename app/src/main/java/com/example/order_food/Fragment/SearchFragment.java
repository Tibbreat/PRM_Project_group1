package com.example.order_food.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        List<Food> allFoods= getAllFoods();
        foods.clear();
        foods.addAll(allFoods);

        RecyclerView recView = view.findViewById(R.id.rec_food_search);
        recView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recView.setAdapter(new PopularAdapter(requireContext(), foods, new PopularAdapter.IclickItemFood() {
            @Override
            public void getFoodDetail(Food food) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("food", food);
                FoodDetailFragment foodDetailFragment = new FoodDetailFragment();
                foodDetailFragment.setArguments(bundle);

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, foodDetailFragment);
                transaction.commit();
            }
        }));
        EditText search = view.findViewById(R.id.edt_search_value);
        ((Button)view.findViewById(R.id.btn_search)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = search.getText().toString().trim();
                foods.clear();
                foods.addAll(getSearchFoods(name));
                recView.getAdapter().notifyDataSetChanged();

            }
        });
        return view;
    }
    private List<Food> getAllFoods() {
        // Retrieve all food items from the database using FoodService
        // You may want to run this on a background thread or use LiveData for better performance
        return FoodService.getInstance(requireContext()).getAllFoodItems();
    }
    private List<Food> getSearchFoods(String searchValue){
        return FoodService.getInstance(requireContext()).getSearchFoods(searchValue);
    }
}