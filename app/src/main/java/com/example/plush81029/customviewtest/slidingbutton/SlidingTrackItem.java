//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.plush81029.customviewtest.slidingbutton;

public class SlidingTrackItem {
    private float X;
    private float Y;
    private long time;
    private float speed;

    public float getX() {
        return this.X;
    }

    public void setX(float x) {
        this.X = Utils.getRightFloat(Float.valueOf(x));
    }

    public float getY() {
        return this.Y;
    }

    public void setY(float y) {
        this.Y = Utils.getRightFloat(Float.valueOf(y));
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float speed) {
        this.speed = Utils.getRightFloat(Float.valueOf(speed));
    }

    public SlidingTrackItem(long time) {
        this.time = time;
    }

    public String toString() {
        return "[" + this.getX() + "," + this.getY() + "," + this.getTime() + "," + this.getSpeed() + "]";
    }
}
