package com.example.order_food.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.order_food.Card.PopularFoodCard;
import com.example.order_food.R;
import com.example.order_food.adapter.PopularAdapter;
import com.example.order_food.db.entity.Food;
import com.example.order_food.service.FoodService;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    List<Food> foods = new ArrayList<>();


    boolean isScrolling = false;


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

        List<Food> allNewFoods = FoodService.getInstance(requireContext()).getAllNewFoods();
        List<Food> allPopularFoods = FoodService.getInstance(requireContext()).getAllPopularFoods();

        foods.clear();
        foods.addAll(allPopularFoods);

        RecyclerView recView = view.findViewById(R.id.rec_popular_food);
        recView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recView.setAdapter(new PopularAdapter(requireContext(),foods));

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
                foods.addAll(allNewFoods);
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
                foods.addAll(allPopularFoods);
                recView.getAdapter().notifyDataSetChanged();
            }
        });


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
}