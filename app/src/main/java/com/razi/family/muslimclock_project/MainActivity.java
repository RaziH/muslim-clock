package com.razi.family.muslimclock_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity {

    String URL = "https://openweathermap.org/data/2.5/weather?q=";
    String apiKey = "AIzaSyA5gJIOBc556QG9AtyahuJiFVZ9Ag2IVco";
    TextView seconds;
    TextView ampm;
    TextView time;
    TextView location;
    TextView date;
    TextView day;
    TextView stat;
    TextView temp;
    ImageView settingsIcon;
    ImageView locationIcon;
    ImageView adhanIcon;
    ImageView refreshIcon;
    ImageView alarmIcon;
    LinearLayout backgroundMain;

    DateFormat dfSeconds;
    DateFormat dfAMPM;
    DateFormat dfTime12Hours;
    DateFormat dfTime24Hours;
    DateFormat dfDate;
    DateFormat dfDay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        location = (TextView) findViewById(R.id.location1);
        seconds = (TextView) findViewById(R.id.seconds);
        ampm = (TextView) findViewById(R.id.AMPM);
        time = (TextView) findViewById(R.id.time);
        temp = (TextView) findViewById(R.id.temp);
        date = (TextView) findViewById(R.id.date);
        stat = (TextView) findViewById(R.id.stat);
        adhanIcon = (ImageView) findViewById(R.id.azan);
        refreshIcon = (ImageView) findViewById(R.id.refresh);
        settingsIcon = (ImageView) findViewById(R.id.settings);
        locationIcon = (ImageView) findViewById(R.id.location);
        alarmIcon = (ImageView) findViewById(R.id.alarm);
        backgroundMain = (LinearLayout) findViewById(R.id.background);
        day = (TextView) findViewById(R.id.day);

        dfSeconds = new SimpleDateFormat("ss");
        dfAMPM = new SimpleDateFormat("a");
        dfTime12Hours = new SimpleDateFormat("hh:mm");
        dfTime24Hours = new SimpleDateFormat("HH:mm");
        dfDate = new SimpleDateFormat("MMM / dd / yyyy");
        dfDay = new SimpleDateFormat("EEEE");

        final SharedPreferences sharedPreferences = getSharedPreferences("mySettings", MODE_PRIVATE);
        Log.d("AddNewRecord", "getAll: " + sharedPreferences.getAll());
        String city = sharedPreferences.getString("city", "");
        String country = sharedPreferences.getString("country", "");

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL + city + "," + country + "&appid=" + "b6907d289e10d714a6e88b30761fae22", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            temp.setText(response.getJSONObject("main").getString("temp").toString().substring(0, 2) + "°");
                            stat.setText(response.getJSONArray("weather").getJSONObject(0).getString("description"));
                        }catch (JSONException e) {
                            Log.e("onErrorResponse", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error == null) Log.e("onErrorResponse", "Null Message");
                        Log.e("onErrorResponse", error.toString());
                    }
                }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                boolean is24Hours = sharedPreferences.getBoolean("is24Hours", false);
                Date dateobj = new Date();
                if (is24Hours) {
                    time.setText(dfTime24Hours.format(dateobj));
                } else {
                    ampm.setText(dfAMPM.format(dateobj));
                    time.setText(dfTime12Hours.format(dateobj));
                }
                seconds.setText(dfSeconds.format(dateobj));
                date.setText(dfDate.format(dateobj));
                day.setText(dfDay.format(dateobj));
                handler.postDelayed(this, 1000);
            }
        });

        handler.post(new Runnable() {
            @Override
            public void run() {
                boolean keepScreenOn = sharedPreferences.getBoolean("screenOn", true);
                if (keepScreenOn) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
            }
        });

        handler.post(new Runnable() {
            @Override
            public void run() {
                boolean secsDisabled = sharedPreferences.getBoolean("secsDisabled", true);
                if (secsDisabled) {
                    seconds.setVisibility(View.VISIBLE);
                } else {
                    seconds.setVisibility(View.GONE);
                }
            }
        });

        handler.post(new Runnable() {
            @Override
            public void run() {
                boolean darkmodeOn = sharedPreferences.getBoolean("darkModeOn", true);
                if (!darkmodeOn) {
                    backgroundMain.setBackgroundColor(Color.WHITE);
                    location.setTextColor(Color.DKGRAY);
                    seconds.setTextColor(Color.DKGRAY);
                    ampm.setTextColor(Color.DKGRAY);
                    time.setTextColor(Color.DKGRAY);
                    date.setTextColor(Color.DKGRAY);
                    adhanIcon.setBackgroundColor(Color.DKGRAY);
                    settingsIcon.setBackgroundColor(Color.DKGRAY);
                    locationIcon.setBackgroundColor(Color.DKGRAY);
                }
            }
        });

        handler.post(new Runnable() {
            @Override
            public void run() {
                boolean adhanIconDisabled = sharedPreferences.getBoolean("adhanIconDisabled", false);
                if (adhanIconDisabled) {
                    adhanIcon.setVisibility(View.VISIBLE);
                } else {
                    adhanIcon.setVisibility(View.GONE);
                }
            }
        });

        alarmIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AlarmActivity.class);
                startActivity(intent);
            }
        });
        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
        locationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(intent);
            }
        });
        adhanIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AdhanActivity.class);
                startActivity(intent);
            }
        });
        refreshIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
                Toast.makeText(getApplicationContext(),"Refreshed successfully!", Toast.LENGTH_LONG).show();
            }
        });
        location.setText(city + ",  " + country);
    }
}