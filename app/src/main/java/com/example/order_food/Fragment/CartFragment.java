package com.example.order_food.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.order_food.Card.PopularFoodCard;
import com.example.order_food.Config.PathDataForPreferences;
import com.example.order_food.R;
import com.example.order_food.adapter.OrderCartAdapter;
import com.example.order_food.service.FoodService;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment implements OrderCartAdapter.OnItemChangeListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ID = "ID";
    private static final String ARG_PARAM2 = "param2";
    private String userID;
    private String mParam2;

    List<PopularFoodCard> orderCartCards = new ArrayList<>();

    public CartFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userID = getArguments().getString(ID);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

//        PopularFoodCard food1 = new PopularFoodCard(1,R.drawable.discoun1,"Food 1",12, 1);
//        PopularFoodCard food2 = new PopularFoodCard(2,R.drawable.discount,"Food 2",15,2);
//        PopularFoodCard food3 = new PopularFoodCard(3,R.drawable.discount2,"Food 3",20,3);
//
//        orderCartCards.clear();
//        orderCartCards.add(food1);
//        orderCartCards.add(food2);
//        orderCartCards.add(food3);
        assert getArguments() != null;
        orderCartCards = PathDataForPreferences.getOrderCart(userID);

        orderCartCards = getFoodByListOfID(orderCartCards);

        RecyclerView recView = view.findViewById(R.id.rec_order_cart_food);
        recView.setLayoutManager(new LinearLayoutManager(requireContext()));
        OrderCartAdapter adapter = new OrderCartAdapter(orderCartCards, userID, getContext());
        adapter.setOnItemChangeListener(this);
        recView.setAdapter(adapter);

        float total = 0;
        for(PopularFoodCard orderCartCard: orderCartCards){
            total = total +(orderCartCard.getFoodPrice() * orderCartCard.getQuantity());
        }
        TextView textView = view.findViewById(R.id.food_order_cart_total);
        textView.setText(total+"$");

        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onListChange(String variable) {
        TextView textView = requireView().findViewById(R.id.food_order_cart_total);
        textView.setText(variable+ "$");
    }
    private List<PopularFoodCard> getFoodByListOfID(List<PopularFoodCard> ids) {
        // Retrieve all food items from the database using FoodService
        // You may want to run this on a background thread or use LiveData for better performance
        return FoodService.getInstance(requireContext()).getFoodItemsByListOfID(ids);
    }
}