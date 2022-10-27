package com.sidhwanibhavesh.placementpredictionsystem.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.sidhwanibhavesh.placementpredictionsystem.HttpRequestExecutor;
import com.sidhwanibhavesh.placementpredictionsystem.R;

import java.util.HashMap;
import java.util.Locale;
// Activity for user login
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//      Find UI components using id
        AppCompatButton btnSignup = findViewById(R.id.create_account_btn);
        AppCompatButton btnLogin = findViewById(R.id.login_btn_login);
        EditText editTextUsername = findViewById(R.id.login_username_edit_text);
        EditText editTextPassword = findViewById(R.id.login_password_edit_text);

//      Click Listener to start Sign Up Activity
        btnSignup.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

//      Click Listener to Log in user after verifying the user's credentials
        btnLogin.setOnClickListener(view -> {
            String username = editTextUsername.getText().toString().toLowerCase(Locale.ROOT);
            String password = editTextPassword.getText().toString();

            HashMap<String, String> userCredentials = new HashMap<>();
            userCredentials.put("username", username);
            userCredentials.put("password", password);

            HttpRequestExecutor.authenticateUser(LoginActivity.this, userCredentials);
        });
    }
}