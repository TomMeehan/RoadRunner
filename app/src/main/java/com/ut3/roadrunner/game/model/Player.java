package com.ut3.roadrunner.game.model;

import android.graphics.Point;

public class Player extends GameObject {

    private int score;
    private int stepMultiplier = 2;

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

    public int getScore(){
        return this.score;
    }

    public void addScore(int scoreToAdd){
        this.score += scoreToAdd;
    }
}
