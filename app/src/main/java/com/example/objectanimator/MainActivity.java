package com.example.objectanimator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Block platform;
    ObjectAnimator objectAnimator;
    //Path p;
    final int dx = 10;
    final int dy = 10;
    Timer timer = new Timer();
    //boolean tiltedRight = false;
    float[] tiltVals = new float[2];

    double angleChangeV = 1;
    double angleChangeH = 0.3;

    //ArrayList<Block> blockPool;

    final int errorMargin = 90;

    ArrayList<Block> placedBlocks = new ArrayList<>();
    Block b;

    Gyroscope gyroscope;

    SharedPreferences shref;

    Handler handler = new Handler();

    GameInfo g;
    String difficulty;
    ArrayList<Block> blockPool;

    float screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    float screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    double tempScreenHeight = Resources.getSystem().getDisplayMetrics().heightPixels; // platform height is ~500px 520

    float platformHeight = (float) tempScreenHeight - (60 * Resources.getSystem().getDisplayMetrics().density); // 1669.0

    float x = screenWidth / 2;
    float y = screenHeight / 2;

    ConstraintLayout c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        g = new GameInfo();
        blockPool = g.getBlocks();
        difficulty = g.getDifficulty();

        b = blockPool.remove(0);

        platform = new Block(ContextCompat.getDrawable(this, R.drawable.rectangle), (int) screenWidth, (int) (60 * getResources().getDisplayMetrics().density), this, screenHeight, screenWidth);
        platform.setY(platformHeight);

        platform.setX(0);

        c = (ConstraintLayout) findViewById(R.id.main);

        c.setConstraintSet(new ConstraintSet());

        System.out.println("screenWidth is " + screenWidth); //1080.0


        c.addView(platform.getImageView());
        c.addView(b.getImageView());

        Button btnFinish = (Button) findViewById(R.id.btnFinish);

        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        Block tempB = new Block(ContextCompat.getDrawable(this, R.drawable.rectangle), (int) screenWidth, (int) (60 * getResources().getDisplayMetrics().density), this, screenHeight, screenWidth);
        tempB.setX(0);


        placedBlocks.add(platform);

        gyroscope = new Gyroscope(this);

        gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            public void onRotation(float rx, float ry, float rz) {
                if (Math.abs(rx) >= angleChangeH) {
                    tiltVals[0] = rx;
                }

                if (Math.abs(ry) >= angleChangeV) {
                    tiltVals[1] = ry;
                }

            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("pressed button finish");
                if (difficulty.equals("easy")) {
                    checkEasy();
                }
                if (difficulty.equals("medium")) {
                    checkMedium();
                }
                if(difficulty.equals("hard")){
                    checkHard();
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
                        //alignBlocks();
                        int size = placedBlocks.size();
                        System.out.println("size of placedBlocks is " + size);
                        System.out.println("placedBlocks: " + placedBlocks);

                        System.out.println("x is " + b.getX());
                        System.out.println("y is " + b.getY());

                        if (checkPlatform) { // else if
                            float rx = tiltVals[0];
                            float ry = tiltVals[1];
                            if (ry > angleChangeV) {
                                Block temp = findMatchingBlock((int) b.getX() + dx, (int) b.getY());
                                if (temp == null) {
                                    b.moveRight(dx);
                                    System.out.println("christmas");

                                }

                            }
                            if (ry < -1 * angleChangeV) {
                                Block temp = findMatchingBlock((int) b.getX() - dx, (int) b.getY());
                                if (temp == null) {
                                    b.moveLeft(dx);
                                    System.out.println("christmas");

                                }
                            }
                            if (rx > angleChangeH) {
                                Block temp = findMatchingBlock((int) b.getX(), (int) b.getY() - dy);
                                if (temp == null) {
                                    b.moveDown(dy);
                                    System.out.println("christmas");
                                } else {
                                    System.out.println("should stack");
                                    b.setY(temp.getY() - b.getHeight());

                                    placedBlocks.add(0, b);
                                    if (blockPool.size() > 0) {
                                        b = blockPool.remove(0);
                                        c.addView(b.getImageView());
                                    } else {
                                        cancel();
                                    }
                                }
                            }
                            if (rx < -1 * angleChangeH) {
                                Block temp = findMatchingBlock((int) b.getX(), (int) b.getY() + dy);
                                if (temp == null) {
                                    b.moveUp(dy);
                                    System.out.println("christmas");

                                }
                            }
                        }

                        System.out.println("timer");
                    }
                });
            }
        }, 0, 20);

    }


    public Block findMatchingBlock(int x, int y) {
        for (int i = 0; i < placedBlocks.size() - 1; i++) {
            Block temp = placedBlocks.get(i);

            if (Math.abs(temp.getX() - x) < temp.getWidth() &&
                    Math.abs(y - temp.getY()) < temp.getHeight()) {
                System.out.println("ambrosia");
                return temp;
            }
        }
        if (Math.abs(y - platform.getY()) < platform.getHeight()) {
            System.out.println("peaches");
            System.out.println("peaches y is " + y);
            return platform;
        }


        return null;
    }

    public Block findFirstBlock(int startX) {
        if (placedBlocks.size() > 1) {
            for (int x = startX + 1; x < screenWidth; x += 40) {
                Block temp = findMatchingBlock(x, (int) placedBlocks.get(placedBlocks.size() - 2).getY());
                if (temp != null) {
                    return temp;
                }
            }
        }

        return null;
    }

    public void checkEasy() {
        boolean correct = false;
        Block firstBlock = findFirstBlock(0);

        if (firstBlock != null) {
            Block secondBlock = findMatchingBlock((int) firstBlock.getX() + firstBlock.getWidth() + dx, (int) firstBlock.getY());
            Block thirdBlock = findMatchingBlock((int) firstBlock.getX(), (int) firstBlock.getY() - firstBlock.getHeight() - dy);
            if (secondBlock != null && Math.abs(secondBlock.getX() - firstBlock.getX()) <= firstBlock.getWidth() + dx) {
                if (thirdBlock != null && Math.abs(thirdBlock.getY() - firstBlock.getY()) <= firstBlock.getHeight() + dy &&
                        Math.abs(thirdBlock.getX() - firstBlock.getX()) <= 7 * dx) {
                    System.out.println("coral reef");
                    correct = true;
                }
            }
        }
        if (correct) {
            goToVictory();
        } else {
            goToGameOver();
        }

    }

    public void checkMedium() {
        System.out.println("platform is " + platform);
        boolean correct = false;
        Block firstBlock = findFirstBlock(0);
        if (firstBlock != null) {
            Block secondBlock = findMatchingBlock((int) firstBlock.getX() + firstBlock.getWidth() + dx, //+dx
                    (int) firstBlock.getY());
            Block fourthBlock = findMatchingBlock((int) firstBlock.getX(),
                    (int) firstBlock.getY() - firstBlock.getHeight() - dy);
            float x = firstBlock.getY() + firstBlock.getHeight() + dy;
            System.out.println("the y for fourthBlock is " + x);
            System.out.println("firstBlock is not null");
            if (secondBlock != null && Math.abs(secondBlock.getX() - firstBlock.getX()) <= firstBlock.getWidth() + 3 * dx) {
                Block thirdBlock = findMatchingBlock((int) secondBlock.getX() + secondBlock.getWidth() + dx,//+dx
                        (int) secondBlock.getY());
                System.out.println("cats");
                if (thirdBlock != null && Math.abs(thirdBlock.getX() - secondBlock.getX()) <= secondBlock.getWidth() + 3 * dx) {
                    Block fifthBlock = findMatchingBlock((int) thirdBlock.getX(), //+dx
                            (int) thirdBlock.getY() - thirdBlock.getHeight() - dy);
                    if (fifthBlock != null && fourthBlock != null &&
                            Math.abs(fifthBlock.getX() - thirdBlock.getX()) <= 7 * dx &&
                            Math.abs(fourthBlock.getX() - firstBlock.getX()) <= 7 * dx &&
                            Math.abs(fourthBlock.getY() - firstBlock.getY()) <= firstBlock.getHeight() + dy &&
                            Math.abs(fifthBlock.getY() - thirdBlock.getY()) <= thirdBlock.getHeight() + dy) {
                        System.out.println("sea turtles");
                        correct = true;
                    }
                }
            }
        }
        if (correct) {
            goToVictory();
        } else {
            goToGameOver();
        }
    }


    public void checkHard() {
        System.out.println("Hard platform is " + platform);
        boolean correct = false;
        Block firstBlock = findFirstBlock(0);
        if(firstBlock != null){
            System.out.println("FIRST DOWN");
            Block secondBlock = findMatchingBlock((int) firstBlock.getX() + firstBlock.getWidth() + dx, //+dx
                    (int) firstBlock.getY());
            if(secondBlock != null && Math.abs(secondBlock.getX() - firstBlock.getX()) <= firstBlock.getWidth() + 3 * dx){
                System.out.println("SECOND DOWN");
                Block thirdBlock = findMatchingBlock((int) secondBlock.getX() + secondBlock.getWidth() + dx, //+dx
                        (int) secondBlock.getY());
                Block fourthBlock = findMatchingBlock((int) secondBlock.getX(),
                        (int) secondBlock.getY() - secondBlock.getHeight() - dy);
                if(thirdBlock != null && Math.abs(fourthBlock.getX() - secondBlock.getX()) <= 7 * dx &&
                        Math.abs(fourthBlock.getY() - secondBlock.getY()) <= secondBlock.getHeight() + dy &&
                        Math.abs(secondBlock.getX() - thirdBlock.getX()) <= thirdBlock.getWidth() + 3 * dx){
                    System.out.println("THIRD DOWN");
                    Block fifthBlock = findMatchingBlock((int) thirdBlock.getX() + dx,
                            (int) thirdBlock.getY() - thirdBlock.getHeight());
                    if(fifthBlock != null && Math.abs(fifthBlock.getX() - thirdBlock.getX()) <= 7 * dx &&
                            Math.abs(fifthBlock.getY() - thirdBlock.getY()) <= thirdBlock.getHeight() + dy){
                        System.out.println("FOURTH DOWN");
                        Block sixthBlock = findMatchingBlock((int) fifthBlock.getX(),
                                (int) fifthBlock.getY() - fifthBlock.getHeight() - dy);
                        if(sixthBlock != null && Math.abs(fifthBlock.getX() - sixthBlock.getX()) <= 7 * dx &&
                                Math.abs(fifthBlock.getY() - sixthBlock.getY()) <= sixthBlock.getHeight() + dy){
                            System.out.println("Fifth down WOWWWW");
                            correct = true;
                        }
                    }

                }
            }
        }

        if (correct) {
            goToVictory();
        } else {
            goToGameOver();
        }
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

    public void goToVictory() {
        Intent intent = new Intent(this, Victory.class);
        startActivity(intent);
    }

    public void goToGameOver() {
        for (Block b : placedBlocks) {
            c.removeView(b.getImageView());
        }
        Intent intent = new Intent(this, GameOver.class);
        if (difficulty.equals("easy")) {
            intent.putExtra("activity", "mainEasy");
        } else if (difficulty.equals("medium")) {
            intent.putExtra("activity", "mainMedium");
        } else if (difficulty.equals("hard")) {
            intent.putExtra("activity", "mainHard");
        }
        startActivity(intent);
    }
}
