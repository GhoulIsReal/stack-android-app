package com.stackpointflow.stackapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QuestionListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        new RequestAsync3().execute();
    }

    public class RequestAsync3 extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                //GET Request
                return RequestHandler.sendGet("http://10.0.103.133:3001/questions");
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if(s!=null){
                //success
                JSONArray questionsList = null;
                String title = null;
                try {
                    questionsList = new JSONArray(s);
                    title = questionsList.getJSONObject(0).getString("title");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();
            } else {
                //handle bad request
            }
        }


    }
}
