package com.sidhwanibhavesh.placementpredictionsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.sidhwanibhavesh.placementpredictionsystem.ui.CompanyListActivity;
import com.sidhwanibhavesh.placementpredictionsystem.ui.HomeActivity;
import com.sidhwanibhavesh.placementpredictionsystem.ui.LoginActivity;
import com.sidhwanibhavesh.placementpredictionsystem.ui.QuizActivity;
import com.sidhwanibhavesh.placementpredictionsystem.ui.ResultActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Class to execute Http Requests
public final class HttpRequestExecutor {

    private static final String LOG_TAG = HttpRequestExecutor.class.getSimpleName();
    static ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    static Handler handler = new Handler(Looper.getMainLooper());

//  Method to authenticate user by sending an authentication request to the server
    public static void authenticateUser (Context context, HashMap<String, String> userCredentials) {
        singleThreadExecutor.execute(() -> {
            URL requestUrl = prepareUrl(TestConstants.BASE_URL, TestConstants.USER_AUTH_PATH);
            if (requestUrl != null) {
                JSONObject postData = preparePostData(userCredentials);
                JSONObject responseData = makeHttpRequest(requestUrl, postData);
                Log.d(LOG_TAG, "authenticateUser: responseData: " + responseData);
                handler.post(() -> {
                    if (responseData != null) {
                        try {
                            switch (responseData.getInt(TestConstants.JSON_RESPONSE_CODE_KEY)) {
                                case TestConstants.AUTH_SUCCESS_CODE:
                                    Toast.makeText(context, "Logged in successfully", Toast.LENGTH_SHORT).show();

                                    JSONObject dataJson = responseData.getJSONObject(TestConstants.JSON_RESPONSE_DATA_KEY);
                                    Log.d(LOG_TAG, "authenticateUser: dataJson: " + dataJson);
                                    HashMap<String, String> loggedinUserMap = jsonToMap(dataJson);
                                    TestConstants.LOGGED_IN_USER = new User(loggedinUserMap);
                                    Log.d(LOG_TAG, "authenticateUser: LOGGED_IN_USER: " + TestConstants.LOGGED_IN_USER);

                                    Intent intent = new Intent(context, HomeActivity.class);
                                    context.startActivity(intent);

                                    break;
                                case TestConstants.AUTH_INVALID_CREDENTIALS_CODE:
                                    Toast.makeText(context, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                                    break;
                                case TestConstants.AUTH_DB_CONN_ERROR_CODE:
                                    Toast.makeText(context, "Database not connected", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else
                        Toast.makeText(context, "Some error occured", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

//  Method to register new user by sending a registration request to the server
    public static void registerUser (Context context, HashMap<String, String> userCredentials) {
        singleThreadExecutor.execute(() -> {
            URL requestUrl = prepareUrl(TestConstants.BASE_URL, TestConstants.USER_REGISTRATION_PATH);
            if (requestUrl != null) {
                JSONObject postData = preparePostData(userCredentials);
                JSONObject responseData = makeHttpRequest(requestUrl, postData);
                Log.d(LOG_TAG, "registerUser: responseData: " + responseData);
                handler.post(() -> {
                    if (responseData != null) {
                        try {
                            switch (responseData.getInt(TestConstants.JSON_RESPONSE_CODE_KEY)) {
                                case TestConstants.REGISTRATION_SUCCESS_CODE:
                                    Toast.makeText(context, "User registered successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context, LoginActivity.class);
                                    context.startActivity(intent);
                                    break;
                                case TestConstants.REGISTRATION_USER_EXISTS_CODE:
                                    Toast.makeText(context, "Username already exists", Toast.LENGTH_SHORT).show();
                                    break;
                                case TestConstants.REGISTRATION_DB_CONN_ERROR_CODE:
                                    Toast.makeText(context, "Database not connected", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else
                        Toast.makeText(context, "Some error occured", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

//  Method to update user details by sending an update request to the server
    public static void updateUser (Context context, HashMap<String, String> userCredentials) {
        singleThreadExecutor.execute(() -> {
            URL requestUrl = prepareUrl(TestConstants.BASE_URL, TestConstants.USER_UPDATE_PATH);
            if (requestUrl != null) {
                JSONObject postData = preparePostData(userCredentials);
                JSONObject responseData = makeHttpRequest(requestUrl, postData);
                Log.d(LOG_TAG, "registerUser: responseData: " + responseData);
                handler.post(() -> {
                    if (responseData != null) {
                        try {
                            switch (responseData.getInt(TestConstants.JSON_RESPONSE_CODE_KEY)) {
                                case TestConstants.UPDATE_SUCCESS_CODE:
                                    JSONObject dataJson = responseData.getJSONObject(TestConstants.JSON_RESPONSE_DATA_KEY);
                                    Log.d(LOG_TAG, "authenticateUser: dataJson: " + dataJson);
                                    HashMap<String, String> loggedinUserMap = jsonToMap(dataJson);
                                    TestConstants.LOGGED_IN_USER = new User(loggedinUserMap);
                                    Log.d(LOG_TAG, "updateUser: LOGGED_IN_USER: " + TestConstants.LOGGED_IN_USER);
                                    Toast.makeText(context, "User updated successfully", Toast.LENGTH_SHORT).show();
                                    break;
                                case TestConstants.UPDATE_USER_EXISTS_CODE:
                                    Toast.makeText(context, "Username already exists", Toast.LENGTH_SHORT).show();
                                    break;
                                case TestConstants.UPDATE_DB_CONN_ERROR_CODE:
                                    Toast.makeText(context, "Database not connected", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else
                        Toast.makeText(context, "Some error occured", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

//  Method to get the list of companies by sending a fetch request to the server
    public static void fetchCompanies (CompanyListActivity activity) {
        singleThreadExecutor.execute(() -> {
            URL requestUrl = prepareUrl(TestConstants.BASE_URL, TestConstants.COMPANY_FETCH_PHP);
            if (requestUrl != null) {
                JSONObject responseData = makeHttpRequest(requestUrl, null);
                Log.d(LOG_TAG, "fetchCompanies: responseData: " + responseData);
                handler.post(() -> {
                    if (responseData != null) {
                        try{
                            switch (responseData.getInt(TestConstants.JSON_RESPONSE_CODE_KEY)) {
                                case TestConstants.FETCH_COMPANY_SUCCESS_CODE:
                                    Log.d(LOG_TAG, "success");
                                    ArrayList<Company> companies = retrieveCompanyDataFromJson(responseData);
                                    activity.setCompanies(companies);
                                    activity.initUI();
                                    Log.d(LOG_TAG, "companies: " + companies);
                                    break;
                                case TestConstants.FETCH_COMPANY_EMPTY_CODE:
                                    Toast.makeText(activity, "Empty list", Toast.LENGTH_SHORT).show();
                                    break;
                                case TestConstants.FETCH_COMPANY_DB_CONN_ERROR_CODE:
                                    Toast.makeText(activity, "Database not connected", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else
                        Toast.makeText(activity, "Some error occured", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

//  Method to add a new company by sending a add new company request to the server
    public static void addCompany (Context context, HashMap<String, String> companyMap) {
        singleThreadExecutor.execute(() -> {
            URL requestUrl = prepareUrl(TestConstants.BASE_URL, TestConstants.ADD_COMPANY_PATH);
            if (requestUrl != null) {
                JSONObject postData = preparePostData(companyMap);
                JSONObject responseData = makeHttpRequest(requestUrl, postData);
                Log.d(LOG_TAG, "addCompany: responseData: " + responseData);
                handler.post(() -> {
                    if (responseData != null) {
                        try {
                            switch (responseData.getInt(TestConstants.JSON_RESPONSE_CODE_KEY)) {
                                case TestConstants.ADD_COMPANY_SUCCESS_CODE:
                                    Toast.makeText(context, "Company added successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context, CompanyListActivity.class);
                                    context.startActivity(intent);
                                    break;
                                case TestConstants.ADD_COMPANY_EXISTS_CODE:
                                    Toast.makeText(context, "Company already exists", Toast.LENGTH_SHORT).show();
                                    break;
                                case TestConstants.ADD_COMPANY_DB_CONN_ERROR_CODE:
                                    Toast.makeText(context, "Database not connected", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else
                        Toast.makeText(context, "Some error occured", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

//  Method to get the list of questions for the test by sending a fetch request to the server
    public static void fetchQuestions (QuizActivity activity, int testType) {
        singleThreadExecutor.execute(() -> {
            URL requestUrl = prepareUrl(TestConstants.BASE_URL, TestConstants.QUESTIONS_FETCH_PATH);
            if (requestUrl != null) {
                HashMap<String, Integer> testTypeMap = new HashMap<>();
                testTypeMap.put(TestConstants.JSON_TEST_TYPE_KEY, testType);
                JSONObject postData = new JSONObject(testTypeMap);
                JSONObject responseData = makeHttpRequest(requestUrl, postData);
                Log.d(LOG_TAG, "fetchQuestions: responseData: " + responseData);

                handler.post(() -> {
                    if (responseData != null) {
                        try{
                            switch (responseData.getInt(TestConstants.JSON_RESPONSE_CODE_KEY)) {
                                case TestConstants.FETCH_QUESTION_SUCCESS_CODE:
                                    Log.d(LOG_TAG, "success");
                                    ArrayList<Question> questions = retrieveQuestionsFromJson(responseData);
                                    Log.d(LOG_TAG, "questions: " + questions);
                                    activity.setQuestionList(questions);
                                    activity.initUI();
                                    break;
                                case TestConstants.FETCH_QUESTION_EMPTY_CODE:
                                    Toast.makeText(activity, "Empty list", Toast.LENGTH_SHORT).show();
                                    break;
                                case TestConstants.FETCH_QUESTION_INCORRECT_TEST_CODE:
                                    Toast.makeText(activity, "Incorrect code", Toast.LENGTH_SHORT).show();
                                    break;
                                case TestConstants.FETCH_QUESTION_DB_CONN_ERROR_CODE:
                                    Toast.makeText(activity, "Database not connected", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else
                        Toast.makeText(activity, "Some error occured", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

//  Method to predict job probability by sending a request to the server
    public static void predict(Context context) {
        singleThreadExecutor.execute(() -> {
            URL requestUrl = prepareUrl(TestConstants.PYTHON_BASE_URL, TestConstants.PREDICT_PROBABILITY_PATH);
            if (requestUrl != null) {
                HashMap<String, String> userMap = new HashMap<>();
                userMap.put(TestConstants.JSON_PREDICT_GENDER_KEY, TestConstants.LOGGED_IN_USER.mGender);
                userMap.put(TestConstants.JSON_PREDICT_CGPA_KEY, String.valueOf(TestConstants.LOGGED_IN_USER.mCgpa));
                userMap.put(TestConstants.JSON_PREDICT_WORKEX_KEY, String.valueOf(TestConstants.LOGGED_IN_USER.mInternship));
                userMap.put(TestConstants.JSON_PREDICT_QUANTS_KEY, String.valueOf(25 -TestConstants.LOGGED_IN_USER.mQuantsScore));
                userMap.put(TestConstants.JSON_PREDICT_LOGICAL_REASONING_KEY, String.valueOf(25 - TestConstants.LOGGED_IN_USER.mLogicalReasoningScore));
                userMap.put(TestConstants.JSON_PREDICT_VERBAL_KEY, String.valueOf(25 - TestConstants.LOGGED_IN_USER.mVerbalScore));
                userMap.put(TestConstants.JSON_PREDICT_PROGRAMMING_KEY, String.valueOf(25 - TestConstants.LOGGED_IN_USER.mProgrammingScore));

                JSONObject postData = new JSONObject(userMap);
                JSONObject responseData = makeHttpRequest(requestUrl, postData);
                Log.d(LOG_TAG, "fetchQuestions: responseData: " + responseData);

                handler.post(() -> {
                    if (responseData != null) {
                        try{
                            double prediction;
                            if(responseData.has(TestConstants.JSON_RESPONSE_VALUE_KEY)) {
                                prediction = responseData.getDouble(TestConstants.JSON_RESPONSE_VALUE_KEY);
                                Intent intent = new Intent(context, ResultActivity.class);
                                intent.putExtra(TestConstants.INTENT_EXTRA_RESULT_TYPE_KEY, TestConstants.INTENT_EXTRA_PREDICTION_KEY);
                                intent.putExtra(TestConstants.INTENT_EXTRA_VALUE_KEY, prediction);
                                context.startActivity(intent);
                            } else {
                                Toast.makeText(context, "Some error occurred", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else
                        Toast.makeText(context, "Some error occured", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

//  Method to get the resume similarity score by sending a request to the server
    public static void checkResume(Context context, String resumeContent, String jdContent) {
        singleThreadExecutor.execute(() -> {
            URL requestUrl = prepareUrl(TestConstants.PYTHON_BASE_URL, TestConstants.RESUME_PATH);
            if (requestUrl != null) {
                HashMap<String, String> resumeMap = new HashMap<>();
                resumeMap.put(TestConstants.JSON_CHECKER_RESUME_KEY, resumeContent);
                resumeMap.put(TestConstants.JSON_JOB_DESCRIPTION_KEY, jdContent);

                JSONObject postData = new JSONObject(resumeMap);
                JSONObject responseData = makeHttpRequest(requestUrl, postData);
                Log.d(LOG_TAG, "checkResume: responseData: " + responseData);

                handler.post(() -> {
                    if (responseData != null) {
                        try{
                            double matchPercentage;
                            if(responseData.has(TestConstants.JSON_RESPONSE_VALUE_KEY)) {
                                matchPercentage = responseData.getDouble(TestConstants.JSON_RESPONSE_VALUE_KEY);
                                Intent intent = new Intent(context, ResultActivity.class);
                                intent.putExtra(TestConstants.INTENT_EXTRA_RESULT_TYPE_KEY, TestConstants.INTENT_EXTRA_RESUME_SIMILARITY_KEY);
                                intent.putExtra(TestConstants.INTENT_EXTRA_VALUE_KEY, matchPercentage);
                                context.startActivity(intent);
                            } else {
                                Toast.makeText(context, "Some error occurred", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else
                        Toast.makeText(context, "Some error occured", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    // ----------------------------------PRIVATE FUNCTIONS-----------------------------------------

//  Method to prepare the URL of the API
    private static URL prepareUrl (String baseUrl, String path) {
        URL requestUrl = null;
        try {
            requestUrl = new URL(
                    new URL(baseUrl),
                    path
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return requestUrl;
    }

//  Method to prepare JSON data for POST HTTP request
    private static JSONObject preparePostData (HashMap<String, String> postData) {
        return new JSONObject(postData);
    }

//  Method to connect to the prepared URL and return the response from the server
    private static JSONObject makeHttpRequest (URL requestUrl, JSONObject postData) {
        JSONObject responseJson = null;

        HttpURLConnection conn = null;
        StringBuilder builder = null;
        try {
            conn = (HttpURLConnection) requestUrl.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            if (postData != null) {
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
                writer.write(postData.toString());
                writer.close();
            }

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line;
                builder = new StringBuilder();
                while ( (line = reader.readLine()) != null ) {
                    builder.append(line);
                }
                reader.close();
            } else {
                Log.e (LOG_TAG, "Error Response Code: " + conn.getResponseCode());
            }

            if (builder != null) {
                responseJson = new JSONObject(builder.toString());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return responseJson;
    }

//  Method to extract company data from JSON data
    private static ArrayList<Company> retrieveCompanyDataFromJson(JSONObject jsonResponse) {
        ArrayList<Company> companies = new ArrayList<>();
        try {
            JSONArray jsonArray = jsonResponse.getJSONArray(TestConstants.JSON_RESPONSE_DATA_KEY);
            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject row = (JSONObject) jsonArray.get(i);
                int id = row.getInt(TestConstants.JSON_ID_KEY);
                String companyName = row.getString(TestConstants.JSON_COMPANY_NAME_KEY);
                String jobRole = row.getString(TestConstants.JSON_JOB_ROLE_KEY);
                String jobDescription = row.getString(TestConstants.JSON_JOB_DESCRIPTION_KEY);
                int pkg = row.getInt(TestConstants.JSON_PACKAGE_KEY);
                String applyLink = row.getString(TestConstants.JSON_APPLY_LINK_KEY);
                companies.add(new Company(id, companyName, jobRole, jobDescription, pkg, applyLink));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return companies;
    }

//  Method to extract questions from JSON data
    private static ArrayList<Question> retrieveQuestionsFromJson(JSONObject jsonResponse) {
        ArrayList<Question> questions = new ArrayList<>();
        try {
            JSONArray jsonArray = jsonResponse.getJSONArray(TestConstants.JSON_RESPONSE_DATA_KEY);
            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject row = (JSONObject) jsonArray.get(i);
                int id = row.getInt(TestConstants.JSON_ID_KEY);
                String question = row.getString(TestConstants.JSON_QUESTION_KEY);
                String optionA = row.getString(TestConstants.JSON_OPTION_A_KEY);
                String optionB = row.getString(TestConstants.JSON_OPTION_B_KEY);
                String optionC = row.getString(TestConstants.JSON_OPTION_C_KEY);
                String optionD = row.getString(TestConstants.JSON_OPTION_D_KEY);
                String answer = row.getString(TestConstants.JSON_ANSWER_KEY);
                questions.add(new Question(id, question, optionA, optionB, optionC, optionD, answer, ""));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return questions;
    }

//  Method to convert JSON data to HashMap
    private static HashMap<String, String> jsonToMap(JSONObject jObject) throws JSONException {

        HashMap<String, String> map = new HashMap<>();
        Iterator<?> keys = jObject.keys();

        while (keys.hasNext()) {
            String key = (String)keys.next();
            String value = String.valueOf(jObject.get(key));
            map.put(key, value);
        }
        return map;
    }
}
