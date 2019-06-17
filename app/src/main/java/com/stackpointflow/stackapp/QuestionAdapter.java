package com.stackpointflow.stackapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {


    private Context mContext;
    private ArrayList<ModelQuestion> sList;
    private String questionTitleForAnswersActivity = "";
    private String questionBodyForAnswersActivity = "";
    private String questionIDForAnswersActivity = "";
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

        viewHolder.currentQuestion = sList.get(i); //for set up of onclick listeners

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

        public View view;
        public ModelQuestion currentQuestion;

        TextView question_title, question_text, username_text, time_posted_text;
        public ViewHolder(final View itemView) {
            super(itemView);

            question_title = itemView.findViewById(R.id.questionTitle);
            question_text = itemView.findViewById(R.id.questionText);
            username_text = itemView.findViewById(R.id.usernameText);
            time_posted_text = itemView.findViewById(R.id.postedTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    String id = sList.get(getAdapterPosition()).getQuestion_id();
                    questionIDForAnswersActivity = sList.get(getAdapterPosition()).getQuestion_id();
                    questionTitleForAnswersActivity = sList.get(getAdapterPosition()).getQuestion_title();
                    questionBodyForAnswersActivity = sList.get(getAdapterPosition()).getQuestion_text();
                    new RequestAsync4().execute(id);
                }
            });
        }
    }

    public class RequestAsync4 extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                //GET Request
                return RequestHandler.sendGet("http://192.168.0.195:3001/questions/" + strings[0] + "/answers");

            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {

            if(s!=null){
                Log.e("lo", s);
                JSONArray answersList1 = null;
                ArrayList<ModelAnswer> answersListForIntent = new ArrayList<>();
                try {
                    answersList1 = new JSONArray(s);
                    for (int i = 0; i < answersList1.length(); i++) {
                        JSONObject answer = answersList1.getJSONObject(i);
                        answersListForIntent.add(new ModelAnswer(answer.getString("id"),
                                                                answer.getString("question_id"),
                                                                answer.getString("user_id"),
                                                                answer.getString("username"),
                                                                answer.getString("answer_text"),
                                                                answer.getString("tips_count")));
                    }
                    openQuestionAnswersActivity(answersListForIntent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

            }
        }


    }

    public void openQuestionAnswersActivity(ArrayList<ModelAnswer> answersList) {
        Intent intent = new Intent(mContext, QuestionAnswerActivity.class);
        intent.putExtra("DATA_LIST", answersList);
        intent.putExtra("Title", questionTitleForAnswersActivity);
        intent.putExtra("Body", questionBodyForAnswersActivity);
        intent.putExtra("Question_id", questionIDForAnswersActivity);
        mContext.startActivity(intent);
    }
}
