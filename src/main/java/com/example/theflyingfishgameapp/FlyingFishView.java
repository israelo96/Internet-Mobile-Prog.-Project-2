package com.example.theflyingfishgameapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Toast;
import android.widget.EditText;
import android.util.Log;


import static java.lang.Thread.sleep;

public class FlyingFishView extends View {
    private Bitmap fish[] = new Bitmap[2];
    private int fishX = 10;
    private int fishY;
    private int fishSpeed;
    private int canvasWidth, canvasHeight;
    private int yellowX, yellowY, yellowSpeed = 16;
    private Paint yellowPaint = new Paint();
    private int greenX, greenY, greenSpeed = 20;
    private Paint greenPaint = new Paint();
    private int redX, redY, redSpeed = 20;
    private Paint redPaint = new Paint();
    static {System.loadLibrary( "JNIexample");}

    public native int PiezoControl(int value);
    public native int LED(int valu);
    public native int CLED(int led_num,int val1,int val2,int val3 );
    //int PiezoData;
    //private EditText textbox;



    private int score, lifeCounterofFish;

    private Boolean touch = false;
    private Bitmap backgroundImage;
    private Paint scorePaint = new Paint();
    private Bitmap life[] = new Bitmap[2];

    public FlyingFishView(Context context)
    {
        super(context);

        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);

        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);
        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(30);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        fishY = 550;
        score = 0;
        lifeCounterofFish = 3;
        LED(lifeCounterofFish);
        CLED(5,0,0,0);
    }



    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);


        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(backgroundImage,0,0,null);
        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHeight - fish[0].getHeight() * 3;
        fishY = fishY + fishSpeed;

        if (fishY < minFishY)
        {
            fishY = minFishY;
        }
        if (fishY > maxFishY)
        {
            fishY = maxFishY;
        }
        fishSpeed = fishSpeed + 2;

        if (touch)
        {
            canvas.drawBitmap(fish[1], fishX, fishY, null);
            touch = false;
        }
        else {
            canvas.drawBitmap(fish[0], fishX, fishY, null);
        }

        yellowX = yellowX - yellowSpeed;
        if (hitBallChecker(yellowX, yellowY))
        {
            score = score + 10;
            yellowX = -100;
            PiezoControl(0x01);
            CLED(5,100,100,0);
            try {
                sleep(90);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            PiezoControl(0);
            CLED(5,0,0,0);
        }
        if (yellowX < 0)
        {
            yellowX = canvasWidth + 21;
            yellowY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(yellowX, yellowY, 25, yellowPaint);

        greenX = greenX - greenSpeed;
        if (hitBallChecker(greenX, greenY))
        {
            score = score + 20;
            greenX = -100;
            PiezoControl(0x031);
            CLED(5,0,100,20);
            try {
                sleep(90);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            PiezoControl(0);
            CLED(5,0,0,0);
        }
        if (greenX < 0)
        {
            greenX = canvasWidth + 21;
            greenY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(greenX, greenY, 25, greenPaint);


        redX = redX - redSpeed;
        if (hitBallChecker(redX, redY))
        {
            redX = -100;
            lifeCounterofFish--;
            PiezoControl(0x24);
            CLED(5,100,0,0);
            try {
                sleep(90);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            PiezoControl(0);
            CLED(5,0,0,0);
            
            if (lifeCounterofFish == 0)
            {
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Press any key to play again or press the button", Toast.LENGTH_SHORT).show();
                CLED(5,100,0,0);
                Intent gameOverIntent = new Intent(getContext(), GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOverIntent.putExtra("score", score);
                getContext().startActivity(gameOverIntent);

            }
        }
        if (redX < 0)
        {
            redX = canvasWidth + 21;
            redY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(redX, redY, 50, redPaint);

        canvas.drawText("Score = " + score, 25,60,scorePaint);

        for (int i=0; i<3; i++)
        {
            int x = (int) (250 + life[0].getWidth() * 1.5 * i);
            int y = 10;

            if (i < lifeCounterofFish)
            {
                canvas.drawBitmap(life[0], x, y, null);
                LED(lifeCounterofFish);
            }
            else
            {
                canvas.drawBitmap(life[1], x, y, null);
                LED(lifeCounterofFish);
            }
        }
    }

    public boolean hitBallChecker (int x, int y)
    {
        if (fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight()))
        {
            return true;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {

            touch = true;
            //Toast.makeText(getContext(), "Coñooo", Toast.LENGTH_SHORT).show();

            fishSpeed = -22;

        }
        return true;
    }


}
