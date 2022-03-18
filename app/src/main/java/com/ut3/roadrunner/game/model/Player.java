package com.ut3.roadrunner.game.model;

public class Player extends GameObject {

    private int score;
    private int stepMultiplier = 2;

    public Player(int resId, int x, int y, int width, int height) {
        super(resId, x, y, width, height);
        this.score = 0;
    }

    @Override
    public void move(Direction direction){
        switch (direction) {
            case UP:
                setY(getY() - (STEP * stepMultiplier));
                break;
            case DOWN:
                setY(getY() + (STEP * stepMultiplier));
                break;
            case LEFT:
                setX(getX() - (STEP * stepMultiplier));
                break;
            case RIGHT:
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
