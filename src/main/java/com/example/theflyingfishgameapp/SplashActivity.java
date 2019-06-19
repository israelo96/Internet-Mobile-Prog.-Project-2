package com.example.theflyingfishgameapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    static {System.loadLibrary( "JNIexample");}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


       Thread thread = new Thread()
       {
           @Override
           public void run()
           {
               try
               {
                   sleep(5000);
               }
               catch (Exception e)
               {
                   e.printStackTrace();
               }
               finally
               {
                   Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                   startActivity(mainIntent);
               }
           }

       };
       thread.start();
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        finish();
    }
}