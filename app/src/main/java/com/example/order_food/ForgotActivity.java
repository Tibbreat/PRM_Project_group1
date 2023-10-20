package com.example.order_food;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class ForgotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);


        ((Button) findViewById(R.id.btn_reset)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean error = false;
                String email = ((EditText) findViewById(R.id.edt_regis_email)).getText().toString().trim();
                TextView err_email = ((TextView) findViewById(R.id.err_regis_email));


                if (email.length() == 0) {
                    err_email.setVisibility(View.VISIBLE);
                    err_email.setText("* Email is required");
                    error = true;
                } else {
                    err_email.setVisibility(View.INVISIBLE);
                }

                if (!error) {
                    Intent intent = new Intent(ForgotActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        ((Button) findViewById(R.id.btn_cancle)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}
