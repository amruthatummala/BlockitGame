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

    float angleChangeV = 1;
    double angleChangeH = 0.6;

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
        g = new GameInfo();
        blockPool = g.getBlocks();
        difficulty = g.getDifficulty();

        b = blockPool.remove(0);

        platform= new Block(ContextCompat.getDrawable(this, R.drawable.rectangle),(int) screenWidth , (int) (60 * getResources().getDisplayMetrics().density), this, screenHeight, screenWidth);
        //platform.setY(platformHeight);
        platform.setY(platformHeight);


        platform.setX(0);

        ConstraintLayout c = (ConstraintLayout) findViewById(R.id.main);

        //c.removeView(b.getImageView());
        //c.addView(b1.getImageView());
        c.setConstraintSet(new ConstraintSet());

        System.out.println("screenWidth is " + screenWidth); //1080.0

        if (getIntent().getExtras() != null) {
            Boolean retry = getIntent().getExtras().getBoolean("retry");
            if (retry) {
                for (Block b : placedBlocks) {
                    c.removeView(b.getImageView());
                }
            }
        }



        c.addView(platform.getImageView());
        c.addView(b.getImageView());

        Button btnFinish = (Button) findViewById(R.id.btnFinish);

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
//        Context context = getApplicationContext();
//        Gson gson = new Gson();
//        shref = context.getSharedPreferences("blocks", Context.MODE_PRIVATE);
//        String response=shref.getString("blocks", "");
//        ArrayList<Block> blockPool = gson.fromJson(response,
//                new TypeToken<ArrayList<Block>>(){}.getType());


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
                } else {
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
//                        for (int i = 0; i < size; i++) {
//                            Block b1 = placedBlocks.get(i);
//                            if(b.stackBlock(b1, errorMargin, dy)) {
//                                System.out.println("successfully stacked block");
////                                cancel();
//                                placedBlocks.add(0, b);
//                                //blockPool.remove(0);
//                                if (blockPool.size() > 0) {
//                                    b = blockPool.remove(0);
//                                    c.addView(b.getImageView());
//                                } else {
//                                    cancel();
//                                }
//                                checkPlatform = false;
//                                //cancel();
//                                break;
//
//                            }
//                        }
                        System.out.println("x is " + b.getX());
                        System.out.println("y is " +b.getY());

                        if (checkPlatform){ // else if
                            float rx = tiltVals[0];
                            float ry = tiltVals[1];
                            if (ry > angleChangeV) {
                                Block temp = findMatchingBlock((int) b.getX()+dx,(int) b.getY());
                                if (temp == null) {
                                    b.moveRight(dx);
                                    System.out.println("christmas");
//                                } else {
//                                    System.out.println("should stack");
//                                    b.setX(temp.getX()-b.getWidth());
//                                    placedBlocks.add(0, b);
//                                    if (blockPool.size() > 0) {
//                                        b = blockPool.remove(0);
//                                        c.addView(b.getImageView());
//                                    } else {
//                                        cancel();
//                                    }
                                }

                            }
                            if (ry < -1*angleChangeV) {
                                Block temp = findMatchingBlock((int) b.getX()-dx,(int) b.getY());
                                if (temp == null) {
                                    b.moveLeft(dx);
                                    System.out.println("christmas");
//                                } else {
//                                    System.out.println("should stack");
//                                    b.setX(temp.getX()-temp.getWidth());
//                                    placedBlocks.add(0, b);
//                                    if (blockPool.size() > 0) {
//                                        b = blockPool.remove(0);
//                                        c.addView(b.getImageView());
//                                    } else {
//                                        cancel();
//                                    }
                                }
                            }
                            if (rx > angleChangeH) {
                                Block temp = findMatchingBlock((int) b.getX(),(int) b.getY()-dy);
                                if (temp == null) {
                                    b.moveDown(dy);
                                    System.out.println("christmas");
                                } else {
                                    System.out.println("should stack");
                                    b.setY(temp.getY()-b.getHeight());
//                                    if (temp.getWidth() > 500) {
//                                        b.setY(temp.getY()-b.getHeight()/2);
//                                    }
                                    placedBlocks.add(0, b);
                                    if (blockPool.size() > 0) {
                                        b = blockPool.remove(0);
                                        c.addView(b.getImageView());
                                    } else {
                                        cancel();
                                    }
                                }
                            }
                            if (rx < -1*angleChangeH) {
                                Block temp = findMatchingBlock((int) b.getX(),(int) b.getY()+dy);
                                if (temp == null) {
                                    b.moveUp(dy);
                                    System.out.println("christmas");
//                                } else {
//                                    System.out.println("should stack");
//                                    b.setY(temp.getY()-b.getHeight());
//                                    placedBlocks.add(0, b);
//                                    if (blockPool.size() > 0) {
//                                        b = blockPool.remove(0);
//                                        c.addView(b.getImageView());
//                                    } else {
//                                        cancel();
//                                    }
                                }
                            }
                        }

                        System.out.println("timer");
                    }
                });
            }
        }, 0, 20);

    }

