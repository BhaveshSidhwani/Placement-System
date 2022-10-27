package com.sidhwanibhavesh.placementpredictionsystem.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.sidhwanibhavesh.placementpredictionsystem.HttpRequestExecutor;
import com.sidhwanibhavesh.placementpredictionsystem.R;
import com.sidhwanibhavesh.placementpredictionsystem.TestConstants;

import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Activity for user sign up
public class SignupActivity extends AppCompatActivity {

    private AppCompatButton btnLogin;
    private AppCompatButton btnSignup;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextCgpa;
    private RadioGroup genderRadioGroup;
    private RadioGroup internshipRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViews();

//      Click Listener to start Login Activity
        btnLogin.setOnClickListener(view -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
        });

//      Click Listener to validate the user credentials and register the user
        btnSignup.setOnClickListener(view -> {
//          Get the user entries
            String username = editTextUsername.getText().toString().toLowerCase(Locale.ROOT);
            String password = editTextPassword.getText().toString();
            String confirmPassword = editTextConfirmPassword.getText().toString();
            String cgpa = editTextCgpa.getText().toString();
            RadioButton genderBtn = findViewById(genderRadioGroup.getCheckedRadioButtonId());
            RadioButton internshipBtn = findViewById(internshipRadioGroup.getCheckedRadioButtonId());
            String gender = "";
            String internship = "";

            boolean entriesValid = false;

            if (username.equals("") && password.equals("") && confirmPassword.equals("") && cgpa.equals("") ) {
                Toast.makeText(this, "Please fill all fields before signing up!!", Toast.LENGTH_SHORT).show();
            } else {
                if (emailIsValid(username))
                    entriesValid = true;
                else
                    Toast.makeText(this, "Enter valid email id", Toast.LENGTH_SHORT).show();

                if (entriesValid) {
                    if (passwordIsValid(password))
                        entriesValid = true;
                    else {
                        entriesValid = false;
                        Toast.makeText(this, "Password should be 6 characters long", Toast.LENGTH_SHORT).show();
                    }
                }

                if (entriesValid) {
                    if (cgpaIsValid(cgpa))
                        entriesValid = true;
                    else {
                        entriesValid = false;
                        Toast.makeText(this, "CGPA should be within 0 to 10", Toast.LENGTH_SHORT).show();
                    }
                }

                if (entriesValid) {
                    if (genderBtn != null) {
                        gender = genderBtn.getText().toString();
                        entriesValid = true;
                    } else {
                        entriesValid = false;
                        Toast.makeText(this, "Please fill all fields before signing up", Toast.LENGTH_SHORT).show();
                    }
                }

                if (entriesValid) {
                    if (internshipBtn != null) {
                        internship = internshipBtn.getText().toString();
                        entriesValid = true;
                    } else {
                        entriesValid = false;
                        Toast.makeText(this, "Please fill all fields before signing up", Toast.LENGTH_SHORT).show();
                    }
                }

                if (entriesValid) {
                    if (password.equals(confirmPassword)) {
                        HashMap<String, String> userCredentials = new HashMap<>();
                        userCredentials.put(TestConstants.JSON_USERNAME_KEY, username);
                        userCredentials.put(TestConstants.JSON_PASSWORD_KEY, password);
                        userCredentials.put(TestConstants.JSON_CGPA_KEY, cgpa);
                        userCredentials.put(TestConstants.JSON_GENDER_KEY, gender);
                        userCredentials.put(TestConstants.JSON_WORKEX_KEY, internship);

                        HttpRequestExecutor.registerUser(SignupActivity.this, userCredentials);
                    } else {
                        Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

//  Method to find UI components using id
    private void findViews() {
        btnLogin = findViewById(R.id.signup_btn_login);
        btnSignup = findViewById(R.id.signup_btn_signup);
        editTextUsername = findViewById(R.id.signup_username_edit_text);
        editTextPassword = findViewById(R.id.signup_password_edit_text);
        editTextConfirmPassword = findViewById(R.id.signup_confirm_password_edit_text);
        editTextCgpa = findViewById(R.id.cgpa_edit_text);
        genderRadioGroup = findViewById(R.id.gender_radio_group);
        internshipRadioGroup = findViewById(R.id.internship_radio_group);
    }

//  Validate password
    private boolean passwordIsValid (String password) {
        return password.length() > 5;
    }

//  Validate Email
    private boolean emailIsValid (String email) {
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);

        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

//  Validate CGPA
    private boolean cgpaIsValid (String cgpaStr) {
        double cgpa;
        try {
            cgpa = Double.parseDouble(cgpaStr);
        } catch (NumberFormatException e) {
            return false;
        }

        return (cgpa > 0) && (cgpa < 10);
    }
}