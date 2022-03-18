package com.ut3.roadrunner.game.model;

import android.graphics.Point;

public class Player extends GameObject {

    private int score;
    private int stepMultiplier = 2;
    private float vision;

    public Player(int resId, int x, int y, int width, int height, Point windowSize) {
        super(resId, x, y, width, height, windowSize);
        this.score = 0;
    }

    public void move(Direction direction){
        switch (direction) {
            case UP:
                if (getY() > 0)
                    setY(getY() - (STEP * stepMultiplier));
                break;
            case DOWN:
                if ((getY() + getHeight()) < this.windowSize.y)
                    setY(getY() + (STEP * stepMultiplier));
                break;
            case LEFT:
                if (getX() > 0)
                    setX(getX() - (STEP * stepMultiplier));
                break;
            case RIGHT:
                if ((getX() + getWidth()) < this.windowSize.x)
                    setX(getX() + (STEP * stepMultiplier));
                break;
            }
    }

    public float getVision() {
        return vision;
    }

    public void setVision(float luminosite) {
        this.vision = luminosite;
    }

    public int getScore(){
        return this.score;
    }

    public void addScore(int scoreToAdd){
        this.score += scoreToAdd;
    }
}
