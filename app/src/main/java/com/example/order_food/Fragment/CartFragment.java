package com.example.order_food.Fragment;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.order_food.Card.PopularFoodCard;
import com.example.order_food.Config.PathDataForPreferences;
import com.example.order_food.R;
import com.example.order_food.adapter.OrderCartAdapter;
import com.example.order_food.service.FoodService;
import com.example.order_food.service.OrderService;

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
    SharedPreferences preferences;
    String userID;
    float total;
    FragmentTransaction frag_tran;
    List<PopularFoodCard> orderCartCards = new ArrayList<>();

    public CartFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
        userID = preferences.getString("id", "");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        assert getArguments() != null;
        orderCartCards = PathDataForPreferences.getOrderCart(userID);

        orderCartCards = getFoodByListOfID(orderCartCards);

        RecyclerView recView = view.findViewById(R.id.rec_order_cart_food);
        recView.setLayoutManager(new LinearLayoutManager(requireContext()));
        OrderCartAdapter adapter = new OrderCartAdapter(orderCartCards, userID, getContext());
        adapter.setOnItemChangeListener(this);
        recView.setAdapter(adapter);

        total = 0;
        for (PopularFoodCard orderCartCard : orderCartCards) {
            total = total + (orderCartCard.getFoodPrice() * orderCartCard.getQuantity());
        }
        TextView textView = view.findViewById(R.id.food_order_cart_total);
        textView.setText(total + "$");

        view.findViewById(R.id.btn_order_cart_order).setOnClickListener(view1 -> {
            total = 0;
            for (PopularFoodCard orderCartCard : orderCartCards) {
                total = total + (orderCartCard.getFoodPrice() * orderCartCard.getQuantity());
            }
            Toast.makeText(requireContext(), "Order process with total "+total+"$", Toast.LENGTH_SHORT).show();
            boolean checkCreate = OrderService.getInstance(getContext()).insertOrder(total, userID, orderCartCards);
            if(orderCartCards.size()==0){
                return;
            }
            if (checkCreate) {
                PathDataForPreferences.updateOrderCart(new ArrayList<>(), userID);
                Toast.makeText(requireContext(), "Order added successfully", Toast.LENGTH_SHORT).show();
                frag_tran = getParentFragmentManager().beginTransaction();
                frag_tran.replace(R.id.fragmentContainerView, HomeFragment.newInstance(), "homeFragment");
                frag_tran.commit();
            }else{
                Toast.makeText(requireContext(), "Order added Fail", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onListChange(String variable) {
        TextView textView = requireView().findViewById(R.id.food_order_cart_total);
        textView.setText(variable + "$");
    }

    private List<PopularFoodCard> getFoodByListOfID(List<PopularFoodCard> ids) {
        return FoodService.getInstance(requireContext()).getFoodItemsByListOfID(ids);
    }
}