package com.ut3.roadrunner.game.model;

import android.graphics.Point;

public class MovingObstacle extends Obstacle {

    private Direction direction;
    private int speedMultiplier;

    public MovingObstacle(int resId, int x, int y, int width, int height, Direction direction, int speedMultiplier, Point windowSize) {
        super(resId, x, y, width, height, windowSize);
        this.direction = direction;
        this.speedMultiplier = speedMultiplier;
    }

    @Override
    public void move(){
        super.move();
        if (this.direction.equals(Direction.LEFT)){
            this.x = this.x - (GameObject.STEP * this.speedMultiplier);
        } else {
            this.x = this.x + (GameObject.STEP * this.speedMultiplier);
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getSpeedMultiplier() {
        return speedMultiplier;
    }

    public void setSpeedMultiplier(int speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }
}
