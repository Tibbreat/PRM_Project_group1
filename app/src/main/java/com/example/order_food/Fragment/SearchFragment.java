package com.example.order_food.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

    SharedPreferences preferences;
    String userID;
    List<Food> foods = new ArrayList<>();

    boolean isScrolling = false;
    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
        userID = preferences.getString("id","");
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
        recView.setAdapter(new PopularAdapter(requireContext(), userID, foods, (food, id) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("food", food);
            bundle.putSerializable("userID", id);
            FoodDetailFragment foodDetailFragment = new FoodDetailFragment();
            foodDetailFragment.setArguments(bundle);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, foodDetailFragment);
            transaction.commit();
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