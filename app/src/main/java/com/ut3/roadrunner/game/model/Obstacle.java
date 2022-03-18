package com.ut3.roadrunner.game.model;

import android.graphics.Point;

public class Obstacle extends GameObject {

    public Obstacle(int resId, int x, int y, int width, int height, Point windowSize) {
        super(resId, x, y, width, height, windowSize);
    }

    @Override
    public void move(int speed){
        super.move(speed);
        if (this.y - this.height >= windowSize.y) {
            this.isAlive = false;
        }
    }
}
