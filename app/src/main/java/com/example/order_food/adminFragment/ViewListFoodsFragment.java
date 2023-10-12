package com.example.order_food.adminFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.order_food.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewListFoodsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewListFoodsFragment extends Fragment {

    public ViewListFoodsFragment() {
        // Required empty public constructor
    }

    public static ViewListFoodsFragment newInstance() {
        ViewListFoodsFragment fragment = new ViewListFoodsFragment();
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

        View view = inflater.inflate(R.layout.fragment_view_list_foods, container, false);

        ImageButton buttonBackToFoodManagement = view.findViewById(R.id.buttonBackToFoodManagement);

        buttonBackToFoodManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FoodManagementFragment());
            }
        });

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView2, fragment);
        transaction.commit();
    }

}