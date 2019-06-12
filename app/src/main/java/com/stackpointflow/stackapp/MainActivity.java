package com.stackpointflow.stackapp;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.widget.Toast;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public String getMacAddress() {
        WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();

        return address;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //openQuestionListActivity();
                openBottomNavigationActivity();
            }
        });
        new RequestAsync().execute();
    }

    public class RequestAsync extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                //GET Request
                //return RequestHandler.sendGet("https://prodevsblog.com/android_get.php");

                // POST Request
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("mac_address", getMacAddress() + "5");
                //postDataParams.put("username", "newphone1");

                return RequestHandler.sendPost("http://192.168.0.195:3001/auth",postDataParams);
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            TextView t1 = (TextView) findViewById(R.id.mac_text);

            if(s!=null){
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                t1.setText(s);
            } else {
                //t1.setText("Need to set username");
                //openLoginActivity();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                t1.setText(s);
            }
        }


    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openAddQuestionActivity() {
        Intent intent = new Intent(this, AddQuestionActivity.class);
        startActivity(intent);
    }

    public void openQuestionListActivity() {
        Intent intent = new Intent(this, QuestionListActivity.class);
        startActivity(intent);
    }

    public void openBottomNavigationActivity() {
        Intent intent = new Intent(this, BottomNavigationActivity.class);
        startActivity(intent);
    }
}
