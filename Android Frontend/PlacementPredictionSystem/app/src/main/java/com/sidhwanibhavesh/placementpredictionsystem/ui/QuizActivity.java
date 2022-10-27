package com.sidhwanibhavesh.placementpredictionsystem.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sidhwanibhavesh.placementpredictionsystem.HttpRequestExecutor;
import com.sidhwanibhavesh.placementpredictionsystem.Question;
import com.sidhwanibhavesh.placementpredictionsystem.R;
import com.sidhwanibhavesh.placementpredictionsystem.TestConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

// Activity to conduct test
public class QuizActivity extends AppCompatActivity {

    private TextView questionNumberTextView;
    private TextView questionTextView;
    private AppCompatButton btnOptionA;
    private AppCompatButton btnOptionB;
    private AppCompatButton btnOptionC;
    private AppCompatButton btnOptionD;
    private AppCompatButton btnNext;
    private ImageView btnBack;
    private TextView timerTextView;
    private TextView selectedTopicNameTextView;

    private Timer quizTimer;
    private int totalTimeInMins = 10;
    private int seconds = 0;
    private int currentQuestionPosition = 0;
    private String selectedOptionByUser = "";

    private List<Question> questionList;

    private String selectedTopicName;
    private String testTypeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        findViews();
        setClickListeners();

//      Set the test domain (Quantitative, Logical, Verbal, Technical)
        selectedTopicName = getIntent().getStringExtra(TestConstants.INTENT_EXTRA_SELECTED_TOPIC_KEY);
        int testType = 1;
        switch (selectedTopicName) {
            case TestConstants.INTENT_EXTRA_QUANTS_KEY:
                testType = TestConstants.QUANTS_TEST_CODE;
                testTypeStr = TestConstants.JSON_QUANTS_KEY;
                break;
            case TestConstants.INTENT_EXTRA_LOGICAL_KEY:
                testType = TestConstants.LOGICAL_TEST_CODE;
                testTypeStr = TestConstants.JSON_LOGICAL_REASONING_KEY;
                break;
            case TestConstants.INTENT_EXTRA_VERBAL_KEY:
                testType = TestConstants.VERBAL_TEST_CODE;
                testTypeStr = TestConstants.JSON_VERBAL_KEY;
                break;
            case TestConstants.INTENT_EXTRA_TECHNICAL_KEY:
                testType = TestConstants.PROGRAMMING_TEST_CODE;
                testTypeStr = TestConstants.JSON_PROGRAMMING_KEY;
                break;
        }
        HttpRequestExecutor.fetchQuestions(this, testType);
    }

//  Method to find UI components using id
    private void findViews() {
        questionNumberTextView = findViewById(R.id.question_number);
        questionTextView = findViewById(R.id.questions);
        btnBack = findViewById(R.id.back_btn);
        timerTextView = findViewById(R.id.timer);
        selectedTopicNameTextView = findViewById(R.id.selected_topic_name);
        btnOptionA = findViewById(R.id.btn_option1);
        btnOptionB = findViewById(R.id.btn_option2);
        btnOptionC = findViewById(R.id.btn_option3);
        btnOptionD = findViewById(R.id.btn_option4);
        btnNext = findViewById(R.id.btn_next);
    }

//  Method to set click listeners for UI components
    private void setClickListeners() {
        btnOptionA.setOnClickListener(view -> {
            if(selectedOptionByUser.equals("")) {
                setSelectedOptionByUser(btnOptionA);
            }
        });

        btnOptionB.setOnClickListener(view -> {
            if(selectedOptionByUser.equals("")) {
                setSelectedOptionByUser(btnOptionB);
            }
        });

        btnOptionC.setOnClickListener(view -> {
            if(selectedOptionByUser.equals("")) {
                setSelectedOptionByUser(btnOptionC);
            }
        });

        btnOptionD.setOnClickListener(view -> {
            if(selectedOptionByUser.equals("")) {
                setSelectedOptionByUser(btnOptionD);
            }
        });

        btnNext.setOnClickListener(view -> {
            if(selectedOptionByUser.isEmpty()) {
                Toast.makeText(QuizActivity.this, "Please Select an Option", Toast.LENGTH_SHORT).show();
            } else {
                changeNextQuestion();
            }
        });

        btnBack.setOnClickListener(view -> {
            quizTimer.purge();
            quizTimer.cancel();
            startActivity(new Intent(QuizActivity.this, HomeActivity.class));
            finish();
        });
    }

//  Helper method to initialize UI
    public void initUI() {
        selectedTopicNameTextView.setText(selectedTopicName);
        Log.d("QuizActivity","QuestionList: " + questionList);
        startTimer(timerTextView);

        initQues();
    }

//  Helper method to set Questions list
    public void setQuestionList (List<Question> ques) {
        this.questionList = ques;
    }

//  Method to change question
    private void changeNextQuestion() {
        currentQuestionPosition++;
        if((currentQuestionPosition+1) == questionList.size()) {
            btnNext.setText(R.string.submit_quiz_text);
        }

        if(currentQuestionPosition < questionList.size()) {
            selectedOptionByUser = "";
            btnOptionA.setBackgroundResource(R.drawable.round_back_white10);
            btnOptionA.setTextColor(Color.parseColor("#1F6BB8"));

            btnOptionB.setBackgroundResource(R.drawable.round_back_white10);
            btnOptionB.setTextColor(Color.parseColor("#1F6BB8"));

            btnOptionC.setBackgroundResource(R.drawable.round_back_white10);
            btnOptionC.setTextColor(Color.parseColor("#1F6BB8"));

            btnOptionD.setBackgroundResource(R.drawable.round_back_white10);
            btnOptionD.setTextColor(Color.parseColor("#1F6BB8"));

            initQues();
        } else {
            endQuiz();
            finish();
        }
    }

