package com.stackpointflow.stackapp;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelAnswer implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ModelAnswer createFromParcel(Parcel in) {
            return new ModelAnswer(in);
        }

        public ModelAnswer[] newArray(int size) {
            return new ModelAnswer[size];
        }
    };
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

    public void setTipsQuantity(String tipsQuantity) {
        this.tipsQuantity = tipsQuantity;
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

    public ModelAnswer(Parcel in) {
        this.answerID = in.readString();
        this.questionId = in.readString();
        this.userID = in.readString();
        this.username = in.readString();
        this.answerText = in.readString();
        this.tipsQuantity = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.answerID);
        dest.writeString(this.questionId);
        dest.writeString(this.userID);
        dest.writeString(this.username);
        dest.writeString(this.answerText);
        dest.writeString(this.tipsQuantity);
    }
}
