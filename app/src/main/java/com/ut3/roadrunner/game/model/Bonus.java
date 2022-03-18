package com.ut3.roadrunner.game.model;

import android.graphics.Point;

public class Bonus extends GameObject{

    public static final int DURATION = 10000;

    private int scoreMultiplier;
    private int scoreToAdd;

    public Bonus(int resId, int x, int y, int width, int height, int scoreMultiplier, Point windowSize) {
        super(resId, x, y, width, height, windowSize);
        this.scoreMultiplier = scoreMultiplier;
    }

    @Override
    public void move(int speed){
        super.move(speed);
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

}
