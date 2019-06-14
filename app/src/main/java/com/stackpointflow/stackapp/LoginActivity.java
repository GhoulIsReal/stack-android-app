package com.stackpointflow.stackapp;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    public String getMacAddress() {
        WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();

        return address;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button button2 = findViewById(R.id.button1);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new RequestAsync1().execute();
            }
        });
    }

    public class RequestAsync1 extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                //GET Request
                //return RequestHandler.sendGet("https://prodevsblog.com/android_get.php");

                // POST Request
                EditText et = (EditText) findViewById(R.id.editText1);
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("mac_address",  getMacAddress());
                postDataParams.put("username", et.getText());

                return RequestHandler.sendPost("http://10.0.103.133:3001/register",postDataParams);
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            TextView t2 = (TextView) findViewById(R.id.tv2);

            if(s!=null){
                //success
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            } else {
                //handle bad request
            }
        }


    }
}
