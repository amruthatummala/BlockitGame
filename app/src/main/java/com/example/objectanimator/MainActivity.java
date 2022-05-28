package com.example.objectanimator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    //Block b;
    ObjectAnimator objectAnimator;
    //Path p;
    final int dx = 15;
    final int dy = 15;
    Timer timer = new Timer();
    //boolean tiltedRight = false;
    float[] tiltVals = new float[2];
    //ArrayList<Block> blockPool;

    Block b;

    Gyroscope gyroscope;

    SharedPreferences shref;

    Handler handler = new Handler();

    float screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    float screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    double tempScreenHeight = Resources.getSystem().getDisplayMetrics().heightPixels; // platform height is ~500px 520
    //float platformHeight = screenHeight - 500;

    float platformHeight = (float) tempScreenHeight - (60 * Resources.getSystem().getDisplayMetrics().density); // 1669.0

//    DisplayMetrics metrics = getWindowManager().getDefaultDisplay().getMetrics(metrics);
//    float logicalDensity = metrics.density;
//    int px = (int) (dp * logicalDensity + 0.5);

    float x = screenWidth / 2;
    float y = screenHeight / 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        //platformHeight = platformHeight - statusBarHeight;
        //float platformHeight = screenHeight - statusBarHeight;
        Block tempB= new Block(ContextCompat.getDrawable(this, R.drawable.rectangle),(int) screenWidth , (int) (60 * getResources().getDisplayMetrics().density), this, screenHeight, screenWidth);
        tempB.setX(0);

        Block platform= new Block(ContextCompat.getDrawable(this, R.drawable.rectangle),(int) screenWidth , (int) (60 * getResources().getDisplayMetrics().density), this, screenHeight, screenWidth);
        platform.setX(0);
        platform.setY(platformHeight);


        GameInfo temp = new GameInfo();
        ArrayList<Block> placedBlocks = new ArrayList<>();

        ArrayList<Block> blockPool = temp.getBlocks();

        blockPool.add(0, tempB);
        b = blockPool.remove(0);
        //b=tempB;
        b.moveDown(900);


        //Block tempB = b;
        //blockPool.add(1, tempB);
        placedBlocks.add(platform);

        System.out.println("starting x is " + x); //540.0
        System.out.println("starting y is " + y); // 1010.0
        System.out.println("platform y is " + platformHeight);
        System.out.println("screen height is " + screenHeight);



        gyroscope = new Gyroscope(this);


        //imageView = b.getImageView();
        ConstraintLayout c = (ConstraintLayout) findViewById(R.id.main);
        c.addView(b.getImageView());
        c.addView(platform.getImageView());
        //c.removeView(b.getImageView());
        //c.addView(b1.getImageView());
        c.setConstraintSet(new ConstraintSet());

        gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            public void onRotation(float rx, float ry, float rz) {
                if (Math.abs(rx) >= 0.3) {
                    tiltVals[0] = rx;
                }

                if (Math.abs(ry) >= 0.3) {
                    tiltVals[1] = ry;
                }


            }
        });


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        boolean checkPlatform = true;
                        if (b.getX() > screenWidth || b.getX() < 0 || b.getY() < 0) { // || b.getY() > platform.getY()+dy
                            b.reset();
                            System.out.println("back to beginning");
                        }
                        int size = placedBlocks.size();
                        System.out.println("size of placedBlocks is " + size);
                        System.out.println("placedBlocks: " + placedBlocks);
                        for (int i = 0; i < size; i++) {
                            Block b1 = placedBlocks.get(i);
                            if(b.stackBlock(b1, dx, dy)) {
                                System.out.println("successfully stacked block");
//                                cancel();
                                placedBlocks.add(0, b);
                                //blockPool.remove(0);
                                if (blockPool.size() > 0) {
                                    b = blockPool.remove(0);
                                    c.addView(b.getImageView());
                                } else {
                                    cancel();
                                }
                                checkPlatform = false;
                                //cancel();
                                break;

                            }
                        }
                        System.out.println("x is " + b.getX());
                        System.out.println("y is " +b.getY());
                        if (checkPlatform){ // else if
                            float rx = tiltVals[0];
                            float ry = tiltVals[1];
                            if (ry > 0.5) {
                                b.moveRight(dx);
                            }
                            if (ry < -0.5) {
                                b.moveLeft(dx);
                            }
                            if (rx > 0.5) {
                                b.moveDown(dy);
                            }
                            if (rx < -0.5) {
                                b.moveUp(dy);
                            }
                        }

                        System.out.println("timer");
                    }
                });
            }
        }, 0, 20);
    }



    @Override
    protected void onResume() {
        super.onResume();

        gyroscope.register();
    }

    @Override
    protected void onPause() {
        super.onPause();

        gyroscope.unregister();
    }
}