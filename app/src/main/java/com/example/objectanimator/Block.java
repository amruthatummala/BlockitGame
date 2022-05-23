package com.example.objectanimator;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.io.Serializable;

public class Block {// implements Parcelable {

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

    // ignore this method for now, part of my failed attempts to stack blocks...
    public boolean stackBlock(Block other, int dx, int dy) {
        int[] coordsOther = new int[2];
        other.getImageView().getLocationOnScreen(coordsOther);
        int[] coordsThis = new int[2];
        getImageView().getLocationOnScreen(coordsThis);

        float ygap = coordsOther[1] - coordsThis[1]; //+ this.getHeight();
        System.out.println("height of this image view is " + this.getHeight());
        System.out.println("height of other image view is " + other.getHeight());
        float xgap = coordsOther[0] - coordsThis[0]; //+ this.getWidth();
        System.out.println("xgap is " + xgap);
        System.out.println("ygap is " + ygap);
        System.out.println("dx is " + dx);
        System.out.println("dy is " + dy);
        if (Math.abs(ygap) <= dy) {
            if (xgap > 0 && xgap <= dx) {
                this.setX(other.getX() - this.getWidth());
                this.setY(other.getY());
            } else if (xgap < 0 && xgap >= -15) {
                this.setX(other.getX() + this.getWidth());
                this.setY(other.getY());
            } else {
                this.setY(other.getY() + this.getHeight());
            }
            return true;
        }


//        if (Math.abs(xgap) <= dx && Math.abs(ygap) <= dy) {
//            System.out.println("stack block");
//            if (xgap <= this.getWidth()/3) {
//                this.setX(other.getX() - this.getWidth());
//            } else if (xgap <= (2/3) * this.getWidth()) {
//                this.setY(other.getY() + this.getHeight());
//            } else {
//                this.setX(other.getX() + other.getWidth());
//            }
//            return true;
//        }
        return false;
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


//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
////        private ImageView image;
////        private float defaultX;
////        private float defaultY;
////        private float x;
////        private float y;
//        dest.writeValue(image);
//        dest.writeFloat(defaultX);
//        dest.writeFloat(defaultY);
//        dest.writeFloat(x);
//        dest.writeFloat(y);
//    }
//    public static final Parcelable.Creator<Block> CREATOR
//            = new Parcelable.Creator<Block>() {
//        public Block createFromParcel(Parcel in) {
//            return new Block(in);
//        }
//
//        public Block[] newArray(int size) {
//            return new Block[size];
//        }
//    };
//
//
//    //        private ImageView image;
////        private float defaultX;
////        private float defaultY;
////        private float x;
////        private float y;
//    private Block(Parcel in) {
//        image = (ImageView) in.readValue(ImageView.class.getClassLoader());
//        defaultX = in.readFloat();
//        x = in.readFloat();
//        y = in.readFloat();
//        defaultY = in.readFloat();
//    }

}
