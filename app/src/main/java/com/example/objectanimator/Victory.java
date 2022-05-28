package com.example.objectanimator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Victory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victory);

        Button backtoMainMenu = (Button) findViewById(R.id.mainMenu);

        backtoMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameInfo g = new GameInfo();
                g.clearBlocks();
                backtoMainMenu();
            }
        });
    }

    public void backtoMainMenu() {
        Intent intent = new Intent (this, ChooseLevel.class);
        startActivity(intent);
    }
}