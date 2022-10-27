package com.sidhwanibhavesh.placementpredictionsystem;

// Class for questions for test
public class Question {
   private final int id;
   private final String question;
   private final String optionA;
   private final String optionB;
   private final String optionC;
   private final String optionD;
   private final String answer;
   private String userSelectedAns;

   public Question(int id, String question, String option_a, String option_b, String option_c, String option_d, String answer, String userSelectedAns) {
      this.id = id;
      this.question = question;
      this.optionA = option_a;
      this.optionB = option_b;
      this.optionC = option_c;
      this.optionD = option_d;
      this.answer = answer;
      this.userSelectedAns = userSelectedAns;
   }

// Helper Methods
   public String getQuestion() {
      return question;
   }

   public String getOptionA() {
      return optionA;
   }

   public String getOptionB() {
      return optionB;
   }

   public String getOptionC() {
      return optionC;
   }

   public String getOptionD() {
      return optionD;
   }

   public String getAnswer() {
      return answer;
   }

   public String getUserSelectedAns() {
      return userSelectedAns;
   }

   public void setUserSelectedAns(String userSelectedAns) {
      this.userSelectedAns = userSelectedAns;
   }
}
