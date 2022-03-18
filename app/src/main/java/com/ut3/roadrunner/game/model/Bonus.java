package com.ut3.roadrunner.game.model;

import android.graphics.Point;

public class Bonus extends GameObject{

    private int scoreMultiplier;
    private int speedMultiplier;

    public Bonus(int resId, int x, int y, int width, int height, int scoreMultiplier, int speedMultiplier, Point windowSize) {
        super(resId, x, y, width, height, windowSize);
        this.scoreMultiplier = scoreMultiplier;
        this.speedMultiplier = speedMultiplier;
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
}
