package com.example.theflyingfishgameapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import static java.lang.Thread.sleep;

import static java.lang.Thread.sleep;


public class GameOverActivity extends AppCompatActivity
{
    private Button StartGameAgain;
    private TextView DisplayScore;
    private String score;
    private EditText textbox;
    //private Boolean state = false;
    static {System.loadLibrary( "JNIexample");}
    public native int CLED(int led_num,int val1,int val2,int val3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        score = getIntent().getExtras().get("score").toString();

        StartGameAgain = (Button) findViewById(R.id.play_again_btn);
        DisplayScore = (TextView) findViewById(R.id.displayScore);
        textbox =  (EditText) findViewById(R.id.editText);


        textbox.setOnKeyListener(new OnKeyListener());



        StartGameAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mainIntent = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(mainIntent);

            }
        });

        DisplayScore.setText("SCORE = " + score);




    }

    class OnKeyListener implements View.OnKeyListener{


        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN)
            {
                Log.i("TAG","Tecla presionada: "+keyCode);
                //state = true;
                Intent mainIntent = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(mainIntent);


            }
            return true;
        }
    }

}
