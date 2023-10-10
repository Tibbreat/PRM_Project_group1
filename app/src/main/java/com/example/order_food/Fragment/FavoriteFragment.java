package com.example.order_food.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.order_food.Card.PopularFoodCard;
import com.example.order_food.R;
import com.example.order_food.adapter.PopularAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    List<PopularFoodCard> foods = new ArrayList<>();
    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        PopularFoodCard food1 = new PopularFoodCard(1,R.drawable.discoun1,"Food 1",12);
        PopularFoodCard food2 = new PopularFoodCard(1,R.drawable.discount,"Food 2",15);
        PopularFoodCard food3 = new PopularFoodCard(1,R.drawable.discount2,"Food 3",20);

        foods.clear();
        foods.add(food1);
        foods.add(food2);
        foods.add(food3);

        RecyclerView recView = view.findViewById(R.id.rec_favorite_food);
        recView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recView.setAdapter(new PopularAdapter(foods));

        return view;
    }
}