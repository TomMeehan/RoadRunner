package com.ut3.roadrunner.game.model;

public class MovingObstacle extends Obstacle {

    private Direction direction;
    private int speedMultiplier;

    public MovingObstacle(int resId, int x, int y, int width, int height, Direction direction, int speedMultiplier) {
        super(resId, x, y, width, height);
        this.direction = direction;
        this.speedMultiplier = speedMultiplier;
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
