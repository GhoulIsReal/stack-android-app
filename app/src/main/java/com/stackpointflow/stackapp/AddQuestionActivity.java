package com.stackpointflow.stackapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;

public class AddQuestionActivity extends AppCompatActivity {

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
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            } else {
                //handle bad request
            }
        }


    }
}
