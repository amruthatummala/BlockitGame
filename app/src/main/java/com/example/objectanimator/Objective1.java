package com.example.objectanimator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class Objective1 extends AppCompatActivity {


    ImageView imageView;
    GameInfo temp = new GameInfo();
    String difficulty;
    //Block b;
    ObjectAnimator objectAnimator;
    //Path p;
    int dx = 10;
    int dy = 10;
    Timer timer = new Timer();
    //boolean tiltedRight = false;
    float[] tiltVals = new float[2];
    //Button button;
    ArrayList<Block> newBlockList;
    ArrayList<Block> newUnwantedBlockList;
    Block b;
    Button button;

    float angleChangeV =  1;
    double angleChangeH = 0.6;


    Gyroscope gyroscope;

    Handler handler = new Handler();

    float screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    float screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    double tempScreenHeight = Resources.getSystem().getDisplayMetrics().heightPixels; // platform height is ~500px 520
    //float platformHeight = screenHeight - 500;
    double platformHeight = tempScreenHeight - (3* 60 * Resources.getSystem().getDisplayMetrics().density); // 1669.0

//    DisplayMetrics metrics = getWindowManager().getDefaultDisplay().getMetrics(metrics);
//    float logicalDensity = metrics.density;
//    int px = (int) (dp * logicalDensity + 0.5);

    float x = screenWidth / 2;
    float y = screenHeight / 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objective1);

        difficulty = temp.getDifficulty();
        System.out.println("difficulty is " + difficulty);

        System.out.println("starting x is " + x); //540.0
        System.out.println("starting y is " + y); // 1010.0
        System.out.println("platform y is " + platformHeight);
        System.out.println("screen height is " + screenHeight);
        double temp = 57 * Resources.getSystem().getDisplayMetrics().density;
        System.out.println("platform height is " + temp);

        //Bottom part/portal
        ImageView blackblockright = (ImageView) findViewById(R.id.blacksquareright);
        ImageView blackblockleft = (ImageView) findViewById(R.id.blacksquareleft);
        ImageView yellowportal = (ImageView) findViewById(R.id.yellowportal);
        double yellowportalwidth = yellowportal.getWidth();

        //Top part/portal
        ImageView blackblockrighttop = (ImageView) findViewById(R.id.blacksquarerighttop);
        ImageView blackblocklefttop = (ImageView) findViewById(R.id.blacksquarelefttop);
        ImageView blueportal = (ImageView) findViewById(R.id.blueportal);
        double blueportalwidth = blueportal.getWidth();

        button = (Button) findViewById(R.id.button2);


        gyroscope = new Gyroscope(this);

        Block b1 = new Block(ContextCompat.getDrawable(this, R.drawable.purplesquare), (int) (60 * getResources().getDisplayMetrics().density), (int) (60 * getResources().getDisplayMetrics().density), this, screenHeight, screenWidth);
        Block b2 = new Block(ContextCompat.getDrawable(this, R.drawable.redsquare), (int) (60 * getResources().getDisplayMetrics().density), (int) (60 * getResources().getDisplayMetrics().density), this, screenHeight, screenWidth);
        Block b3 = new Block(ContextCompat.getDrawable(this, R.drawable.redsquare), (int) (60 * getResources().getDisplayMetrics().density), (int) (60 * getResources().getDisplayMetrics().density), this, screenHeight, screenWidth);
        //Block b4 = new Block(ContextCompat.getDrawable(this, R.drawable.blueblock), (int) (60 * getResources().getDisplayMetrics().density), (int) (60 * getResources().getDisplayMetrics().density), this, screenHeight, screenWidth);
        Block b5 = new Block(ContextCompat.getDrawable(this, R.drawable.purplesquare), (int) (60 * getResources().getDisplayMetrics().density), (int) (60 * getResources().getDisplayMetrics().density), this, screenHeight, screenWidth);
        Block b6 = new Block(ContextCompat.getDrawable(this, R.drawable.redsquare), (int) (60 * getResources().getDisplayMetrics().density), (int) (60 * getResources().getDisplayMetrics().density), this, screenHeight, screenWidth);
        Block b7 = new Block(ContextCompat.getDrawable(this, R.drawable.purplesquare), (int) (60 * getResources().getDisplayMetrics().density), (int) (60 * getResources().getDisplayMetrics().density), this, screenHeight, screenWidth);
        Block b8 = new Block(ContextCompat.getDrawable(this, R.drawable.purplesquare), (int) (60 * getResources().getDisplayMetrics().density), (int) (60 * getResources().getDisplayMetrics().density), this, screenHeight, screenWidth);
        Block b9 = new Block(ContextCompat.getDrawable(this, R.drawable.redsquare), (int) (60 * getResources().getDisplayMetrics().density), (int) (60 * getResources().getDisplayMetrics().density), this, screenHeight, screenWidth);
        Block b4 = new Block(ContextCompat.getDrawable(this, R.drawable.blueblock), (int) (60 * getResources().getDisplayMetrics().density), (int) (60 * getResources().getDisplayMetrics().density), this, screenHeight, screenWidth);



        ArrayList<Block> wrongBlocks = new ArrayList<>();

        ArrayList<Block> oldBlockList = new ArrayList<>();
        newBlockList = new ArrayList<>();
        newUnwantedBlockList = new ArrayList<>();

        oldBlockList.add(b1);
        oldBlockList.add(b2);
        oldBlockList.add(b3);
        oldBlockList.add(b5);
        oldBlockList.add(b6);
        oldBlockList.add(b7);
        oldBlockList.add(b8);
        oldBlockList.add(b9);
        //oldBlockList.add(b4);


        ArrayList<Block> correctBlocks = new ArrayList<>();
        if (difficulty.equals("easy")) {
            correctBlocks.add(b1);
            correctBlocks.add(b2);
            correctBlocks.add(b3);

            wrongBlocks.add(b5);
            wrongBlocks.add(b6);
            wrongBlocks.add(b7);
            wrongBlocks.add(b8);
            wrongBlocks.add(b9);
        } else if (difficulty.equals("medium")) {
            correctBlocks.add(b1);
            correctBlocks.add(b2);
            correctBlocks.add(b3);
            correctBlocks.add(b5);
            correctBlocks.add(b6);

            wrongBlocks.add(b7);
            wrongBlocks.add(b8);
            wrongBlocks.add(b9);
        } else if (difficulty.equals("hard")) {
            correctBlocks.add(b1);
            correctBlocks.add(b2);
            correctBlocks.add(b3);
            correctBlocks.add(b5);
            correctBlocks.add(b6);
            correctBlocks.add(b7);
            correctBlocks.add(b8);
            correctBlocks.add(b9);
        }


        b = oldBlockList.get(0);

        ConstraintLayout c = (ConstraintLayout) findViewById(R.id.objective1);
        c.addView(b.getImageView());
        c.setConstraintSet(new ConstraintSet());

