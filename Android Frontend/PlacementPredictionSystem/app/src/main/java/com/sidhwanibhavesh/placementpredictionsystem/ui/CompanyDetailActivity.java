package com.sidhwanibhavesh.placementpredictionsystem.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.sidhwanibhavesh.placementpredictionsystem.HttpRequestExecutor;
import com.sidhwanibhavesh.placementpredictionsystem.R;
import com.sidhwanibhavesh.placementpredictionsystem.TestConstants;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

// Activity to display the details of the company that is clicked or to add a new company
public class CompanyDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);

//      Find UI components using their id
        AppCompatButton btnAddCompany = findViewById(R.id.btn_add_company);
        AppCompatButton btnApplyCompany = findViewById(R.id.btn_apply_company);
        EditText companyNameEditText = findViewById(R.id.company_name_edit_text);
        EditText jobRoleEditText = findViewById(R.id.job_role_edit_text);
        EditText jobDescriptionEditText = findViewById(R.id.job_description_edit_text);
        EditText packageEditText = findViewById(R.id.package_edit_text);
        EditText applyLinkEditText = findViewById(R.id.apply_link_edit_text);

//      If user is not Training and Placement Officer then remove admin permissions from that user
        if (!TestConstants.LOGGED_IN_USER.mIsTpo) {
            companyNameEditText.setFocusable(false);
            jobRoleEditText.setFocusable(false);
            jobDescriptionEditText.setFocusable(false);
            packageEditText.setFocusable(false);
            applyLinkEditText.setFocusable(false);
            btnAddCompany.setVisibility(View.GONE);
            btnApplyCompany.setVisibility(View.VISIBLE);
        }
//      Get intent extras and initialize the UI based on the company that is clicked
        Intent intent = getIntent();
        if (intent.hasExtra(TestConstants.JSON_COMPANY_NAME_KEY)
                && intent.hasExtra(TestConstants.JSON_JOB_ROLE_KEY)
                && intent.hasExtra(TestConstants.JSON_JOB_DESCRIPTION_KEY)
                && intent.hasExtra(TestConstants.JSON_PACKAGE_KEY)
                && intent.hasExtra(TestConstants.JSON_APPLY_LINK_KEY)) {

            String companyName = intent.getStringExtra(TestConstants.JSON_COMPANY_NAME_KEY);
            String jobRole = intent.getStringExtra(TestConstants.JSON_JOB_ROLE_KEY);
            String jobDescription = intent.getStringExtra(TestConstants.JSON_JOB_DESCRIPTION_KEY);
            int pkg = intent.getIntExtra(TestConstants.JSON_PACKAGE_KEY, 0);
            String applyLink = intent.getStringExtra(TestConstants.JSON_APPLY_LINK_KEY);

            companyNameEditText.setText(companyName);
            jobRoleEditText.setText(jobRole);
            jobDescriptionEditText.setText(jobDescription);
            packageEditText.setText(String.valueOf(pkg));
            applyLinkEditText.setText(applyLink);

            btnAddCompany.setVisibility(View.INVISIBLE);
        }

//      Click Listener to add company
        btnAddCompany.setOnClickListener(view -> {
            String companyName = companyNameEditText.getText().toString().trim();
            String jobRole = jobRoleEditText.getText().toString().trim();
            String jobDescription = jobDescriptionEditText.getText().toString().trim();
            String pkg = packageEditText.getText().toString().trim();
            String applyLink = applyLinkEditText.getText().toString().trim();

            boolean entriesValid = false;

//          Check if Company name entry is valid or not
            if ( !companyName.equals("") && (isAlphabetic(companyName) || isAlphaNumeric(companyName)) )
                entriesValid = true;
            else {
                Toast.makeText(this, "Enter valid company name", Toast.LENGTH_SHORT).show();
            }

//          Check if Job role entry is valid or not
            if (entriesValid) {
                if ( !jobRole.equals("") && isAlphabetic(jobRole) )
                    entriesValid = true;
                else {
                    entriesValid = false;
                    Toast.makeText(this, "Enter valid job role", Toast.LENGTH_SHORT).show();
                }
            }

//          Check if Job description entry is valid or not
            if (entriesValid) {
                if ( !jobDescription.equals("") && (isAlphabetic(jobDescription) || isAlphaNumeric(jobDescription)) )
                    entriesValid = true;
                else {
                    entriesValid = false;
                    Toast.makeText(this, "Enter valid job description", Toast.LENGTH_SHORT).show();
                }
            }

//          Check if package entry is valid or not
            if (entriesValid) {
                if ( !pkg.equals("") && isNumeric(pkg))
                    entriesValid = true;
                else {
                    entriesValid = false;
                    Toast.makeText(this, "Enter valid package", Toast.LENGTH_SHORT).show();
                }
            }

//          Check if Application link entry is valid or not
            if (entriesValid) {
                if ( !applyLink.equals("") && applyLinkIsValid(applyLink) )
                    entriesValid = true;
                else {
                    entriesValid = false;
                    Toast.makeText(this, "Enter valid link",Toast.LENGTH_SHORT).show();
                }
            }

//          Add company to the database
            if (entriesValid) {
                HashMap<String, String> companyMap = new HashMap<>();
                companyMap.put(TestConstants.JSON_COMPANY_NAME_KEY, companyName);
                companyMap.put(TestConstants.JSON_JOB_ROLE_KEY, jobRole);
                companyMap.put(TestConstants.JSON_JOB_DESCRIPTION_KEY, jobDescription);
                companyMap.put(TestConstants.JSON_PACKAGE_KEY, pkg);
                companyMap.put(TestConstants.JSON_APPLY_LINK_KEY, applyLink);

                HttpRequestExecutor.addCompany(CompanyDetailActivity.this, companyMap);
            }
        });

//      Click listener for Apply button (Opens the URL in browser)
        btnApplyCompany.setOnClickListener(view -> {
            String url = applyLinkEditText.getText().toString().trim();
            Intent applyIntent = new Intent(Intent.ACTION_VIEW);
            applyIntent.setData(Uri.parse(url));
            startActivity(applyIntent);
        });
    }

//  Method to check if the input is Alpha-Numeric or not
    private static boolean isAlphaNumeric(String s) {
        return s != null && s.matches("^[a-zA-Z0-9]*$");
    }

//  Method to check if the input is Alphabetic or not
    private static boolean isAlphabetic(String s) {
        return s != null && s.matches("^[a-zA-Z]*$");
    }

//  Method to check if the input is Numeric or not
    private static boolean isNumeric(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

//  Method to check if the URL is valid or not
    private static boolean applyLinkIsValid (String s) {
        try {
            new URL(s);
        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }
}