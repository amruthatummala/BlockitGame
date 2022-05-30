package com.example.objectanimator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class GameOver extends AppCompatActivity {

    GameInfo g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        g = new GameInfo();

        float screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        float screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        TextView obj1 = (TextView) findViewById(R.id.objective1);
        TextView main = (TextView) findViewById(R.id.mainactivity);
        Button retry = (Button) findViewById(R.id.tryAgain);
        Button backtoMain = (Button) findViewById(R.id.backtoMain);
        ArrayList<Block> correctBlocks = new ArrayList<>();

        String s = getIntent().getExtras().getString("activity");
        if (s.equals("objective1")) {
            obj1.setVisibility(View.VISIBLE);
            main.setVisibility(View.INVISIBLE);
        } else {
            main.setVisibility(View.VISIBLE);
            obj1.setVisibility(View.INVISIBLE);

            Block b1 = new Block(ContextCompat.getDrawable(this, R.drawable.purplesquare), (int) (60 * getResources().getDisplayMetrics().density), (int) (60 * getResources().getDisplayMetrics().density), this, screenHeight, screenWidth);
            Block b2 = new Block(ContextCompat.getDrawable(this, R.drawable.redsquare), (int) (60 * getResources().getDisplayMetrics().density), (int) (60 * getResources().getDisplayMetrics().density), this, screenHeight, screenWidth);
            Block b3 = new Block(ContextCompat.getDrawable(this, R.drawable.redsquare), (int) (60 * getResources().getDisplayMetrics().density), (int) (60 * getResources().getDisplayMetrics().density), this, screenHeight, screenWidth);
            //Block b4 = new Block(ContextCompat.getDrawable(this, R.drawable.blueblock), (int) (60 * getResources().getDisplayMetrics().density), (int) (60 * getResources().getDisplayMetrics().density), this, screenHeight, screenWidth);
            Block b5 = new Block(ContextCompat.getDrawable(this, R.drawable.purplesquare), (int) (60 * getResources().getDisplayMetrics().density), (int) (60 * getResources().getDisplayMetrics().density), this, screenHeight, screenWidth);
            Block b6 = new Block(ContextCompat.getDrawable(this, R.drawable.redsquare), (int) (60 * getResources().getDisplayMetrics().density), (int) (60 * getResources().getDisplayMetrics().density), this, screenHeight, screenWidth);
            Block b7 = new Block(ContextCompat.getDrawable(this, R.drawable.purplesquare), (int) (60 * getResources().getDisplayMetrics().density), (int) (60 * getResources().getDisplayMetrics().density), this, screenHeight, screenWidth);
            Block b8 = new Block(ContextCompat.getDrawable(this, R.drawable.purplesquare), (int) (60 * getResources().getDisplayMetrics().density), (int) (60 * getResources().getDisplayMetrics().density), this, screenHeight, screenWidth);
            Block b9 = new Block(ContextCompat.getDrawable(this, R.drawable.redsquare), (int) (60 * getResources().getDisplayMetrics().density), (int) (60 * getResources().getDisplayMetrics().density), this, screenHeight, screenWidth);

            if (s.equals("mainEasy")) {
                correctBlocks.add(b1);
                correctBlocks.add(b2);
                correctBlocks.add(b3);
            } else if (s.equals("mainMedium")) {
                correctBlocks.add(b1);
                correctBlocks.add(b2);
                correctBlocks.add(b3);
                correctBlocks.add(b5);
                correctBlocks.add(b6);
            } else if (s.equals("mainHard")) {
                correctBlocks.add(b1);
                correctBlocks.add(b2);
                correctBlocks.add(b3);
                correctBlocks.add(b5);
                correctBlocks.add(b6);
                correctBlocks.add(b7);
            }
        }

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s.equals("objective1")) {
                    openObjective1();
                } else {
                    //g.refillBlockPool();
                    g.clearBlocks();
                    g.setBlocks(correctBlocks);

                    openMainActivity();
                }
                //backtoMainMenu();
            }
        });

        backtoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backtoMainMenu();
            }
        });
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("retry", true);
        startActivity(intent);
    }

    public void openObjective1() {
        Intent intent = new Intent(this, Objective1.class);
        startActivity(intent);
    }

    public void backtoMainMenu() {
        g.restartGame();
        Intent intent = new Intent(this, ChooseLevel.class);
        startActivity(intent);
    }
}