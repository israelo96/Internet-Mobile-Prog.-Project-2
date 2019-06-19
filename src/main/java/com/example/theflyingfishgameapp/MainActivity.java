package com.example.theflyingfishgameapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
//APP_PROJECT_PATH:= $(call my-dir)
//NDK_PROJECT_PATH:=$(call my-dir)
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
{
    private FlyingFishView gameView;
    private Handler handler = new Handler();
    private final static long Interval = 30;
    //private EditText textbox;
    //public native String GetMyString();
    static {System.loadLibrary( "JNIexample");}

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //textbox.setOnKeyListener(onKeyDown(int keyCode,KeyEvent event) );
        gameView = new FlyingFishView(this);
        setContentView(gameView);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run()
            {
                handler.post(new Runnable() {
                    @Override
                    public void run()
                    {
                        gameView.invalidate();

                    }
                });

            }
        }, 0, Interval);

    }
}
