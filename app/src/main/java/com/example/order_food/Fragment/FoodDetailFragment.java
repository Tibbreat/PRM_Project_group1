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

import androidx.fragment.app.Fragment;

import com.example.order_food.Config.PathDataForPreferences;
import com.example.order_food.R;
import com.example.order_food.db.entity.Food;

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
                });
            }
        }

        return view;
    }
}
