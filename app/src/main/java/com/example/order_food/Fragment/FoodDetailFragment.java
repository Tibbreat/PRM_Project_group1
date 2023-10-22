package com.example.order_food.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.order_food.R;
import com.example.order_food.db.entity.Food;

import java.io.File;

public class FoodDetailFragment extends Fragment {
    private TextView food_name;
    private TextView food_price;
    private TextView food_description;
    private Button btn_add;
    private ImageView food_image;

    private Context context;
    public static FoodDetailFragment newInstance() {
        return new FoodDetailFragment();
    }
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_detail, container, false);
        food_name = view.findViewById(R.id.tv_name);
        food_description = view.findViewById(R.id.tv_description);
        food_price = view.findViewById(R.id.tv_price);
        food_image = view.findViewById(R.id.img_food);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Food food = (Food) bundle.getSerializable("food");

            if (food != null) {
                food_name.setText("Name : " + food.getFoodName());
                food_description.setText("Food's description : " + food.getFoodDescription());
                food_price.setText(String.valueOf("Price: "+food.getFoodPrice()+" $"));
                try {
                    String imageFileName = food.getImageUri();
                    Bitmap bitmap = BitmapFactory.decodeFile(requireContext().getFilesDir() + "/" + imageFileName);
                    food_image.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("image", "onCreateView: " + e);
                }

            }
        }
        return view;
    }
}
