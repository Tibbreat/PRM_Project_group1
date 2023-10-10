package com.example.order_food.Fragment;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.order_food.Card.PopularFoodCard;
import com.example.order_food.R;
import com.example.order_food.adapter.PopularAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    List<PopularFoodCard> foods = new ArrayList<>();



    public HomeFragment() {
        // Required empty public constructor
    }




    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ArrayList imageList = new ArrayList<SlideModel>();
        imageList.add(new SlideModel(R.drawable.discount,ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.discoun1,ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.discount2, ScaleTypes.FIT));

        ImageSlider imageSlider = view.findViewById(R.id.image_slider);
        imageSlider.setImageList(imageList);

        PopularFoodCard food1 = new PopularFoodCard(1,R.drawable.discoun1,"Food 1",12);
        PopularFoodCard food2 = new PopularFoodCard(1,R.drawable.discount,"Food 2",15);
        PopularFoodCard food3 = new PopularFoodCard(1,R.drawable.discount2,"Food 3",20);

        foods.clear();
        foods.add(food1);
        foods.add(food2);
        foods.add(food3);

        RecyclerView recView = view.findViewById(R.id.rec_popular_food);
        recView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recView.setAdapter(new PopularAdapter(foods));

        TextView btnNew = view.findViewById(R.id.btn_home_new);
        TextView btnPopular = view.findViewById(R.id.btn_home_popular);

        ((TextView)view.findViewById(R.id.btn_home_new)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View newView) {
                int textColor = ContextCompat.getColor(requireContext(), R.color.white);
                btnNew.setTextColor(textColor);
                btnNew.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.greenbuttongradient));

                btnPopular.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
                btnPopular.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.textviewshape));

                foods.clear();
                foods.add(food3);
                foods.add(food1);
                foods.add(food2);
                foods.add(food2);
                foods.add(food3);

                recView.getAdapter().notifyDataSetChanged();
            }
        });

        ((TextView)view.findViewById(R.id.btn_home_popular)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View newView) {
                int textColor = ContextCompat.getColor(requireContext(), R.color.white);
                btnPopular.setTextColor(textColor);
                btnPopular.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.greenbuttongradient));
                btnNew.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
                btnNew.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.textviewshape));


                foods.clear();
                foods.add(food1);
                foods.add(food2);
                foods.add(food3);

                recView.getAdapter().notifyDataSetChanged();
            }
        });

        return view;
    }
}