//        b.setX(x);
//        b.setY(y);

        //Path p = new Path();
        //p.addCircle(500, 500, 200, Path.Direction.CCW);
        //p.rMoveTo(10, 10);


        //p.addCircle(500, 500, 200, Path.Direction.CCW);

        //objectAnimator = ObjectAnimator.ofFloat(imageView, "x", "y", p);


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


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (b.getX() > screenWidth || b.getX() < 0 || b.getY() < blackblockrighttop.getHeight() || b.getY() > screenHeight - blackblockleft.getHeight()) {
                            b.reset();
                            System.out.println("back to beginning");
                        }
                        System.out.println("x is " + b.getX());
                        System.out.println("y is " +b.getY());
//                        b.getY() < platformHeight + dy && b.getY() > platformHeight - dy &&
//                                b.getX() < blackblockright.getX() +dx && b.getX() >
//                                screenWidth-(yellowportalwidth+blackblockright.getX())
                        if (Math.abs(b.getY()-yellowportal.getY()) <= yellowportal.getHeight()/2+dy &&
                                b.getX() < blackblockright.getX() +dx && b.getX() >
                                yellowportal.getX()-dx) {
//                            boolean found = false;
//                            for (Block cb : correctBlocks) {
//                                if (b.getImageView().getDrawable().equals(cb.getImageView().getDrawable())) {
//                                    found = true;
//                                }
//                            }
//                            if (!found) {
//                                System.out.println("game over");
//                                cancel();
//                                gameOver();
////                                Toast.makeText(Objective1&& b.getX() < blackblockright.getX() +dx && b.getX() > screenWidth-(yellowportalwidth+blackblockright.getX()).this, "Remember to only collect the blocks that you need to build the structure!"
////                                , Toast.LENGTH_LONG).show();
//                            } else {
                                System.out.println("roses");
                                b.reset();
                                c.removeView(b.getImageView());
                                newBlockList.add(b);
                                oldBlockList.remove(0);
                                System.out.println("idk");
                                System.out.println("Added block to newBlockList");
                                System.out.println("Removed block from oldBlockList");
                                if (oldBlockList.size() == 0) {
                                    cancel();
                                    System.out.println("MOVING ON");
                                    //openMainActivity();
                                } else {
                                    b = oldBlockList.get(0);
                                    c.addView(b.getImageView());
                                }
                            //}

                            //cancel();
                            // else if in line below
                        } if(b.getY() > 0 && b.getY() < blueportal.getHeight() && b.getX() > blackblocklefttop.getWidth() && b.getX() < screenWidth - (blackblockrighttop.getWidth()+blueportalwidth)) {
//                            System.out.println("I touched the top");
//                            boolean found = false;
//                            for (Block cb : wrongBlocks) {
//                                if (b.getImageView().getDrawable().equals(cb.getImageView().getDrawable())) {
//                                    found = true;
//                                }
//                            }
//                            if (!found) {
//                                System.out.println("game over");
//                                cancel();
//                                gameOver();
////                                Toast.makeText(Objective1&& b.getX() < blackblockright.getX() +dx && b.getX() > screenWidth-(yellowportalwidth+blackblockright.getX()).this, "Remember to only collect the blocks that you need to build the structure!"
////                                , Toast.LENGTH_LONG).show();
//                            } else {
                                System.out.println("roses");
                                c.removeView(b.getImageView());
                                b.reset();
                                newUnwantedBlockList.add(b);
                                oldBlockList.remove(0);
                                System.out.println("idk");
                                System.out.println("Added block to newUnwantedBlockList");
                                System.out.println("Removed block from oldBlockList");
                                if (oldBlockList.size() == 0) {
                                    cancel();
                                    //openMainActivity();
                                } else {
                                    b = oldBlockList.get(0);
                                    c.addView(b.getImageView());
                                }
                            //}


                        }
                        else {
                            float rx = tiltVals[0];
                            float ry = tiltVals[1];
                            System.out.println("rx is " + rx);
                            System.out.println("ry is " + ry);
                            if (ry > angleChangeV) {
                                b.moveRight(dx);
                            }
                            if (ry < -1*angleChangeV) {
                                b.moveLeft(dx);
                            }
                            if (rx > angleChangeH) {
                                b.moveDown(dy);
                            }
                            if (rx < -1*angleChangeH) {
                                b.moveUp(dy);
                            }
                        }

                        System.out.println("timer");
                    }
                });
            }
        }, 0, 20);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctBlocks.size() == newBlockList.size()) {
                    openMainActivity();
                } else {
                    gameOver();
                }
//                int correctCount = 0;
//                for(Block cbl : correctBlocks){
//                    for(Block nbl : newBlockList){
//                        if(cbl.getImageView().getDrawable().equals(nbl.getImageView().getDrawable())){
//                            correctCount++;
//                        }
//                    }
//                }
//                if(correctCount == correctBlocks.size()){
//                    System.out.println("Roger that");
//                    openObjective2();
//                }
            }
        });

    }

    public void openObjective2(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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

    public void gameOver() {
        Intent gameOver = new Intent(this, GameOver.class);
        gameOver.putExtra("activity", "objective1");
        startActivity(gameOver);
    }

    public void openMainActivity(){
        Intent openMainActivity = new Intent(this, MainActivity.class);
        temp.setBlocks(newBlockList);
        startActivity(openMainActivity);
    }
}