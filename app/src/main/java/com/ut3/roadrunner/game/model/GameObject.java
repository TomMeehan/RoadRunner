package com.ut3.roadrunner.game.model;

public class GameObject {

    public static final int STEP = 5;

    private int resId;
    private int x;
    private int y;
    private int width;
    private int height;

    public GameObject(int resId, int x, int y, int width, int height) {
        this.resId = resId;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move(Direction direction){
        switch (direction){
            case UP:
                this.y = this.y - STEP;
                break;
            case DOWN:
                this.y = this.y + STEP;
                break;
            case LEFT:
                this.x = this.x - STEP;
                break;
            case RIGHT:
                this.x = this.x + STEP;
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
}
