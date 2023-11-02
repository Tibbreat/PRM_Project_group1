package com.example.order_food.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.order_food.Config.PathDataForPreferences;
import com.example.order_food.R;
import com.example.order_food.db.entity.FavoriteFood;
import com.example.order_food.db.entity.Food;
import com.example.order_food.service.FavoriteFoodService;

import java.io.File;

public class FoodDetailFragment extends Fragment {
    private TextView food_name;
    private TextView food_price;
    private TextView food_description;
    private ImageView food_image;

    public static FoodDetailFragment newInstance(String param1) {
        FoodDetailFragment fragment = new FoodDetailFragment();
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
        View view = inflater.inflate(R.layout.fragment_food_detail, container, false);
        food_name = view.findViewById(R.id.tv_name);
        food_description = view.findViewById(R.id.tv_description);
        food_price = view.findViewById(R.id.tv_price);
        food_image = view.findViewById(R.id.img_food);

        Bundle bundle = getArguments();
        if (bundle != null) {
            Food food = (Food) bundle.getSerializable("food");
            String userID = (String) bundle.getSerializable("userID");
            Log.d("image", "onCreateView: "+ userID);
            Log.d("image", "onCreateView: "+ bundle);
            if (food != null) {

                food_name.setText("Name : " + food.getFoodName());
                food_description.setText("Food's description : " + food.getFoodDescription());
                food_price.setText("Price: " + food.getFoodPrice() + " $");
                try {
                    String imageFileName = food.getImageUri();
                    Bitmap bitmap = BitmapFactory.decodeFile(requireContext().getFilesDir() + "/" + imageFileName);
                    food_image.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("image", "onCreateView: " + e);
                }
                ((TextView) view.findViewById(R.id.btn_add)).setOnClickListener(newView -> {
                    assert userID != null;
                    PathDataForPreferences.addNewOrderCart(userID, food.getId());
                    CartFragment cartFragment = CartFragment.newInstance();

                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainerView, cartFragment);
                    transaction.commit();
                });
                ((ImageView) view.findViewById(R.id.btn_favicon_add)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean check = checkFavFood(Integer.parseInt(userID), food.getId());
                        Log.d("image", "check: "+check);
                        if(check == false){
                            insertFavFood(new FavoriteFood(food.getId(), Integer.parseInt(userID)));
                            Toast.makeText(requireContext(), "add to favorite successfully", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(requireContext(), "already on your favorite", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

        return view;
    }

    public boolean checkFavFood(int uid, int pid){
        return FavoriteFoodService.getInstance(requireContext()).checkFavFood(uid, pid);
    }

    public boolean insertFavFood(FavoriteFood favoriteFood){
        return FavoriteFoodService.getInstance(requireContext()).insert(favoriteFood);
    }
}
