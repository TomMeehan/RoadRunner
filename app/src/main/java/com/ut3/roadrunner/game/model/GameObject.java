package com.ut3.roadrunner.game.model;

import android.graphics.Point;

public abstract class GameObject {

    public static final int STEP = 5;

    protected int resId;
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    protected Point windowSize;
    protected boolean isAlive;

    public GameObject(int resId, int x, int y, int width, int height, Point windowSize) {
        this.resId = resId;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.windowSize = windowSize;
        this.isAlive = true;
    }

    public void move(int speed){
        this.move(Direction.DOWN, speed);
    }

    public void move(Direction direction, int speed){
        switch (direction){
            case UP:
                this.y = this.y - (STEP*speed);
                break;
            case DOWN:
                this.y = this.y + (STEP*speed);
                break;
            case LEFT:
                this.x = this.x - (STEP*speed);
                break;
            case RIGHT:
                this.x = this.x + (STEP*speed);
                break;
        }
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public Point getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(Point windowSize) {
        this.windowSize = windowSize;
    }
}
