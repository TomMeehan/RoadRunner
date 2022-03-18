package com.ut3.roadrunner.model;

public class Player {

    private float posX;
    private float posY;

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public Player(float posX, float posY){
        this.posX = posX;
        this.posY = posY;
    }
}
