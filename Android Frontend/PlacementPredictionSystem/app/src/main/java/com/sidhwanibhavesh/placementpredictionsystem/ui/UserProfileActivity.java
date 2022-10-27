package com.sidhwanibhavesh.placementpredictionsystem.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.sidhwanibhavesh.placementpredictionsystem.HttpRequestExecutor;
import com.sidhwanibhavesh.placementpredictionsystem.R;
import com.sidhwanibhavesh.placementpredictionsystem.TestConstants;

import java.util.HashMap;

public class UserProfileActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    EditText cgpaEditText;
    RadioGroup genderRadioGroup;
    RadioGroup workexRadioGroup;
    TextView quantsTextView;
    TextView logicalTextView;
    TextView verbalTextView;
    TextView programmingTextView;
    AppCompatButton btnUpdateUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        findViews();

        Intent intent = getIntent();
        if (intent.hasExtra(TestConstants.JSON_USERNAME_KEY)
                && intent.hasExtra(TestConstants.JSON_PASSWORD_KEY)
                && intent.hasExtra(TestConstants.JSON_CGPA_KEY)
                && intent.hasExtra(TestConstants.JSON_GENDER_KEY)
                && intent.hasExtra(TestConstants.JSON_WORKEX_KEY)
                && intent.hasExtra(TestConstants.JSON_QUANTS_KEY)
                && intent.hasExtra(TestConstants.JSON_LOGICAL_REASONING_KEY)
                && intent.hasExtra(TestConstants.JSON_VERBAL_KEY)
                && intent.hasExtra(TestConstants.JSON_PROGRAMMING_KEY)) {
            setViews(intent);
        }

        btnUpdateUser.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String cgpa = cgpaEditText.getText().toString();
            RadioButton genderBtn = findViewById(genderRadioGroup.getCheckedRadioButtonId());
            String gender = genderBtn.getText().toString();
            RadioButton internshipBtn = findViewById(workexRadioGroup.getCheckedRadioButtonId());
            String internship = internshipBtn.getText().toString();

            if (username.equals("") && password.equals("") && cgpa.equals("") && gender.equals("") && internship.equals("")) {
                Toast.makeText(this, "Please fill all fields before signing up!!", Toast.LENGTH_SHORT).show();
            } else {
                HashMap<String, String> userMap = new HashMap<>(TestConstants.LOGGED_IN_USER.mUserMap);
                userMap.put(TestConstants.JSON_USERNAME_KEY, username);
                userMap.put(TestConstants.JSON_PASSWORD_KEY, password);
                userMap.put(TestConstants.JSON_CGPA_KEY, cgpa);
                userMap.put(TestConstants.JSON_GENDER_KEY, gender);
                userMap.put(TestConstants.JSON_WORKEX_KEY, internship);
                HttpRequestExecutor.updateUser(this, userMap);
            }
        });
    }

//  Method to find UI components using id
    private void findViews() {
        usernameEditText = findViewById(R.id.user_profile_username_edit_text);
        passwordEditText = findViewById(R.id.user_profile_password_edit_text);
        cgpaEditText = findViewById(R.id.user_profile_cgpa_edit_text);
        genderRadioGroup = findViewById(R.id.user_profile_gender_radio_group);
        workexRadioGroup = findViewById(R.id.user_profile_internship_radio_group);
        quantsTextView = findViewById(R.id.quants_score_text_view);
        logicalTextView = findViewById(R.id.logical_score_text_view);
        verbalTextView = findViewById(R.id.verbal_score_text_view);
        programmingTextView = findViewById(R.id.technical_score_text_view);
        btnUpdateUser = findViewById(R.id.update_user_btn);
    }

//  Initialize UI components
    private void setViews(Intent intent) {
        usernameEditText.setText(intent.getStringExtra(TestConstants.JSON_USERNAME_KEY));
        passwordEditText.setText(intent.getStringExtra(TestConstants.JSON_PASSWORD_KEY));
        cgpaEditText.setText(String.valueOf(intent.getFloatExtra(TestConstants.JSON_CGPA_KEY, 0)));

        String gender = intent.getStringExtra(TestConstants.JSON_GENDER_KEY);
        int genderRadioButtonId = R.id.female_radio_btn;
        if (gender.equals(getResources().getString(R.string.male_text)))
            genderRadioButtonId = R.id.male_radio_btn;
        genderRadioGroup.check(genderRadioButtonId);

        boolean internship = intent.getBooleanExtra(TestConstants.JSON_WORKEX_KEY, false);
        int internshipRadioButtonId = R.id.internship_no_radio_btn;
        if (internship)
            internshipRadioButtonId = R.id.internship_yes_radio_btn;
        workexRadioGroup.check(internshipRadioButtonId);

        quantsTextView.setText(getResources().getString(
                R.string.quantitative_score_text,
                intent.getIntExtra(TestConstants.JSON_QUANTS_KEY, -1)));

        logicalTextView.setText(getResources().getString(
                R.string.logical_score_text,
                intent.getIntExtra(TestConstants.JSON_LOGICAL_REASONING_KEY, -1)));

        verbalTextView.setText(getResources().getString(
                R.string.verbal_score_text,
                intent.getIntExtra(TestConstants.JSON_VERBAL_KEY, -1)));

        programmingTextView.setText(getResources().getString(
                R.string.programming_score_text,
                intent.getIntExtra(TestConstants.JSON_PROGRAMMING_KEY, -1)));
    }
}