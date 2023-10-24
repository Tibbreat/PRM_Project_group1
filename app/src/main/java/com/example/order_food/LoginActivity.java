package com.example.order_food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.order_food.db.entity.User;
import com.example.order_food.service.UserService;

public class LoginActivity extends AppCompatActivity {


    SharedPreferences shared_pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        shared_pref = getSharedPreferences("account",MODE_PRIVATE);
        String usn = shared_pref.getString("email","");
        String usp = shared_pref.getString("password","");
        UserService userService = UserService.getInstance(LoginActivity.this);
        User user = userService.getUser(usn,usp);


        if(user != null){
            Intent newIntent;
            if(user.getRole().equals("admin")){
                newIntent = new Intent(LoginActivity.this, AdminActivity.class);
            }
            else{
                newIntent = new Intent(LoginActivity.this, MainActivity.class);
            }
            startActivity(newIntent);
        }


        ((Button)findViewById(R.id.register_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = ((EditText)findViewById(R.id.edt_email)).getText().toString().trim();
                String password = ((EditText)findViewById(R.id.edt_password)).getText().toString().trim();

                TextView err_email = ((TextView)findViewById(R.id.err_email));
                TextView err_password = ((TextView)findViewById(R.id.err_password));

                boolean error = false;

                if(email.length() == 0){
                    err_email.setVisibility(View.VISIBLE);
                    err_email.setText("* Email is required");
                    error = true;
                }
                else{
                    err_email.setVisibility(View.INVISIBLE);
                }

                if(password.length() == 0){
                    err_password.setVisibility(View.VISIBLE);
                    err_password.setText("* Password is required");
                    error = true;
                }
                else{
                    err_password.setVisibility(View.INVISIBLE);
                }

                if(!error){
                    editor = shared_pref.edit();

                    User user = userService.getUser(email,password);
                    if(user != null){
                        editor.putString("id",user.id+"");
                        editor.putString("email",email);
                        editor.putString("password",password);
                        editor.putString("address",user.address);
                        editor.putString("phone_number",user.phone);
                        editor.putString("name",user.name);
                        editor.commit();
                        Intent intent;
                        if(user.role.equals("admin")){
                            intent = new Intent(LoginActivity.this, AdminActivity.class);
                        }
                        else{
                            intent = new Intent(LoginActivity.this, MainActivity.class);
                        }
                        startActivity(intent);
                    }
                    else{
                        err_email.setVisibility(View.VISIBLE);
                        err_email.setText("* Email or password is incorrect!");
                    }
                }
            }
        });

        ((Button)findViewById(R.id.regis_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        ((TextView)findViewById(R.id.forgot_Password)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(LoginActivity.this,ForgotActivity.class);
                startActivity(intent);
            }
        });
    }
}