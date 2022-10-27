package com.sidhwanibhavesh.placementpredictionsystem.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.sidhwanibhavesh.placementpredictionsystem.HttpRequestExecutor;
import com.sidhwanibhavesh.placementpredictionsystem.R;
import com.sidhwanibhavesh.placementpredictionsystem.TestConstants;
import com.sidhwanibhavesh.placementpredictionsystem.User;

// Activity for dashboard
public class HomeActivity extends AppCompatActivity {

    private AppCompatButton btnUserProfile;
    private AppCompatButton btnQuants;
    private AppCompatButton btnLogical;
    private AppCompatButton btnVerbal;
    private AppCompatButton btnTechnical;
    private AppCompatButton btnCompanyList;
    private AppCompatButton btnPredictProbability;
    private AppCompatButton btnLogout;
    private AppCompatButton btnResumeScorer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViews();
        setClickListeners();

        btnUserProfile.setText(getString(R.string.hello_user_text, TestConstants.LOGGED_IN_USER.mUsername));
    }

//  Method to find UI components using id
    private void findViews() {
        btnUserProfile = findViewById(R.id.btn_user_profile);
        btnQuants = findViewById(R.id.btn_quants);
        btnLogical = findViewById(R.id.btn_logical);
        btnVerbal = findViewById(R.id.btn_verbal);
        btnTechnical = findViewById(R.id.btn_technical);
        btnCompanyList = findViewById(R.id.btn_company_list);
        btnPredictProbability = findViewById(R.id.btn_predict_probability);
        btnResumeScorer = findViewById(R.id.btn_resume_scorer);
        btnLogout = findViewById(R.id.logout_btn);
    }

//  Method to set click listeners for UI components
    private void setClickListeners() {
//      Starts quantitative test
        btnQuants.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, QuizActivity.class);
            intent.putExtra(TestConstants.INTENT_EXTRA_SELECTED_TOPIC_KEY, TestConstants.INTENT_EXTRA_QUANTS_KEY);
            startActivity(intent);
        });

//      Starts logical test
        btnLogical.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, QuizActivity.class);
            intent.putExtra(TestConstants.INTENT_EXTRA_SELECTED_TOPIC_KEY, TestConstants.INTENT_EXTRA_LOGICAL_KEY);
            startActivity(intent);
        });

//      Starts verbal test
        btnVerbal.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, QuizActivity.class);
            intent.putExtra(TestConstants.INTENT_EXTRA_SELECTED_TOPIC_KEY, TestConstants.INTENT_EXTRA_VERBAL_KEY);
            startActivity(intent);
        });

//      Starts Technical test
        btnTechnical.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, QuizActivity.class);
            intent.putExtra(TestConstants.INTENT_EXTRA_SELECTED_TOPIC_KEY, TestConstants.INTENT_EXTRA_TECHNICAL_KEY);
            startActivity(intent);
        });

//      Displays the company list
        btnCompanyList.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, CompanyListActivity.class);
            startActivity(intent);
        });

//      Sends request to calculate the probability based on the user's profile
        btnPredictProbability.setOnClickListener(view -> {
            User loggedInUser = TestConstants.LOGGED_IN_USER;
//          Check if test scores are greater than 7
            if (loggedInUser.mQuantsScore < 7
                    && loggedInUser.mLogicalReasoningScore < 7
                    && loggedInUser.mVerbalScore < 7
                    && loggedInUser.mProgrammingScore < 7) {
                Toast.makeText(this, "Test results are less than 7", Toast.LENGTH_SHORT).show();
            } else {
                HttpRequestExecutor.predict(this);
            }
        });

//      Starts the Resume Scorer Activity
        btnResumeScorer.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ResumeActivity.class);
            startActivity(intent);
        });

//      Displays the user profile
        btnUserProfile.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, UserProfileActivity.class);
            intent.putExtra(TestConstants.JSON_USERNAME_KEY, TestConstants.LOGGED_IN_USER.mUsername);
            intent.putExtra(TestConstants.JSON_PASSWORD_KEY, TestConstants.LOGGED_IN_USER.mPassword);
            intent.putExtra(TestConstants.JSON_CGPA_KEY, TestConstants.LOGGED_IN_USER.mCgpa);
            intent.putExtra(TestConstants.JSON_GENDER_KEY, TestConstants.LOGGED_IN_USER.mGender);
            intent.putExtra(TestConstants.JSON_WORKEX_KEY, TestConstants.LOGGED_IN_USER.mInternship);
            intent.putExtra(TestConstants.JSON_QUANTS_KEY, TestConstants.LOGGED_IN_USER.mQuantsScore);
            intent.putExtra(TestConstants.JSON_LOGICAL_REASONING_KEY, TestConstants.LOGGED_IN_USER.mLogicalReasoningScore);
            intent.putExtra(TestConstants.JSON_VERBAL_KEY, TestConstants.LOGGED_IN_USER.mVerbalScore);
            intent.putExtra(TestConstants.JSON_PROGRAMMING_KEY, TestConstants.LOGGED_IN_USER.mProgrammingScore);
            startActivity(intent);
        });

//      Logs out the current user
        btnLogout.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}