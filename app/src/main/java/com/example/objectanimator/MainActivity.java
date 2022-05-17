package com.example.objectanimator;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button button;
    ObjectAnimator objectAnimator;
    Path p = new Path();
    float x;
    float y;

    Gyroscope gyroscope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gyroscope = new Gyroscope(this);

        imageView = (ImageView) findViewById(R.id.block);
        button = (Button) findViewById(R.id.button);

        //Path p = new Path();
        //p.addCircle(500, 500, 200, Path.Direction.CCW);
        //p.rMoveTo(10, 10);
        p.moveTo(450, 700);

        //p.addCircle(500, 500, 200, Path.Direction.CCW);

        objectAnimator = ObjectAnimator.ofFloat(imageView, "x", "y", p);


        gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            public void onRotation(float rx, float ry, float rz) {
                if (ry > 1.0) { // tilt right 0.5 is what we want
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                    //p.moveTo(450, 700);
                    //p.lineTo(600, 700);
//                    p.addCircle(500, 500, 200, Path.Direction.CCW);
                    //objectAnimator.start();
                    moveRight();
                }
                if (ry < -1.0) { // tilt left
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                    //p.moveTo(450, 700);
                    //p.lineTo(300, 700);
//                    p.addCircle(500, 500, 200, Path.Direction.CCW);
                    //objectAnimator.start();
                }
                if (rz > 1.0) { // rotate left while flat
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                } else if (rz < -1.0) { // rotate right while flat
                    getWindow().getDecorView().setBackgroundColor(Color.GRAY);
                }
                if (rx > 1.0) { // tilt so it's facing you
                    getWindow().getDecorView().setBackgroundColor(Color.MAGENTA);
                } else if (rx < -1.0) { // tilt so it's facing away
                    getWindow().getDecorView().setBackgroundColor(Color.CYAN);
                }
            }
        });


        //objectAnimator = ObjectAnimator.ofFloat(imageView, "x", 800);
        //objectAnimator.start();


        //objectAnimator.start();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //objectAnimator.setDuration(100); // 4 seconds
                //objectAnimator = ObjectAnimator.ofFloat(imageView, "y", 300);
                //objectAnimator.setDuration(2000);
                //objectAnimator.start();
            }
        });
    }

    public void moveRight() {

        p.rLineTo(40, 0);
        x = x + 40;
        y = y + 0;
        objectAnimator = ObjectAnimator.ofFloat(imageView, "x", "y", p);
        p.moveTo(x, y);
        objectAnimator.start();

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