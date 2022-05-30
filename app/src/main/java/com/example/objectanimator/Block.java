package com.example.objectanimator;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import java.io.Serializable;

public class Block {// implements Parcelable {

    private ImageView image;
    //private ObjectAnimator objectAnimator;
    private float defaultX;
    private float defaultY;
    private float x;
    private float y;

    private final int animationTime = 2400;

    public Block(Drawable d, int w, int h, Context c, float screenHeight, float screenWidth) {
        image = new ImageView(c);
        //image.setAdjustViewBounds(true);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        image.setLayoutParams(params);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        image.setImageDrawable(d);
        image.getLayoutParams().width = w;
        image.getLayoutParams().height = h;

        defaultX = screenWidth/2;
        defaultY = screenHeight/2-500;
        x = defaultX;
        y = defaultY;
        image.setY(y);
        image.setX(x);
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
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public void setWidth(int w) {
        image.getLayoutParams().width = w;
    }

    public void setHeight(int h) {
        image.getLayoutParams().height = h;
    }

    public void moveRight(int dx) {
        Path p = new Path();
        x = x + dx;
        p.moveTo(x, y);
        p.rLineTo(dx, 0);


        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(image, "x", "y", p);
        objectAnimator.setDuration(animationTime);

        objectAnimator.start();

    }

    public void moveLeft(int dx) {
        Path p = new Path();
        x = x - dx;
        p.moveTo(x, y);
        p.rLineTo(-1*dx, 0);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(image, "x", "y", p);
        objectAnimator.setDuration(animationTime);

        objectAnimator.start();

    }

    public void moveUp(int dy) {
        Path p = new Path();
        y = y - dy;
        p.moveTo(x, y);
        p.rLineTo(0, -1*dy);


        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(image, "x", "y", p);
        objectAnimator.setDuration(animationTime);

        objectAnimator.start();

    }

    public void moveDown(int dy) {
        Path p = new Path();
        y = y + dy;
        p.moveTo(x, y);
        p.rLineTo(0, dy);


        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(image, "x", "y", p);
        objectAnimator.setDuration(animationTime);

        objectAnimator.start();

    }

    public void reset() {
        Path p = new Path();
        setX(defaultX);
        setY(defaultY);
        p.moveTo(defaultX, defaultY);
    }

}