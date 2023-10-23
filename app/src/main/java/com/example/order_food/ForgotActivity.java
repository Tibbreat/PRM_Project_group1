package com.example.order_food;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.order_food.db.entity.User;
import com.example.order_food.service.UserService;

public class ForgotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);


        ((Button) findViewById(R.id.changes_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean error = false;
                String email = ((EditText) findViewById(R.id.edt_forgot_email)).getText().toString().trim();
                String address = ((EditText)findViewById(R.id.edt_forgot_address)).getText().toString().trim();

                TextView err_forgot_email = ((TextView) findViewById(R.id.err_forgot_email));
                TextView err_forgot_address = ((TextView)findViewById(R.id.err_forgot_address));

                if (email.length() == 0) {
                    err_forgot_email.setVisibility(View.VISIBLE);
                    err_forgot_email.setText("* Email is required");
                    error = true;
                } else {
                    err_forgot_email.setVisibility(View.INVISIBLE);
                }
                if(address.length() == 0){
                    err_forgot_address.setVisibility(View.VISIBLE);
                    err_forgot_address.setText("* Address is required");
                    error = true;
                }
                else{
                    err_forgot_address.setVisibility(View.INVISIBLE);
                }

                if (!error) {
                    UserService userService = UserService.getInstance(ForgotActivity.this);
                    User user = userService.getUserReset(email, address);

                    if (user != null) {
                        Intent intent = new Intent(ForgotActivity.this, ResetPasswordActivity.class);
                        intent.putExtra("userEmail", email);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ForgotActivity.this, "Account does not exist!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        ((Button) findViewById(R.id.login_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}
