package com.example.order_food.adminFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.order_food.R;

public class CreateFoodFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageViewFood;
    private Button btnChooseImage;
    private EditText editFoodName, editFoodPrice, editFoodDescription, editFoodIngredients;

    private Uri selectedImageUri;

    public CreateFoodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_food, container, false);

        initUI(view);

        // Handle the "Choose Image" button click
        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        Button btnAddFood = view.findViewById(R.id.btn_add_food);
        btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logFoodInformation();
            }
        });

        // Handle other actions such as adding food to your app

        ImageButton buttonBackToFoodManagement = view.findViewById(R.id.buttonBackToFoodManagement);

        buttonBackToFoodManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FoodManagementFragment());
            }
        });


        return view;
    }

    private void initUI(View view) {
        imageViewFood = view.findViewById(R.id.imageViewFood);
        btnChooseImage = view.findViewById(R.id.btn_choose_image);
        editFoodName = view.findViewById(R.id.edit_food_name);
        editFoodPrice = view.findViewById(R.id.edit_food_price);
        editFoodDescription = view.findViewById(R.id.edit_food_description);
        editFoodIngredients = view.findViewById(R.id.edit_food_ingredients);
    }

    // Handle image selection by opening an image picker
    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            // Get the selected image's URI and store it in the class-level variable
            selectedImageUri = data.getData();
            // Set the selected image to the ImageView
            imageViewFood.setImageURI(selectedImageUri);
        }
    }

    // Add methods to handle adding food to your app

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView2, fragment);
        transaction.commit();
    }

    private void logFoodInformation() {
        String foodName = editFoodName.getText().toString();
        String foodPrice = editFoodPrice.getText().toString();
        String foodDescription = editFoodDescription.getText().toString();
        String foodIngredients = editFoodIngredients.getText().toString();

        // Log the image URI along with other information
        if (selectedImageUri != null) {
            Log.d("FoodInfo", "Image URI: " + selectedImageUri.toString());
        }

        Log.d("FoodInfo", "Food Name: " + foodName);
        Log.d("FoodInfo", "Food Price: " + foodPrice);
        Log.d("FoodInfo", "Food Description: " + foodDescription);
        Log.d("FoodInfo", "Food Ingredients: " + foodIngredients);

        // You can add more code here to save the food information to your app's database or perform other actions.
    }
}
