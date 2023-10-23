package com.example.order_food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.order_food.Fragment.ProfileFragment;
import com.example.order_food.adminFragment.AccountManagementFragment;
import com.example.order_food.adminFragment.DashboardFragment;
import com.example.order_food.adminFragment.FoodManagementFragment;
import com.example.order_food.adminFragment.OrderManagementFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {

    DashboardFragment dashboard;
    AccountManagementFragment accountManagement;
    FoodManagementFragment foodManagement;
    OrderManagementFragment orderManagement;

    ProfileFragment profile;

    FragmentManager fragmentManager;

    FragmentTransaction frag_tran;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        dashboard = DashboardFragment.newInstance();
        accountManagement = AccountManagementFragment.newInstance();
        foodManagement = FoodManagementFragment.newInstance();
        orderManagement = OrderManagementFragment.newInstance(null,null);
        profile = ProfileFragment.newInstance();

        fragmentManager = getSupportFragmentManager();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.dashboard){
                    frag_tran = fragmentManager.beginTransaction();
                    frag_tran.replace(R.id.fragmentContainerView2,dashboard,"dashboardFragment");
                    frag_tran.commit();
                    return true;
                }
                else if(id == R.id.accountManagement){
                    frag_tran = fragmentManager.beginTransaction();
                    frag_tran.replace(R.id.fragmentContainerView2,accountManagement,"cartFragment");
                    frag_tran.commit();
                    return true;
                }
                else if(id == R.id.foodManagement){
                    frag_tran = fragmentManager.beginTransaction();
                    frag_tran.replace(R.id.fragmentContainerView2,foodManagement,"searchFragment");
                    frag_tran.commit();
                    return true;
                }
                else if(id == R.id.orderManagement){
                    frag_tran = fragmentManager.beginTransaction();
                    frag_tran.replace(R.id.fragmentContainerView2,orderManagement,"historyFragment");
                    frag_tran.commit();
                    return true;
                }
                else if(id == R.id.profileManagment){
                    frag_tran = fragmentManager.beginTransaction();
                    frag_tran.replace(R.id.fragmentContainerView2,profile,"profileManagement");
                    frag_tran.commit();
                    return true;
                }
                return false;
            }
        });
    }
}