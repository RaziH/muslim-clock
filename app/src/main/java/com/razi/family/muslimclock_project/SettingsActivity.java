package com.razi.family.muslimclock_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    LinearLayout backgroundSettings;
    Switch screenOn;
    Switch hourtime;
    Switch darkmode;
    Switch turnonplugin;
    Switch keepon;
    Switch secs;
    Switch recite;
    Switch adhaan;
    TextView save;
    Spinner pageSpinner;
    Spinner textSpinner;
    ImageView back3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        backgroundSettings = (LinearLayout) findViewById(R.id.background4);
        hourtime = (Switch) findViewById(R.id.hour_time);
        darkmode = (Switch) findViewById(R.id.dark_mode);
        keepon = (Switch) findViewById(R.id.screen_on);
        secs = (Switch) findViewById(R.id.seconds);
        adhaan = (Switch) findViewById(R.id.adhaan);
        screenOn = (Switch) findViewById(R.id.screen_on);
        save = (TextView) findViewById(R.id.save_button);
        pageSpinner = (Spinner) findViewById(R.id.pagecolors);
        textSpinner = (Spinner) findViewById(R.id.textcolors);
        back3 = (ImageView) findViewById(R.id.back3);

        final SharedPreferences sharedPreferences = getSharedPreferences("mySettings", MODE_PRIVATE);
        boolean is24Hours = sharedPreferences.getBoolean("is24Hours", false);
        boolean secsDisabled = sharedPreferences.getBoolean("secsDisabled", true);
        boolean darkmodeOn = sharedPreferences.getBoolean("darkModeOn", true);
        boolean keepScreenOn = sharedPreferences.getBoolean("screenOn", true);
        boolean adhanIconDisabled = sharedPreferences.getBoolean("adhanIconDisabled", false);
        String wantedPageColor = sharedPreferences.getString("wantedPageColor", "pageColor");
        String wantedTextColor = sharedPreferences.getString("wantedTextColor", "textColor");

        ArrayList<String> pageColors = new ArrayList<>();
        pageColors.add("Dark Gray");
        pageColors.add("Red");
        pageColors.add("Blue");
        pageColors.add("White");
        pageColors.add("Green");
        pageColors.add("Purple");
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pageColors);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pageSpinner.setAdapter(arrayAdapter1);

        ArrayList<String> textColors = new ArrayList<>();
        textColors.add("White");
        textColors.add("Red");
        textColors.add("Blue");
        textColors.add("Dark Gray");
        textColors.add("Green");
        textColors.add("Purple");
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, textColors);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textSpinner.setAdapter(arrayAdapter2);

        screenOn.setChecked(keepScreenOn);
        screenOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                final Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences sharedPreferences = getSharedPreferences("mySettings", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("screenOn", isChecked);
                        editor.commit();
                    }
                });
            }
        });

        darkmode.setChecked(darkmodeOn);
        darkmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                final Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences sharedPreferences = getSharedPreferences("mySettings", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("darkModeOn", isChecked);
                        editor.commit();
                    }
                });
            }
        });

        hourtime.setChecked(is24Hours);
        hourtime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                final Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences sharedPreferences = getSharedPreferences("mySettings", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("is24Hours", isChecked);
                        editor.commit();
                    }
                });
            }
        });

        secs.setChecked(secsDisabled);
        secs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                final Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences sharedPreferences = getSharedPreferences("mySettings", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("secsDisabled", isChecked);
                        editor.commit();
                    }
                });
            }
        });

        adhaan.setChecked(adhanIconDisabled);
        adhaan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                final Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences sharedPreferences = getSharedPreferences("mySettings", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("adhanIconDisabled", isChecked);
                        editor.commit();
                    }
                });
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("mySettings", MODE_PRIVATE);
                Log.e("AddNewRecord", "getAll: " + sharedPreferences.getAll());
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        pageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences sharedPreferences = getSharedPreferences("mySettings", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("wantedPageColor", "pagecolor");
                        editor.commit();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        textSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences sharedPreferences = getSharedPreferences("mySettings", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("wantedTextColor", "Textcolor");
                        editor.commit();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                boolean darkmodeOn = sharedPreferences.getBoolean("darkModeOn", true);
                if (!darkmodeOn) {
                    backgroundSettings.setBackgroundColor(Color.WHITE);
                    screenOn.setTextColor(Color.DKGRAY);
                    hourtime.setTextColor(Color.DKGRAY);
                    darkmode.setTextColor(Color.DKGRAY);
                    turnonplugin.setTextColor(Color.DKGRAY);
                    keepon.setTextColor(Color.DKGRAY);
                    secs.setTextColor(Color.DKGRAY);
                    recite.setTextColor(Color.DKGRAY);
                    adhaan.setTextColor(Color.DKGRAY);
                    save.setTextColor(Color.DKGRAY);
                }
            }
        });
    }
}