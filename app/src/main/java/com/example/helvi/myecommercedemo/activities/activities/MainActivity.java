package com.example.helvi.myecommercedemo.activities.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.helvi.myecommercedemo.R;

public class MainActivity extends AppCompatActivity
{
    /**
     * This activity is a Splash screen.
     * After 4 sec it will automatically go to Next activity: Home Activity
     */
    private static int SPLASH_TIME_OUT=4000;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable()
        {
              public void run() {
                  Intent hoomeIntent = new Intent(MainActivity.this, HomeActivity.class);
                  startActivity(hoomeIntent);
                  finish();
              }
            },SPLASH_TIME_OUT);
        }
    }

