package com.example.objectanimator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        TextView obj1 = (TextView) findViewById(R.id.objective1);
        TextView main = (TextView) findViewById(R.id.mainactivity);
        Button retry = (Button) findViewById(R.id.tryAgain);

        String s = getIntent().getExtras().getString("activity");
        if (s.equals("objective1")) {
            obj1.setVisibility(View.VISIBLE);
            main.setVisibility(View.INVISIBLE);
        } else {
            main.setVisibility(View.VISIBLE);
            obj1.setVisibility(View.INVISIBLE);
        }

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s.equals("objective1")) {
                    openObjective1();
                } else {
                    openMainActivity();
                }
            }
        });
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openObjective1() {
        Intent intent = new Intent(this, Objective1.class);
        startActivity(intent);
    }
}