package com.example.order_food.adminFragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.order_food.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountManagementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountManagementFragment extends Fragment {

    public AccountManagementFragment() {
        // Required empty public constructor
    }

    public static AccountManagementFragment newInstance() {
        AccountManagementFragment fragment = new AccountManagementFragment();
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
        View view = inflater.inflate(R.layout.fragment_account_management, container, false);

        AppCompatButton buttonCreate = view.findViewById(R.id.button_create_account);
        AppCompatButton buttonView = view.findViewById(R.id.button_view_account);

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace the current fragment with CreateFoodFragment
                replaceFragment(new CreateAccoutFragment());
            }
        });

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ViewListAccoutsFragment());
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