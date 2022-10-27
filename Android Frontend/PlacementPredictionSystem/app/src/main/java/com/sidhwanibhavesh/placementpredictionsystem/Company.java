package com.sidhwanibhavesh.placementpredictionsystem;

// Company class
public class Company {
   public int mId;
   public String mCompanyName;
   public String mJobRole;
   public String mJobDescription;
   public int mPackage;
   public String mApplyLink;

   public Company (int id, String companyName, String jobRole, String jobDescription, int pkg, String applyLink) {
      mId = id;
      mCompanyName = companyName;
      mJobRole = jobRole;
      mJobDescription = jobDescription;
      mPackage = pkg;
      mApplyLink = applyLink;
   }

   @Override
   public String toString() {
      return "Company{" +
              "mId=" + mId +
              ", mCompanyName='" + mCompanyName + '\'' +
              ", mJobRole='" + mJobRole + '\'' +
              ", mJobDescription='" + mJobDescription + '\'' +
              ", mPackage=" + mPackage +
              ", mApplyLink='" + mApplyLink + '\'' +
              '}';
   }
}
