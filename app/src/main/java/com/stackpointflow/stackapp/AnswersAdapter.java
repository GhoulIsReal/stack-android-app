package com.stackpointflow.stackapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ModelAnswer> sList;
    AnswersAdapter(Context context, ArrayList<ModelAnswer> list) {
        mContext = context;
        sList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View view = layoutInflater.inflate(R.layout.rv_answer_item, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ModelAnswer answerItem = sList.get(i);

        viewHolder.username.setText(answerItem.getUsername());
        viewHolder.answer_body_text.setText(answerItem.getAnswerText());
        viewHolder.tips_count.setText("x" + answerItem.getTipsQuantity());
    }

    @Override
    public int getItemCount() {
        return sList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView username, answer_body_text, tips_count;
        public ViewHolder(final View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.usernameInAnswer);
            answer_body_text = itemView.findViewById(R.id.questionBodyInAnswerCard);
            tips_count = itemView.findViewById(R.id.tipsCounter);

        }
    }
}
