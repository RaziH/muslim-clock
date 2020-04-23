package com.razi.family.muslimclock_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class AdhanActivity extends AppCompatActivity {
    TextView location1;
    TextView fajr;
    TextView sunrise;
    TextView dhuhr;
    LinearLayout backgroundAdhan;
    TextView maghrib;
    TextView midnight;
    String URL = "http://api.aladhan.com/v1/calendarByCity";
    ImageView back1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adhan);
//mwahahahaaha
        backgroundAdhan = (LinearLayout) findViewById(R.id.background5);
        location1 = (TextView) findViewById(R.id.location);
        fajr = (TextView) findViewById(R.id.fajr);
        sunrise = (TextView) findViewById(R.id.sunrise);
        dhuhr = (TextView) findViewById(R.id.dhuhr);
        maghrib = (TextView) findViewById(R.id.maghrib);
        midnight = (TextView) findViewById(R.id.midnight);
        back1 = (ImageView) findViewById(R.id.back1);




        //i see you
        // im mesing the <code
        final SharedPreferences sharedPreferences = getSharedPreferences("mySettings", MODE_PRIVATE);
        String city = sharedPreferences.getString("city", "");
        String country = sharedPreferences.getString("country", "");

        RequestQueue queue = Volley.newRequestQueue(AdhanActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL + "?city=" + city + "&country=" + country, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject jsonObject = response.getJSONArray("data").getJSONObject(0).getJSONObject("timings");
                            fajr.setText("Fajr: " + jsonObject.getString("Fajr").toString().substring(0, 5));
                            sunrise.setText("Sunrise: " + jsonObject.getString("Sunrise").toString().substring(0, 5));
                            dhuhr.setText("Dhuhr: " + jsonObject.getString("Dhuhr").toString().substring(0, 5));
                            maghrib.setText("Maghrib: " + jsonObject.getString("Maghrib").toString().substring(0, 5));
                            midnight.setText("Midnight: " + jsonObject.getString("Midnight").toString().substring(0, 5));

                        } catch (JSONException e) {
                            Log.e("onErrorResponse", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "PLEASE ENTER CITY AND COUNTRY IN LOCATION", Toast.LENGTH_LONG).show();
                        if (error == null) Log.e("onErrorResponse", "Null Message");
                        Log.e("onErrorResponse", error.toString());
                    }
                }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

        location1.setText(city + " ,  " + country);

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdhanActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                boolean darkmodeOn = sharedPreferences.getBoolean("darkModeOn", true);
                if (!darkmodeOn) {
                    backgroundAdhan.setBackgroundColor(Color.WHITE);
                    location1.setTextColor(Color.DKGRAY);
                    fajr.setTextColor(Color.DKGRAY);
                    sunrise.setTextColor(Color.DKGRAY);
                    dhuhr.setTextColor(Color.DKGRAY);
                    maghrib.setTextColor(Color.DKGRAY);
                }
            }
        });
    }
}
