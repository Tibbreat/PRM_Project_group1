package com.example.order_food.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.order_food.Card.PopularFoodCard;
import com.example.order_food.R;
import com.example.order_food.adapter.FavoriteCartAdapter;
import com.example.order_food.service.FoodService;

import java.util.ArrayList;
import java.util.List;

public class  FavoriteFragment extends Fragment {
    SharedPreferences preferences;
    String userID;
    FragmentTransaction frag_tran;
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
        Log.d("favourite_fragment", "getInto onCreate favoriteFragment");
        preferences = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
        userID = preferences.getString("id", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        int id=-1;
        try{
            id = Integer.parseInt(userID);
        }catch (Exception e) {
            frag_tran = getParentFragmentManager().beginTransaction();
            frag_tran.replace(R.id.fragmentContainerView, HomeFragment.newInstance(), "homeFragment");
            frag_tran.commit();
        }
        foods = FoodService.getInstance(requireContext()).getFavoriteFood(id);
        Log.d("favourite_fragment", "getInto onCreateView get list of fav: "+foods.size());
        RecyclerView recView = view.findViewById(R.id.rec_favorite_food);
        recView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recView.setAdapter(new FavoriteCartAdapter(foods, String.valueOf(userID), getContext(), getParentFragmentManager()));

        return view;
    }
}