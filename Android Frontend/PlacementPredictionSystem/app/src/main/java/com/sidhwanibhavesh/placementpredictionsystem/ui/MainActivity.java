package com.sidhwanibhavesh.placementpredictionsystem.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;

import com.sidhwanibhavesh.placementpredictionsystem.R;

// Landing Activity
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Find UI components using id
        AppCompatButton loginBtn = findViewById(R.id.btn_login_main);
        AppCompatButton signupBtn = findViewById(R.id.btn_signup_main);

//      Click Listener to start Login Activity
        loginBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

//      Click Listener to start Sign Up Activity
        signupBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }
}