package com.stackpointflow.stackapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {


    private Context mContext;
    private ArrayList<ModelQuestion> sList;
    QuestionAdapter(Context context, ArrayList<ModelQuestion> list) {
        mContext = context;
        sList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View view = layoutInflater.inflate(R.layout.rv_question_item, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ModelQuestion questionItem = sList.get(i);

        TextView question_title, question_text, username_text, time_posted_text;
        question_title = viewHolder.question_title;
        question_text = viewHolder.question_text;
        username_text = viewHolder.username_text;
        time_posted_text = viewHolder.time_posted_text;

        question_title.setText(questionItem.getQuestion_title());
        question_text.setText(questionItem.getQuestion_text());
        username_text.setText(questionItem.getUsername_text());
        time_posted_text.setText(questionItem.getTime_posted_text());
    }

    @Override
    public int getItemCount() {
        return sList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView question_title, question_text, username_text, time_posted_text;
        public ViewHolder(View itemView) {
            super(itemView);

            question_title = itemView.findViewById(R.id.questionTitle);
            question_text = itemView.findViewById(R.id.questionText);
            username_text = itemView.findViewById(R.id.usernameText);
            time_posted_text = itemView.findViewById(R.id.postedTime);
        }
    }
}
