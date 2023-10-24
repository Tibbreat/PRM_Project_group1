package com.example.order_food.adminFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.order_food.R;
import com.example.order_food.db.entity.Food;
import com.example.order_food.service.FoodService;

import java.io.FileOutputStream;
import java.io.InputStream;

public class CreateFoodFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageViewFood;
    private Button btnChooseImage, btnAddFood;
    private ImageButton btnBack;
    private EditText editFoodName, editFoodPrice, editFoodDescription, editFoodIngredients;
    private Uri selectedImageUri;

    public CreateFoodFragment() {
    }

    private void initUI(View view) {
        imageViewFood = view.findViewById(R.id.imageViewFood);
        btnChooseImage = view.findViewById(R.id.btn_choose_image);
        editFoodName = view.findViewById(R.id.edit_food_name);
        editFoodPrice = view.findViewById(R.id.edit_food_price);
        editFoodDescription = view.findViewById(R.id.edit_food_description);
        editFoodIngredients = view.findViewById(R.id.edit_food_ingredients);
        btnAddFood = view.findViewById(R.id.btn_add_food);
        btnBack = view.findViewById(R.id.buttonBackToFoodManagement);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_food, container, false);
        initUI(view);

        btnChooseImage.setOnClickListener(v -> openImagePicker());
        btnAddFood.setOnClickListener(v -> insert());
        btnBack.setOnClickListener(v -> replaceFragment(new FoodManagementFragment()));

        return view;
    }

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
            selectedImageUri = data.getData();
            imageViewFood.setImageURI(selectedImageUri);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView2, fragment);
        transaction.commit();
    }

    private void insert() {
        String foodName = editFoodName.getText().toString().trim();
        String foodPrice = editFoodPrice.getText().toString().trim();
        String foodDescription = editFoodDescription.getText().toString().trim();
        String foodIngredients = editFoodIngredients.getText().toString().trim();

        if (validateInput(foodName, foodPrice, foodDescription, foodIngredients)) {
            // Save the image to app's internal storage
            String imageFileName = null;
            Uri imageUri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/drawable/eating");
            if (selectedImageUri == null){
                imageFileName = saveImageToInternalStorage(imageUri);
            } else {
                imageFileName = saveImageToInternalStorage(selectedImageUri);
            }
            Float parsePrice = Float.parseFloat(foodPrice);

            if (imageFileName != null) {
                Food food = new Food(foodName, parsePrice, foodDescription, foodIngredients, imageFileName);
                FoodService foodService = FoodService.getInstance(requireContext());
                boolean insertionResult = foodService.insert(food);

                if (insertionResult) {
                    clearFields();
                    Toast.makeText(requireContext(), "Food item added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Failed to add food item", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Failed to save the image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String saveImageToInternalStorage(Uri selectedImageUri) {
        try {
            // Generate a unique file name for the image
            String imageFileName = "food_" + System.currentTimeMillis() + ".png";

            // Open an input stream from the selected image URI
            InputStream inputStream = requireContext().getContentResolver().openInputStream(selectedImageUri);

            // Create an output stream to the app's internal storage
            FileOutputStream outputStream = requireContext().openFileOutput(imageFileName, Context.MODE_PRIVATE);

            // Copy the image from the input stream to the output stream
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // Close the streams
            inputStream.close();
            outputStream.close();

            // Return the file path to the saved image
            return imageFileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private boolean validateInput(String foodName, String foodPrice, String foodDescription, String foodIngredients) {
        if (foodName.isEmpty() || foodPrice.isEmpty() || foodDescription.isEmpty() || foodIngredients.isEmpty()) {
            showToast("All fields are required");
            return false;
        }
        try {
            Float price = Float.parseFloat(foodPrice);

            if (price <= 0) {
                showToast("Price must be positive");
                return false;
            }
        } catch (NumberFormatException e) {
            showToast("Invalid price format");
            return false;
        }

        return true;
    }

    private void clearFields() {
        editFoodName.setText("");
        editFoodPrice.setText("");
        editFoodDescription.setText("");
        editFoodIngredients.setText("");
        imageViewFood.setImageResource(R.drawable.placeholder);
        selectedImageUri = null;
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
