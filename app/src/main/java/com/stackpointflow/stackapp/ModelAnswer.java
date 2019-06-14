package com.stackpointflow.stackapp;

public class ModelAnswer {
    private String answerID, questionId, userID, username,  answerText, tipsQuantity;

    public ModelAnswer(String answerID, String questionId, String userID, String username, String answerText, String tipsQuantity) {
        this.answerID = answerID;
        this.questionId = questionId;
        this.userID = userID;
        this.username = username;
        this.answerText = answerText;
        this.tipsQuantity = tipsQuantity;
    }

    public String getTipsQuantity() {
        return tipsQuantity;
    }

    public String getAnswerID() {
        return answerID;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getAnswerText() {
        return answerText;
    }
}
