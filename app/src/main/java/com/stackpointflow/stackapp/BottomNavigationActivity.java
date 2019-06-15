package com.stackpointflow.stackapp;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

public class BottomNavigationActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        new RequestAsync().execute();
    }

    private boolean loadFragment(Fragment fragment) {
        if(fragment !=null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

            return true;
        }
        return false;
    }

    private boolean loadFragment(Fragment fragment, Bundle bundle) {
        if(fragment !=null) {
            fragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                fragment.setArguments(bundleForHomeFragment);
                break;

            case R.id.navigation_dashboard:
                fragment = new DashboardFragment();
                break;

            case R.id.navigation_notifications: //change to settings
                fragment = new SettingsFragment();
                break;
        }

        return loadFragment(fragment);
    }

    public static String staticUserID;
    private String userID, username, points;
    private Bundle bundleForHomeFragment;

    public String getMacAddress() {
        WifiManager manager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();

        return address;
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

            if(s!=null){
                try {
                    JSONObject data = new JSONObject(s);
                    userID = data.getString("id");
                    username = data.getString("username");
                    points = data.getString("points");
                    staticUserID = userID;

                    bundleForHomeFragment = new Bundle();
                    bundleForHomeFragment.putString("id", userID);
                    bundleForHomeFragment.putString("username", username);
                    bundleForHomeFragment.putString("points", points);

                    loadFragment(new HomeFragment(), bundleForHomeFragment);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                openLoginActivity();
            }
        }


    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
