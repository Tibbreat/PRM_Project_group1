package com.example.order_food;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.order_food.db.entity.User;
import com.example.order_food.service.UserService;

public class ResetPasswordActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private EditText edtNewPassword;
    private EditText edtConfirmPassword;
    private Button changePasswordBtn;
    private String userEmail;
    SharedPreferences shared_pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intentUser = getIntent();
        if (intentUser != null) {
            userEmail = intentUser.getStringExtra("userEmail");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        btnBack = findViewById(R.id.buttonBack);
        edtNewPassword = findViewById(R.id.edt_new_password);
        edtConfirmPassword = findViewById(R.id.edt_confirm_password);
        changePasswordBtn = findViewById(R.id.reset_password_btn);

        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword(userEmail);
            }
        });
    }

    private void changePassword(String userEmailS) {
        String newPassword = edtNewPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();

        TextView err_newPassword = ((TextView)findViewById(R.id.err_newPass));
        TextView err_confirmNewPassword = ((TextView)findViewById(R.id.err_confirmNewPass));

        boolean error = false;

        if (userEmailS.isEmpty()) {
            Toast.makeText(ResetPasswordActivity.this, "Failed connect!", Toast.LENGTH_SHORT).show();
            error = true;
        }

        // Validate new password
        if (newPassword.isEmpty()) {
            err_newPassword.setVisibility(View.VISIBLE);
            err_newPassword.setText("* New password is required");
            error = true;
        } else {
            err_newPassword.setVisibility(View.INVISIBLE);
        }

        // Validate confirm password
        if (confirmPassword.isEmpty()) {
            err_confirmNewPassword.setVisibility(View.VISIBLE);
            err_confirmNewPassword.setText("* Confirm new password is required");
            error = true;
        } else if (!newPassword.equals(confirmPassword)) {
            err_confirmNewPassword.setVisibility(View.VISIBLE);
            err_confirmNewPassword.setText("* Passwords do not match");
            error = true;
        } else {
            err_confirmNewPassword.setVisibility(View.INVISIBLE);
        }

        if(!error){
            shared_pref = getSharedPreferences("account", MODE_PRIVATE);

            UserService userService = UserService.getInstance(this);
            User user = userService.getUserByEmail(userEmailS);
            if (user != null) {
                // Update the password
                int rowsAffected = userService.changePassword(userEmail, newPassword);

                if (rowsAffected > 0) {
                    Toast.makeText(ResetPasswordActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "Failed to change password", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

