package com.sidhwanibhavesh.placementpredictionsystem;

// Class for constants
final public class TestConstants {
   // URLs for sending POST request
   public static final String BASE_URL = "https://placement-prediction-system.herokuapp.com/";
   public static final String USER_AUTH_PATH = "user-authentication.php";
   public static final String USER_REGISTRATION_PATH = "user-registration.php";
   public static final String USER_UPDATE_PATH = "user-update.php";
   public static final String COMPANY_FETCH_PHP = "company-fetch.php";
   public static final String ADD_COMPANY_PATH = "company-add.php";
   public static final String QUESTIONS_FETCH_PATH = "questions-fetch.php";

   // URL for python POST requests
   public static final String PYTHON_BASE_URL = "https://placement-prediction-system-py.herokuapp.com/";
   public static final String PREDICT_PROBABILITY_PATH = "predict";
   public static final String RESUME_PATH = "resume";

   // BASE_URL for testing
   public static final String ADD_TEST_BASE_URL = "http://192.168.0.162:8000/";
   public static final String FETCH_TEST_BASE_URL = "http://192.168.0.162:8001/";

   // Keys for user JSON data
   public static final String JSON_USERNAME_KEY = "username";
   public static final String JSON_PASSWORD_KEY = "password";
   public static final String JSON_TPO_KEY = "tpo";
   public static final String JSON_GENDER_KEY = "gender";
   public static final String JSON_CGPA_KEY = "cgpa";
   public static final String JSON_WORKEX_KEY = "workex";
   public static final String JSON_QUANTS_KEY = "quants";
   public static final String JSON_LOGICAL_REASONING_KEY = "logical_reasoning";
   public static final String JSON_VERBAL_KEY = "verbal";
   public static final String JSON_PROGRAMMING_KEY = "programming";
   public static final String JSON_TEST_TYPE_KEY = "test_type";

   // Keys for general JSON data
   public static final String JSON_RESPONSE_CODE_KEY = "response_code";
   public static final String JSON_RESPONSE_DATA_KEY = "data";
   public static final String JSON_DB_ERROR_MSG_KEY = "error_msg";
   public static final String JSON_RESPONSE_VALUE_KEY = "value";
   public static final String JSON_ID_KEY = "_id";

   // Keys for company JSON data
   public static final String JSON_COMPANY_NAME_KEY = "company_name";
   public static final String JSON_JOB_ROLE_KEY = "job_role";
   public static final String JSON_JOB_DESCRIPTION_KEY = "job_description";
   public static final String JSON_PACKAGE_KEY = "package";
   public static final String JSON_APPLY_LINK_KEY = "apply_link";

   // Keys for question JSON data
   public static final String JSON_QUESTION_KEY = "question";
   public static final String JSON_OPTION_A_KEY = "option_a";
   public static final String JSON_OPTION_B_KEY = "option_b";
   public static final String JSON_OPTION_C_KEY = "option_c";
   public static final String JSON_OPTION_D_KEY = "option_d";
   public static final String JSON_ANSWER_KEY = "answer";

   // Keys for prediction JSON data
   public static final String JSON_PREDICT_GENDER_KEY = "gender";
   public static final String JSON_PREDICT_CGPA_KEY = "degree_p";
   public static final String JSON_PREDICT_WORKEX_KEY = "workex";
   public static final String JSON_PREDICT_QUANTS_KEY = "quants";
   public static final String JSON_PREDICT_LOGICAL_REASONING_KEY = "logical_reasoning";
   public static final String JSON_PREDICT_VERBAL_KEY = "verbal";
   public static final String JSON_PREDICT_PROGRAMMING_KEY = "programming";

   // Keys for resume checking JSON data
   public static final String JSON_CHECKER_RESUME_KEY = "resume";

   // Keys to access Intent Extras
   public static final String INTENT_EXTRA_SELECTED_TOPIC_KEY = "SelectedTopic";
   public static final String INTENT_EXTRA_QUANTS_KEY = "Quantitative Test";
   public static final String INTENT_EXTRA_LOGICAL_KEY = "Logical Reasoning Test";
   public static final String INTENT_EXTRA_VERBAL_KEY = "Verbal Test";
   public static final String INTENT_EXTRA_TECHNICAL_KEY = "Technical Test";
   public static final String INTENT_EXTRA_CORRECT_ANSWER_KEY = "correct_answer";
   public static final String INTENT_EXTRA_INCORRECT_ANSWER_KEY = "incorrect_answer";
   public static final String INTENT_EXTRA_VALUE_KEY = "value";
   public static final String INTENT_EXTRA_RESULT_TYPE_KEY = "result_type";
   public static final String INTENT_EXTRA_PREDICTION_KEY = "Prediction: ";
   public static final String INTENT_EXTRA_RESUME_SIMILARITY_KEY = "Resume Similarity Score: ";

   // Response codes for authentication process
   public static final int AUTH_SUCCESS_CODE = 100;
   public static final int AUTH_INVALID_CREDENTIALS_CODE = 101;
   public static final int AUTH_DB_CONN_ERROR_CODE = 102;

   // Response codes for registration process
   public static final int REGISTRATION_SUCCESS_CODE = 200;
   public static final int REGISTRATION_USER_EXISTS_CODE = 201;
   public static final int REGISTRATION_DB_CONN_ERROR_CODE = 202;

   // Response codes for user updates
   public static final int UPDATE_SUCCESS_CODE = 600;
   public static final int UPDATE_USER_EXISTS_CODE = 601;
   public static final int UPDATE_DB_CONN_ERROR_CODE = 602;

   // Response codes for company fetch process
   public static final int FETCH_COMPANY_SUCCESS_CODE = 300;
   public static final int FETCH_COMPANY_EMPTY_CODE = 301;
   public static final int FETCH_COMPANY_DB_CONN_ERROR_CODE = 302;

   // Response codes for company add process
   public static final int ADD_COMPANY_SUCCESS_CODE = 400;
   public static final int ADD_COMPANY_EXISTS_CODE = 401;
   public static final int ADD_COMPANY_DB_CONN_ERROR_CODE = 402;

   // Response codes for questions fetch
   public static final int FETCH_QUESTION_SUCCESS_CODE = 500;
   public static final int FETCH_QUESTION_EMPTY_CODE = 501;
   public static final int FETCH_QUESTION_INCORRECT_TEST_CODE = 502;
   public static final int FETCH_QUESTION_DB_CONN_ERROR_CODE = 503;

   // Test codes to fetch questions
   public static final int QUANTS_TEST_CODE = 1;
   public static final int LOGICAL_TEST_CODE = 2;
   public static final int VERBAL_TEST_CODE = 3;
   public static final int PROGRAMMING_TEST_CODE = 4;

   // Logged in user
   public static User LOGGED_IN_USER;
}
