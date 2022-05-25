package com.example.objectanimator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseLevel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level);

        Button btnEasy = (Button) findViewById(R.id.btnEasy);
        Button btnMedium = (Button) findViewById(R.id.btnMedium);
        Button btnHard = (Button) findViewById(R.id.btnHard);

        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GameInfo().setDifficulty("easy");
                openObjective1();
            }
        });

        btnMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GameInfo().setDifficulty("medium");
                openObjective1();
            }
        });

        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GameInfo().setDifficulty("hard");
                openObjective1();
            }
        });
    }

    public void openObjective1() {
        Intent openObj1 = new Intent(this, Objective1.class);
        startActivity(openObj1);
    }
}