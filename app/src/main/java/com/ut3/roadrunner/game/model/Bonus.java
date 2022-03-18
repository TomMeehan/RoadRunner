package com.ut3.roadrunner.game.model;

import android.graphics.Point;

public class Bonus extends GameObject{

    public static final int DURATION = 10000;

    private int scoreMultiplier;
    private int speedMultiplier;
    private int scoreToAdd;

    public Bonus(int resId, int x, int y, int width, int height, int scoreMultiplier, int speedMultiplier, int scoreToAdd, Point windowSize) {
        super(resId, x, y, width, height, windowSize);
        this.scoreMultiplier = scoreMultiplier;
        this.speedMultiplier = speedMultiplier;
        this.scoreToAdd = scoreToAdd;
    }

    @Override
    public void move(){
        super.move();
        if (this.y - this.height >= windowSize.y) {
            this.isAlive = false;
        }
    }

    public int getScoreMultiplier() {
        return scoreMultiplier;
    }

    public void setScoreMultiplier(int scoreMultiplier) {
        this.scoreMultiplier = scoreMultiplier;
    }

    public int getSpeedMultiplier() {
        return speedMultiplier;
    }

    public void setSpeedMultiplier(int speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    public int getScoreToAdd() {
        return scoreToAdd;
    }

    public void setScoreToAdd(int scoreToAdd) {
        this.scoreToAdd = scoreToAdd;
    }
}