//  Method to initialize UI for a new question
    private void initQues() {
        String questionNumber = (currentQuestionPosition+1) + "/" + questionList.size();
        questionNumberTextView.setText(questionNumber);
        questionTextView.setText( questionList.get(currentQuestionPosition).getQuestion() );
        btnOptionA.setText( questionList.get(currentQuestionPosition).getOptionA() );
        btnOptionB.setText( questionList.get(currentQuestionPosition).getOptionB() );
        btnOptionC.setText( questionList.get(currentQuestionPosition).getOptionC() );
        btnOptionD.setText( questionList.get(currentQuestionPosition).getOptionD() );
    }

//  Helper method to set the selected option
    private void setSelectedOptionByUser(AppCompatButton btnClicked) {
        selectedOptionByUser = btnClicked.getText().toString();
        btnClicked.setBackgroundResource(R.drawable.round_back_red_10);
        btnClicked.setTextColor(Color.WHITE);

        revealAnswer();
        questionList.get(currentQuestionPosition).setUserSelectedAns(selectedOptionByUser);
    }

//  Helper method to start the timer
    private void startTimer(TextView timerTextView) {
        quizTimer = new Timer();

        quizTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                 if(seconds == 0 && totalTimeInMins == 0) {
                    quizTimer.purge();
                    quizTimer.cancel();
                    runOnUiThread(() -> Toast.makeText(QuizActivity.this, "Time Over", Toast.LENGTH_SHORT).show());
                    endQuiz();

                    finish();
                } else if(seconds == 0) {
                    totalTimeInMins--;
                    seconds = 59;
                } else {
                    seconds--;
                }

                runOnUiThread(() -> {
                    String finalMins = String.valueOf(totalTimeInMins);
                    String finalSecs = String.valueOf(seconds);
                    if(finalMins.length() == 1) {
                        finalMins = "0" + finalMins;
                    }

                    if(finalSecs.length() == 1) {
                        finalSecs = "0" + finalSecs;
                    }

                    String displayedTime = finalMins + ":" + finalSecs;
                    timerTextView.setText(displayedTime);
                });
            }
        },1000,1000);
    }

//  Method to terminate the test
    private void endQuiz() {
        int correctAns = getCorrectAns();
        HashMap<String, String> userMap = TestConstants.LOGGED_IN_USER.mUserMap;
        userMap.put(testTypeStr, String.valueOf(correctAns));
        HttpRequestExecutor.updateUser(this, userMap);
        Intent intent = new Intent(QuizActivity.this, QuizResultActivity.class);
        intent.putExtra(TestConstants.INTENT_EXTRA_CORRECT_ANSWER_KEY, correctAns);
        intent.putExtra(TestConstants.INTENT_EXTRA_INCORRECT_ANSWER_KEY, getIncorrectAns());
        startActivity(intent);
    }

//  Helper method to get the correctly selected answer by the user
    private int getCorrectAns() {
        int correctAns = 0;
        for(int i = 0; i< questionList.size(); i++) {
            final String getUserSelectedAns = questionList.get(i).getUserSelectedAns();
            final String getAns = questionList.get(i).getAnswer();

            if(getUserSelectedAns.equals(getAns)) {
                correctAns++;
            }
        }
        return correctAns;
    }

//  Helper method to get the incorrectly selected answer by the user
    private int getIncorrectAns() {
        int correctAns = 0;
        for(int i = 0; i< questionList.size(); i++) {
            final String getUserSelectedAns = questionList.get(i).getUserSelectedAns();
            final String getAns = questionList.get(i).getAnswer();

            if(!getUserSelectedAns.equals(getAns)) {
                correctAns++;
            }
        }
        return correctAns;
    }

    @Override
    public void onBackPressed() {
        quizTimer.purge();
        quizTimer.cancel();
        startActivity(new Intent(QuizActivity.this, MainActivity.class));
        finish();
    }

//  Helper method to show the correct answer
    private void revealAnswer() {
        final String answer = questionList.get(currentQuestionPosition).getAnswer();
        if(btnOptionA.getText().toString().equals(answer)) {
            btnOptionA.setBackgroundResource(R.drawable.round_back_green10);
            btnOptionA.setTextColor(Color.WHITE);
        } else if(btnOptionB.getText().toString().equals(answer)) {
            btnOptionB.setBackgroundResource(R.drawable.round_back_green10);
            btnOptionB.setTextColor(Color.WHITE);
        } else if(btnOptionC.getText().toString().equals(answer)) {
            btnOptionC.setBackgroundResource(R.drawable.round_back_green10);
            btnOptionC.setTextColor(Color.WHITE);
        } else if(btnOptionD.getText().toString().equals(answer)) {
            btnOptionD.setBackgroundResource(R.drawable.round_back_green10);
            btnOptionD.setTextColor(Color.WHITE);
        }
    }
}