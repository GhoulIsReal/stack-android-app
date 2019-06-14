package com.stackpointflow.stackapp;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

    public String getMacAddress() {
        WifiManager manager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();

        return address;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        new RequestAsync().execute();
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final Button addQuestionButton = getView().findViewById(R.id.addQuestionButton);
        addQuestionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openAddQuestionActivity();
            }
        });
    }

    public class RequestAsync extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                //GET Request
                //return RequestHandler.sendGet("https://prodevsblog.com/android_get.php");

                // POST Request
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("mac_address", getMacAddress());
                //postDataParams.put("username", "newphone1");

                return RequestHandler.sendPost("http://192.168.0.195:3001/auth",postDataParams);
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            TextView usernameWelcomeTextView = getView().findViewById(R.id.usernameHomeTextView);
            TextView pointsTextView = getView().findViewById(R.id.pointsHomeTextView);

            if(s!=null){
                try {
                    JSONObject data = new JSONObject(s);
                    usernameWelcomeTextView.setText("Welcome, " + data.getString("username"));
                    pointsTextView.setText("Points: " + data.getString("points"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                    openLoginActivity();
            }
        }


    }

    public void openAddQuestionActivity() {
        Intent intent = new Intent(getContext(), AddQuestionActivity.class);
        startActivity(intent);
    }

    public void openLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }
}
