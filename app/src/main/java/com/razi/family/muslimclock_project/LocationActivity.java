package com.razi.family.muslimclock_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class LocationActivity extends AppCompatActivity implements LocationListener {

    TextView okButton;
    TextView city1;
    Spinner country1;
    LinearLayout backgroundLocation;
    ImageView back2;
    Button GPS;
    TextView longLatLocation;

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    String lat;
    String provider;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        backgroundLocation = (LinearLayout) findViewById(R.id.background2);
        okButton = (TextView) findViewById(R.id.ok_button);
        city1 = (TextView) findViewById(R.id.city_value);
        country1 = (Spinner) findViewById(R.id.country_value);
        back2 = (ImageView) findViewById(R.id.back2);
        GPS = (Button) findViewById(R.id.gpsbutton);
        longLatLocation = (TextView) findViewById(R.id.longlatlocation);

        final Intent i = new Intent(this, MainActivity.class);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("mySettings", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("city", city1.getText().toString());
                editor.putString("country", country1.getSelectedItem().toString());
                editor.commit();
                startActivity(i);
            }
        });
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        final SharedPreferences sharedPreferences = getSharedPreferences("mySettings", MODE_PRIVATE);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                boolean darkmodeOn = sharedPreferences.getBoolean("darkModeOn", true);
                if (!darkmodeOn) {
                    backgroundLocation.setBackgroundColor(Color.WHITE);
                    okButton.setTextColor(Color.DKGRAY);
                    city1.setTextColor(Color.DKGRAY);
                }
            }
        });
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }
}