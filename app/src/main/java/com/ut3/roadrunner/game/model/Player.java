package com.ut3.roadrunner.game.model;

import android.util.Log;

import com.ut3.roadrunner.game.GameView;

public class Player extends GameObject {

    private int score;
    private final GameView gameView;

    public Player(int resId, int x, int y, int width, int height, GameView gameView) {
        super(resId, x, y, width, height);
        this.score = 0;
        this.gameView = gameView;
    }

    @Override
    public void move(Direction direction){
        GameObject object = null; /*isInCollision() == null*/
        if (object == null) {
            switch (direction) {
                case UP:
                    setY(getY() - STEP);
                    break;
                case DOWN:
                    setY(getY() + STEP);
                    break;
                case LEFT:
                    setX(getX() - STEP);
                    break;
                case RIGHT:
                    setX(getX() + STEP);
                    break;
            }
        } else {
            handleCollision(object);
            this.gameView.getObjects().remove(object);
        }
    }

    private void handleCollision(GameObject object) {
        if (object instanceof Bonus){
            // Bonus added
            Log.d("BONUS", "Bonus added");
        } else if (object instanceof Obstacle) {
            // Game lost
            Log.d("END", "you lost the game");
        }
    }

    public int getScore(){
        return this.score;
    }

    public void addScore(int scoreToAdd){
        this.score += scoreToAdd;
    }
}
