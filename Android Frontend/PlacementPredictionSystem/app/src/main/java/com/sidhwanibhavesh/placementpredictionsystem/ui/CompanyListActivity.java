package com.sidhwanibhavesh.placementpredictionsystem.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sidhwanibhavesh.placementpredictionsystem.Company;
import com.sidhwanibhavesh.placementpredictionsystem.CompanyListAdapter;
import com.sidhwanibhavesh.placementpredictionsystem.HttpRequestExecutor;
import com.sidhwanibhavesh.placementpredictionsystem.R;
import com.sidhwanibhavesh.placementpredictionsystem.TestConstants;

import java.util.ArrayList;
import java.util.List;

// Activity to display the list of companies
public class CompanyListActivity extends AppCompatActivity {

    public List<Company> companies;
    CompanyListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);

//      Fetch Companies
        HttpRequestExecutor.fetchCompanies(CompanyListActivity.this);

//      Initialize company list UI and adapters
        ListView companyListView = findViewById(R.id.companies_list);
        TextView emptyTextView = findViewById(R.id.empty_view);
        companyListView.setEmptyView(emptyTextView);
        mAdapter = new CompanyListAdapter(this, new ArrayList<>());
        companyListView.setAdapter(mAdapter);
        FloatingActionButton addCompanyBtn = findViewById(R.id.add_company);
        if (!TestConstants.LOGGED_IN_USER.mIsTpo) {
            addCompanyBtn.setVisibility(View.GONE);
        }
//      Click Listener for Add New Company Floating button
        addCompanyBtn.setOnClickListener(view -> {
            Intent intent = new Intent(CompanyListActivity.this, CompanyDetailActivity.class);
            startActivity(intent);
        });

//      Click Listener for clicks on a specific company in the list
        companyListView.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent intent = new Intent(CompanyListActivity.this, CompanyDetailActivity.class);
            intent.putExtra(TestConstants.JSON_COMPANY_NAME_KEY, companies.get(position).mCompanyName);
            intent.putExtra(TestConstants.JSON_JOB_ROLE_KEY, companies.get(position).mJobRole);
            intent.putExtra(TestConstants.JSON_JOB_DESCRIPTION_KEY, companies.get(position).mJobDescription);
            intent.putExtra(TestConstants.JSON_PACKAGE_KEY, companies.get(position).mPackage);
            intent.putExtra(TestConstants.JSON_APPLY_LINK_KEY, companies.get(position).mApplyLink);
            Log.d("HomeActivity", "OnItemClickListener: company: " + companies.get(position));
            startActivity(intent);
        });
    }

//  Helper method
    public void setCompanies (ArrayList<Company> c) {
        companies = c;
    }

//  Helper method
    public void initUI () {
        mAdapter.clear();

        mAdapter.addAll(companies);
    }
}