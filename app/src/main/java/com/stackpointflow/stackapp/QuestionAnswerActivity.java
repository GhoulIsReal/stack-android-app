package com.stackpointflow.stackapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuestionAnswerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ModelAnswer> answersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer);

        answersList = getIntent().getParcelableArrayListExtra("DATA_LIST");
        String title = getIntent().getStringExtra("Title");
        String textBody = getIntent().getStringExtra("Body");
        final String questionID = getIntent().getStringExtra("Question_id");

        if(answersList.isEmpty()) {
            TextView noAnswersText = findViewById(R.id.no_answers_text);
            noAnswersText.setVisibility(View.VISIBLE);
        }

        TextView titleHolder = findViewById(R.id.answers_title);
        TextView bodyHolder = findViewById(R.id.answers_questionBody);

        titleHolder.setText(title);
        bodyHolder.setText(textBody);

        recyclerView = findViewById(R.id.rw_for_answers);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager rvLayoutManager = layoutManager;

        recyclerView.setLayoutManager(rvLayoutManager);

        AnswersAdapter aa = new AnswersAdapter(this, answersList);
        recyclerView.setAdapter(aa);

        Button backButton = findViewById(R.id.back_to_dashboard);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                finish();
            }
        });

        Button addQuestionButton = findViewById(R.id.send_answer_button);
        addQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText answer_editText = findViewById(R.id.answer_textEdit);
                String answer = answer_editText.getText().toString();
                new RequestAsync7().execute(questionID, BottomNavigationActivity.staticUserID, answer);
                answer_editText.setText("");
            }
        });
    }


    public class RequestAsync7 extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                //GET Request
                //return RequestHandler.sendGet("https://prodevsblog.com/android_get.php");

                // POST Request
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("question_id", strings[0]);
                postDataParams.put("user_id", strings[1]);
                postDataParams.put("answer_text", strings[2]);


                return RequestHandler.sendPost("http://192.168.0.195:3001/answer",postDataParams);
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {

            if(s!=null){
                //success
                try {
                    JSONObject jo = new JSONObject(s);
                    ModelAnswer newAnswer = new ModelAnswer(jo.getString("id"),
                                                            jo.getString("question_id"),
                                                            jo.getString("user_id"),
                                                            jo.getString("username"),
                                                            jo.getString("answer_text"),
                                                            jo.getString("tips_count"));
                    answersList.add(newAnswer);
                    recyclerView.getAdapter().notifyDataSetChanged();

                    TextView noAnswersText = findViewById(R.id.no_answers_text);
                    noAnswersText.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(), "Your answer has been added. Thank you!", Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                //handle bad request
            }
        }


    }
}
