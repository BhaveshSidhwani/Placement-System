package com.sidhwanibhavesh.placementpredictionsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

// Class to display company list dynamically
public class CompanyListAdapter extends ArrayAdapter {

   public CompanyListAdapter(Context context, ArrayList<Company> companies) {
      super(context, 0, companies);
   }


// Method to populate the convertView
   @NonNull
   @Override
   public View getView(int position, View convertView, ViewGroup parent) {
      View listItemView = convertView;
      if (listItemView == null) {
         listItemView = LayoutInflater.from(getContext()).inflate(
                 R.layout.company_list_element, parent, false);
      }

      Company currentCompany = (Company) getItem(position);

      TextView companyNameTextView = listItemView.findViewById(R.id.company_name_text_view);
      TextView jobRoleTextView = listItemView.findViewById(R.id.job_role_text_view);
      TextView packageTextView = listItemView.findViewById(R.id.package_text_view);

      companyNameTextView.setText(currentCompany.mCompanyName);

      jobRoleTextView.setText(currentCompany.mJobRole);

      double pkg = (double) currentCompany.mPackage / 100000;
      packageTextView.setText(String.valueOf(pkg));

      return listItemView;
   }
}
