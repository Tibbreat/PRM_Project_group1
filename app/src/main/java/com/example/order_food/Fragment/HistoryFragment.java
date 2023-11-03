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

import com.example.order_food.Card.OrderCard;
import com.example.order_food.R;
import com.example.order_food.adapter.OrderHistoryAdapter;
import com.example.order_food.service.OrderService;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    SharedPreferences preferences;
    String userID;
    FragmentTransaction frag_tran;
    List<OrderCard> orders;

    public HistoryFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("history_fragment", "getInto onCreate history Fragment");
        preferences = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
        userID = preferences.getString("id", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        Log.d("history_fragment", "getInto onCreateView history Fragment");
        int id;
        try{
            id = Integer.parseInt(userID);
        }catch (Exception e) {
            frag_tran = getParentFragmentManager().beginTransaction();
            frag_tran.replace(R.id.fragmentContainerView, HomeFragment.newInstance(), "homeFragment");
            frag_tran.commit();
            return view;
        }
        Log.d("history_fragment", "getInto onCreateView history Fragment, get id successfully");
        orders = OrderService.getInstance(getContext()).getListOfOrder(id);
        Log.d("history_fragment", "getInto onCreateView history Fragment, get order total "+orders.size());
        try{
            RecyclerView recView = view.findViewById(R.id.rec_order_history);
            recView.setLayoutManager(new LinearLayoutManager(requireContext()));
            recView.setAdapter(new OrderHistoryAdapter(orders, getContext()));
        }catch (Exception e){
            Log.d("history_fragment", "getInto onCreateView history Fragment " + e.getMessage());
        }
        return view;
    }
}