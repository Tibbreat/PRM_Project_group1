package com.example.order_food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.order_food.Fragment.CartFragment;
import com.example.order_food.Fragment.FavoriteFragment;
import com.example.order_food.Fragment.HistoryFragment;
import com.example.order_food.Fragment.HomeFragment;
import com.example.order_food.Fragment.ProfileFragment;
import com.example.order_food.Fragment.SearchFragment;
import com.example.order_food.adapter.OrderCartAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    HomeFragment homeFragment;
    CartFragment cartFragment;
    SearchFragment searchFragment;
    ProfileFragment profileFragment;
    HistoryFragment historyFragment;

    FavoriteFragment favoriteFragment;

    FragmentManager fragmentManager;

    FragmentTransaction frag_tran;
    SharedPreferences shared_pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shared_pref = getSharedPreferences("account",MODE_PRIVATE);
        String userId = shared_pref.getString("id","");

        homeFragment = HomeFragment.newInstance(userId);
        cartFragment = CartFragment.newInstance(userId,null);
        searchFragment = SearchFragment.newInstance(null,null);
        historyFragment = HistoryFragment.newInstance(userId,null);
        profileFragment = ProfileFragment.newInstance();
        favoriteFragment = FavoriteFragment.newInstance(userId, null);

        fragmentManager = getSupportFragmentManager();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.home){
                    frag_tran = fragmentManager.beginTransaction();
                    frag_tran.replace(R.id.fragmentContainerView,homeFragment,"homeFragment");
                    frag_tran.commit();
                    return true;
                }
                else if(id == R.id.cart){
                    frag_tran = fragmentManager.beginTransaction();
                    frag_tran.replace(R.id.fragmentContainerView,cartFragment,"cartFragment");
                    frag_tran.commit();
                    return true;
                }
                else if(id == R.id.search){
                    frag_tran = fragmentManager.beginTransaction();
                    frag_tran.replace(R.id.fragmentContainerView,searchFragment,"searchFragment");
                    frag_tran.commit();
                    return true;
                }
                else if(id == R.id.history){
                    frag_tran = fragmentManager.beginTransaction();
                    frag_tran.replace(R.id.fragmentContainerView,historyFragment,"historyFragment");
                    frag_tran.commit();
                    return true;
                }
                else if(id == R.id.profile){
                    frag_tran = fragmentManager.beginTransaction();
                    frag_tran.replace(R.id.fragmentContainerView,profileFragment,"profileFragment");
                    frag_tran.commit();
                    return true;
                }
                return false;
            }
        });

        ((ImageView)findViewById(R.id.favorite_icons)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frag_tran = fragmentManager.beginTransaction();
                frag_tran.replace(R.id.fragmentContainerView,favoriteFragment,"favoriteFragment");
                frag_tran.commit();
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}