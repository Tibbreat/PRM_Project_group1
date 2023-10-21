package com.example.order_food;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.order_food.db.entity.User;
import com.example.order_food.service.UserService;

public class ChangePasswordActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private EditText edtOldPassword;
    private EditText edtNewPassword;
    private EditText edtConfirmPassword;
    private Button changePasswordBtn;
    SharedPreferences shared_pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        btnBack = findViewById(R.id.buttonBack);
        edtOldPassword = findViewById(R.id.edt_old_password);
        edtNewPassword = findViewById(R.id.edt_new_password);
        edtConfirmPassword = findViewById(R.id.edt_confirm_password);
        changePasswordBtn = findViewById(R.id.change_password_btn);

        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
            startActivity(intent);
        });

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
    }

    private void changePassword() {
        String oldPassword = edtOldPassword.getText().toString();
        String newPassword = edtNewPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();

        TextView err_oldPassword = ((TextView)findViewById(R.id.err_oldPass));
        TextView err_newPassword = ((TextView)findViewById(R.id.err_newPass));
        TextView err_confirmNewPassword = ((TextView)findViewById(R.id.err_confirmNewPass));

        boolean error = false;

        // Validate old password
        if (oldPassword.isEmpty()) {
            err_oldPassword.setVisibility(View.VISIBLE);
            err_oldPassword.setText("* Old password is required");
            error = true;
        } else {
            err_oldPassword.setVisibility(View.INVISIBLE);
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
            String userEmail = shared_pref.getString("email", "");

            UserService userService = UserService.getInstance(this);
            User user = userService.getUser(userEmail, oldPassword);
            if (user != null) {
                // Update the password
                int rowsAffected = userService.changePassword(userEmail, newPassword);

                if (rowsAffected > 0) {
                    Toast.makeText(ChangePasswordActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Failed to change password", Toast.LENGTH_SHORT).show();
                }
            } else {
                err_oldPassword.setVisibility(View.VISIBLE);
                err_oldPassword.setText("* Old password is incorrect");
            }
        }
    }
}

