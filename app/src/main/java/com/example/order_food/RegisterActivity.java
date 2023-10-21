package com.example.order_food;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.order_food.db.entity.User;
import com.example.order_food.service.UserService;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ((Button)findViewById(R.id.register_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean error = false;
                String name = ((EditText)findViewById(R.id.edt_regis_name)).getText().toString().trim();
                String email = ((EditText)findViewById(R.id.edt_regis_email)).getText().toString().trim();
                String phone = ((EditText)findViewById(R.id.edt_regis_phone)).getText().toString().trim();
                String address = ((EditText)findViewById(R.id.edt_regis_address)).getText().toString().trim();
                String password = ((EditText)findViewById(R.id.edt_regis_password)).getText().toString().trim();
                String re_password = ((EditText)findViewById(R.id.edt_regis_re_password)).getText().toString().trim();

                TextView err_name = ((TextView)findViewById(R.id.err_regis_name));
                TextView err_email = ((TextView)findViewById(R.id.err_regis_email));
                TextView err_phone = ((TextView)findViewById(R.id.err_regis_phone));
                TextView err_address = ((TextView)findViewById(R.id.err_regis_address));
                TextView err_password = ((TextView)findViewById(R.id.err_regis_password));
                TextView err_re_password = ((TextView)findViewById(R.id.err_regis_re_password));
                if(name.length() == 0){
                    err_name.setVisibility(View.VISIBLE);
                    err_name.setText("* Name is required");
                    error = true;
                }
                else{
                    err_name.setVisibility(View.INVISIBLE);
                }
                if(email.length() == 0){
                    err_email.setVisibility(View.VISIBLE);
                    err_email.setText("* Email is required");
                    error = true;
                }
                else{
                    err_email.setVisibility(View.INVISIBLE);
                }
                if(phone.length() != 10){
                    err_phone.setVisibility(View.VISIBLE);
                    err_phone.setText("* The phone number must have exactly 10 digits");
                    error = true;
                }
                else{
                    err_phone.setVisibility(View.INVISIBLE);
                }
                if(address.length() == 0){
                    err_address.setVisibility(View.VISIBLE);
                    err_address.setText("* Address is required");
                    error = true;
                }
                else{
                    err_address.setVisibility(View.INVISIBLE);
                }
                if(password.length() == 0){
                    err_password.setVisibility(View.VISIBLE);
                    err_password.setText("* password is required");
                    error = true;
                }
                else{
                    err_password.setVisibility(View.INVISIBLE);
                }
                if(!re_password.equals(password)){
                    err_re_password.setVisibility(View.VISIBLE);
                    err_re_password.setText("* Not match with the password");
                    error = true;
                }
                else{
                    err_re_password.setVisibility(View.INVISIBLE);
                }

                if (!error) {
                    User user = new User(name, email, password, "user", address, phone);

                    UserService userService = UserService.getInstance(RegisterActivity.this);
                    boolean isInserted = userService.insert(user);

                    if (isInserted) {
                        Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
        ((Button)findViewById(R.id.login_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}