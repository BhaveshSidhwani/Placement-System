package com.sidhwanibhavesh.placementpredictionsystem.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.sidhwanibhavesh.placementpredictionsystem.R;
import com.sidhwanibhavesh.placementpredictionsystem.TestConstants;

// Activity to display the test result
public class QuizResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

//      Find UI components using their id
        final AppCompatButton btnHome = findViewById(R.id.btn_home);
        final TextView correctAnswerTextView = findViewById(R.id.correct_ans_text_view);
        final TextView incorrectAnswerTextView = findViewById(R.id.incorrect_ans_text_view);

//      Get data from intent (Correct and Incorrect answers)
        final int correctAnswers = getIntent().getIntExtra(TestConstants.INTENT_EXTRA_CORRECT_ANSWER_KEY,0);
        final int incorrectAnswers = getIntent().getIntExtra(TestConstants.INTENT_EXTRA_INCORRECT_ANSWER_KEY,0);

//      Set text view to display number of correct and incorrect answers selected
        correctAnswerTextView.setText(getString(R.string.correct_answers_text, correctAnswers));
        incorrectAnswerTextView.setText(getString(R.string.incorrect_answers_text, incorrectAnswers));

//      Click Listener to move up to the Home Activity
        btnHome.setOnClickListener(view -> {
            startActivity(new Intent(QuizResultActivity.this, HomeActivity.class));
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(QuizResultActivity.this, HomeActivity.class));
        finish();
    }
}