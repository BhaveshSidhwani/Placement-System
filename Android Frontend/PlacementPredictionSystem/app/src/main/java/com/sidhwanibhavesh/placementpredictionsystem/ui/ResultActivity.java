package com.sidhwanibhavesh.placementpredictionsystem.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.sidhwanibhavesh.placementpredictionsystem.R;
import com.sidhwanibhavesh.placementpredictionsystem.TestConstants;

import org.json.JSONException;
import org.json.JSONObject;

// Activity to display the job prediction result
public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

//      Find UI Components using id
        TextView resultTextView = findViewById(R.id.result_text_view);
        TextView resultTypeTextView = findViewById(R.id.result_type_text_view);

//      Get result from intent data
        Intent intent = getIntent();
        String resultType;
        if (intent.hasExtra(TestConstants.INTENT_EXTRA_RESULT_TYPE_KEY)) {
            resultType = intent.getStringExtra(TestConstants.INTENT_EXTRA_RESULT_TYPE_KEY);
            resultTypeTextView.setText(resultType);
        }

        double prediction = intent.getDoubleExtra(TestConstants.INTENT_EXTRA_VALUE_KEY, -1);

//      Converting the double to percentage
        prediction = prediction * 100;
        int pred_value = (int) prediction;
        String pred_str = pred_value + " %";
        resultTextView.setText(pred_str);
    }
}