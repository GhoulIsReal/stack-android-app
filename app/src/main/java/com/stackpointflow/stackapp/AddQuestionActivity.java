package com.stackpointflow.stackapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddQuestionActivity extends AppCompatActivity {

    private String questionTitleForAnswersActivity = "";
    private String questionBodyForAnswersActivity = "";
    private String questionIDForAnswersActivity = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        final Button button3 = findViewById(R.id.addQuestionButton);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new RequestAsync2().execute();
            }
        });

        final Button back_to_home = findViewById(R.id.back_to_home);
        setActionFinishOnButton(back_to_home);

        final Button cancel_add_question = findViewById(R.id.cancelAddQuestionButton);
        setActionFinishOnButton(cancel_add_question);

    }

    public void setActionFinishOnButton(Button b) {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public class RequestAsync2 extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                //GET Request
                //return RequestHandler.sendGet("https://prodevsblog.com/android_get.php");

                // POST Request
                EditText et1 = (EditText) findViewById(R.id.titleText);
                EditText et2 = (EditText) findViewById(R.id.questionBodyText);

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("user_id", BottomNavigationActivity.staticUserID);
                postDataParams.put("title", et1.getText());
                postDataParams.put("question_text", et2.getText());

                return RequestHandler.sendPost("http://192.168.0.195:3001/add-question",postDataParams);
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if(s!=null){
                //success
                JSONArray ja = null;
                try {
                    ja = new JSONArray(s);
                    JSONObject question =ja.getJSONObject(0);
                    questionTitleForAnswersActivity = question.getString("title");
                    questionBodyForAnswersActivity = question.getString("question_text");
                    questionIDForAnswersActivity = question.getString("question_primary_id");

                    openQuestionAnswersActivity(new ArrayList<ModelAnswer>());
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                //handle bad request
                Toast.makeText(getApplicationContext(), "Please, fill in all fields!", Toast.LENGTH_LONG).show();
            }
        }


    }

    public void openQuestionAnswersActivity(ArrayList<ModelAnswer> answersList) {
        Intent intent = new Intent(this, QuestionAnswerActivity.class);
        intent.putExtra("DATA_LIST", answersList);
        intent.putExtra("Title", questionTitleForAnswersActivity);
        intent.putExtra("Body", questionBodyForAnswersActivity);
        intent.putExtra("Question_id", questionIDForAnswersActivity);
        startActivity(intent);
    }
}
