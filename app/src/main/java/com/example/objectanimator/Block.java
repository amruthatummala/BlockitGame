package com.example.objectanimator;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class Block {

    private ImageView image;
    //private ObjectAnimator objectAnimator;
    private float defaultX;
    private float defaultY;
    private float x;
    private float y;

    public Block(Drawable d, int w, int h, Context c, float screenHeight, float screenWidth) {
        image = new ImageView(c);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        image.setLayoutParams(params);
        image.setImageDrawable(d);
        image.getLayoutParams().width = w;
        image.getLayoutParams().height = h;

        Path p = new Path();
        defaultX = screenWidth/2;
        defaultY = screenHeight/2;
        x = defaultX;
        y = defaultY;
        p.moveTo(defaultX, defaultY);

//        ConstraintLayout con = new ConstraintLayout(c);
//        con.addView(image);
//        con.setConstraintSet(new ConstraintSet());
    }

    public ImageView getImageView() {
        return image;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float tx) {
        x = tx;
        image.setX(x);
    }

    public void setY(float ty) {
        y = ty;
        image.setY(y);
    }

    public int getWidth() {
        return image.getLayoutParams().width;
    }

    public int getHeight() {
        return image.getLayoutParams().height;
    }

    public void moveRight(int dx) {
        Path p = new Path();
        x = x + dx;
        p.moveTo(x, y);
        p.rLineTo(dx, 0);
        //image.setX(x);
        //p.moveTo(x, y);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(image, "x", "y", p);
        objectAnimator.setDuration(2000);
        objectAnimator.start();

    }

    public void moveLeft(int dx) {
        Path p = new Path();
        x = x - dx;
        p.moveTo(x, y);
        p.rLineTo(-1*dx, 0);
        //image.setX(x);
        //p.moveTo(x, y);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(image, "x", "y", p);
        objectAnimator.setDuration(2000);
        objectAnimator.start();

    }

    public void moveUp(int dy) {
        Path p = new Path();
        y = y - dy;
        p.moveTo(x, y);
        p.rLineTo(0, -1*dy);
        //image.setY(y);
        //p.moveTo(x, y);


        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(image, "x", "y", p);
        objectAnimator.setDuration(2000);
        objectAnimator.start();

    }

    public void moveDown(int dy) {
        Path p = new Path();
        y = y + dy;
        p.moveTo(x, y);
        p.rLineTo(0, dy);
        //image.setY(y);
        //p.moveTo(x, y);


        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(image, "x", "y", p);
        objectAnimator.setDuration(2000);
        objectAnimator.start();

    }

    public void reset() {
        Path p = new Path();
        x = defaultX;
        y = defaultY;
        p.moveTo(defaultX, defaultY);
    }



}
