package com.example.objectanimator;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button button;
    ObjectAnimator objectAnimator;
    //Path p;
    float x = 450;
    float y = 700;
    int dx = 40;
    int dy = 40;
    boolean tiltedRight = false;

    Gyroscope gyroscope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        p = new Path();
//        p.moveTo(x, y);

        gyroscope = new Gyroscope(this);

        imageView = (ImageView) findViewById(R.id.block);
        button = (Button) findViewById(R.id.button);

        //Path p = new Path();
        //p.addCircle(500, 500, 200, Path.Direction.CCW);
        //p.rMoveTo(10, 10);


        //p.addCircle(500, 500, 200, Path.Direction.CCW);

        //objectAnimator = ObjectAnimator.ofFloat(imageView, "x", "y", p);


        gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            public void onRotation(float rx, float ry, float rz) {
                if (ry > 1.0) { // tilt right 0.5 is what we want
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                    System.out.println("tilted");
                    //tiltedRight = true;
                    //p.moveTo(450, 700);
                    //p.lineTo(600, 700);
//                    p.addCircle(500, 500, 200, Path.Direction.CCW);
                    //objectAnimator.start();
                    moveRight();
                }
                if (ry < -1.0) { // tilt left
                    System.out.println("tilted");
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                    //p.moveTo(450, 700);
                    //p.lineTo(300, 700);
//                    p.addCircle(500, 500, 200, Path.Direction.CCW);
                    //objectAnimator.start();
                    //moveLeft();
                }
                if (rz > 1.0) { // rotate left while flat
                    System.out.println("tilted");
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                } if (rz < -1.0) { // rotate right while flat
                    System.out.println("tilted");
                    getWindow().getDecorView().setBackgroundColor(Color.GRAY);
                }
                if (rx > 1.0) { // tilt so it's facing you
                    System.out.println("tilted");
                     //moveDown();
                    getWindow().getDecorView().setBackgroundColor(Color.MAGENTA);
                } if (rx < -1.0) { // tilt so it's facing away
                    getWindow().getDecorView().setBackgroundColor(Color.CYAN);
                    System.out.println("tilted");
                    //moveUp();
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
                System.out.println("button");
            }
        });
    }

    public void moveRight() {
        Path p = new Path();
        p.moveTo(x, y);
        x = x + dx;
        y = y + 0;
        p.rLineTo(dx, 0);
        //p.moveTo(x, y);
        objectAnimator = ObjectAnimator.ofFloat(imageView, "x", "y", p);
        objectAnimator.setDuration(200);

        objectAnimator.start();

    }

    public void moveLeft() {
        Path p = new Path();
        p.moveTo(x, y);
        x = x - dx;
        y = y + 0;
        p.rLineTo(-1*dx, 0);
        //p.moveTo(x, y);
        objectAnimator = ObjectAnimator.ofFloat(imageView, "x", "y", p);
        objectAnimator.setDuration(200);

        objectAnimator.start();
    }

    public void moveUp() {
        Path p = new Path();
        p.moveTo(x, y);
        x = x + 0;
        y = y + dy;
        p.rLineTo(0, dy);
        //p.moveTo(x, y);
        objectAnimator = ObjectAnimator.ofFloat(imageView, "x", "y", p);
        objectAnimator.setDuration(200);

        objectAnimator.start();
    }

    public void moveDown() {
        Path p = new Path();
        p.moveTo(x, y);
        x = x + 0;
        y = y -dy;
        p.rLineTo(0, -1*dy);
        //p.moveTo(x, y);
        objectAnimator = ObjectAnimator.ofFloat(imageView, "x", "y", p);
        objectAnimator.setDuration(200);

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