package com.sidhwanibhavesh.placementpredictionsystem;

import java.util.HashMap;

// Class for logged in user
public class User {
    public int mId;
    public String mUsername;
    public String mPassword;
    public boolean mIsTpo;
    public String mGender;
    public float mCgpa;
    public boolean mInternship;
    public int mQuantsScore;
    public int mLogicalReasoningScore;
    public int mVerbalScore;
    public int mProgrammingScore;

    public HashMap<String, String> mUserMap;

    public User (HashMap<String, String> userMap) {
        mId = Integer.parseInt(userMap.get(TestConstants.JSON_ID_KEY));
        mUsername = userMap.get(TestConstants.JSON_USERNAME_KEY);
        mPassword = userMap.get(TestConstants.JSON_PASSWORD_KEY);
        mIsTpo = userMap.get(TestConstants.JSON_TPO_KEY).equals("t");
        mGender = userMap.get(TestConstants.JSON_GENDER_KEY);
        mCgpa = Float.parseFloat(userMap.get(TestConstants.JSON_CGPA_KEY));
        mInternship = userMap.get(TestConstants.JSON_WORKEX_KEY).equals("t");
        mQuantsScore = Integer.parseInt(userMap.get(TestConstants.JSON_QUANTS_KEY));
        mLogicalReasoningScore = Integer.parseInt(userMap.get(TestConstants.JSON_LOGICAL_REASONING_KEY));
        mVerbalScore = Integer.parseInt(userMap.get(TestConstants.JSON_VERBAL_KEY));
        mProgrammingScore = Integer.parseInt(userMap.get(TestConstants.JSON_PROGRAMMING_KEY));

        mUserMap = userMap;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + mId +
                ", Username='" + mUsername + '\'' +
                ", Password='" + mPassword + '\'' +
                ", IsTpo=" + mIsTpo +
                ", Gender='" + mGender + '\'' +
                ", Cgpa=" + mCgpa +
                ", Internship=" + mInternship +
                ", QuantsScore=" + mQuantsScore +
                ", LogicalReasoningScore=" + mLogicalReasoningScore +
                ", VerbalScore=" + mVerbalScore +
                ", ProgrammingScore=" + mProgrammingScore +
                '}';
    }
}
