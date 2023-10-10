package com.example.order_food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

        if(!usn.equals("") && !usp.equals("")){
            Intent newIntent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(newIntent);
        }


        ((Button)findViewById(R.id.login_btn)).setOnClickListener(new View.OnClickListener() {
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
                    editor.putString("email",email);
                    editor.putString("password",password);
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this,AdminActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}