//    public void alignBlocks() {
//        if (placedBlocks.size() > 3) {
//            float[] baseCoords = findFirstBlock(0);
//            int x = (int) baseCoords[0];
//            int y = (int) baseCoords[1];
//            Block match = findMatchingBlock(x+placedBlocks.get(0).getWidth()+errorMargin, y);
//            if (match != null) {
//                System.out.println("this ran");
//                match.setX(x+match.getWidth());
//            }
//        }
//    }

    public Block findMatchingBlock(int x, int y) {
        for (int i = 0; i < placedBlocks.size()-1; i++) {
            Block temp = placedBlocks.get(i);
            //temp.getX() <= x && x <= temp.getX()+temp.getWidth()
            if (Math.abs(temp.getX()-x) < temp.getWidth()  &&
            Math.abs(y - temp.getY()) < temp.getHeight() ) {
                return temp;
            }
        }
        if (Math.abs(y - platformHeight) < platform.getHeight()) {
            return platform;
        }
        return null;
    }

    public Block findFirstBlock(int startX) {
        if (placedBlocks.size() > 1) {
            for (int x = startX; x < screenWidth; x+=40) {
                Block temp = findMatchingBlock(x, (int) placedBlocks.get(placedBlocks.size()-2).getY());
                if (temp != null) {
                    return temp;
                }
            }
        }

        return null;
    }

    public void checkEasy() {
//        Block firstBlock = placedBlocks.get(0);
//        Block secondBlock = placedBlocks.get(1);
//        Block thirdBlock = placedBlocks.get(2);

        Block firstBlock = findFirstBlock(0);
        System.out.println("firstBlock x: " + firstBlock.getX());
        System.out.println("firstBlock y: " + firstBlock.getY());
        if (firstBlock != null) {
            Block secondBlock = findMatchingBlock((int) firstBlock.getX()+firstBlock.getWidth()+dx, (int) firstBlock.getY());
            Block thirdBlock = findMatchingBlock((int) firstBlock.getX(), (int) firstBlock.getY()+ firstBlock.getHeight()+dy);
            if (secondBlock != null) {
                if (thirdBlock != null) {
                    System.out.println("coral reef");
                    goToVictory();
                }

            } else {
                System.out.println("dolphin");
                goToGameOver();
            }
        } else {
            System.out.println("dolphin");
            goToGameOver();
        }




//        secondBlock.setX(300);
//        firstBlock.setX(300+firstBlock.getWidth());
//        thirdBlock.setX(300);
//        thirdBlock.setY(secondBlock.getY()+secondBlock.getHeight());
//        if (firstBlock.getX() - secondBlock.getWidth() >= secondBlock.getX()-40
//        && Math.abs(thirdBlock.getX()-firstBlock.getX()) <= 40
//        && firstBlock.getY()+firstBlock.getHeight() == thirdBlock.getY()) {
//            System.out.println("right");
//        }
    }

//        if (containsBlock(350, (int) platformHeight+firstBlock.getHeight())
//                && containsBlock(350+(int) firstBlock.getWidth(), (int) platformHeight+firstBlock.getHeight())
//                && containsBlock(350, (int) platformHeight+ 2*firstBlock.getHeight())) {
//            System.out.println("right");
//        }

//        if (secondBlock.getX() - thirdBlock.getWidth() >= thirdBlock.getX()-40
//                && Math.abs(firstBlock.getX()-secondBlock.getX()) <= 40
//                && secondBlock.getY()+secondBlock.getHeight() == firstBlock.getY()) {
//            System.out.println("right");
//        }
//        if (thirdBlock.getX() - firstBlock.getWidth() >= firstBlock.getX()-40
//                && Math.abs(thirdBlock.getX()-secondBlock.getX()) <= 40
//                && thirdBlock.getY()+thirdBlock.getHeight() == secondBlock.getY()) {
//            System.out.println("right");
//        }
//        if (thirdBlock.getX() - secondBlock.getWidth() >= secondBlock.getX()-40
//                && Math.abs(thirdBlock.getX()-firstBlock.getX()) <= 40
//                && thirdBlock.getY()+thirdBlock.getHeight() == firstBlock.getY()) {
//            System.out.println("right");
//        }
    }

    public void checkMedium() {

    }

    public void checkHard() {

    }

    public boolean containsBlock(int x, int y) {
        for (int i = 0; i < placedBlocks.size()-2; i++) {
            if (Math.abs((int) placedBlocks.get(i).getY()-y) <= 70
                    && Math.abs((int) placedBlocks.get(i).getX()-x) <= 70) {
                return true;
            }
        }
        return false;
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
        Intent intent = new Intent(this, GameOver.class);
        intent.putExtra("activity", "main");
        startActivity(intent);
    }
}