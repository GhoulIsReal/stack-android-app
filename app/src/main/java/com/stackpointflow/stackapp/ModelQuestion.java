package com.stackpointflow.stackapp;

public class ModelQuestion {
    private String question_id, question_title, question_text, username_text, time_posted_text;

    public ModelQuestion(String question_id, String question_title, String question_text, String username_text, String time_posted_text) {
        this.question_id = question_id;
        this.question_title = question_title;
        this.question_text = question_text;
        this.username_text = username_text;
        this.time_posted_text = time_posted_text;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public String getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public String getUsername_text() {
        return username_text;
    }

    public void setUsername_text(String username_text) {
        this.username_text = username_text;
    }

    public String getTime_posted_text() {
        return time_posted_text;
    }

    public void setTime_posted_text(String time_posted_text) {
        this.time_posted_text = time_posted_text;
    }
}
