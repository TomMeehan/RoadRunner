package com.ut3.roadrunner.game.threads;

import android.os.Handler;
import android.util.Log;

import com.ut3.roadrunner.game.GameView;
import com.ut3.roadrunner.game.model.Direction;
import com.ut3.roadrunner.game.model.GameObject;
import com.ut3.roadrunner.game.model.MovingObstacle;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class UpdateThread extends Thread {

    private final int GENERATION_TIMER = 2000;

    private int updateTimer = 1000/20;

    private boolean running = false;
    private Handler updateHandler;

    //Object generation
    private Handler generatorHandler;
    private boolean canGenerate = true;

    private final GameView gameView;

    public UpdateThread(GameView gameView) {
        super();
        this.updateHandler = new Handler();
        this.generatorHandler = new Handler();
        this.gameView = gameView;
    }

    private Runnable doUpdate = new Runnable() {
        @Override
        public void run() {
            if (running){
                updateState();
                checkCollisions();
                destroyDeadObjects();
                updateHandler.postDelayed(this, updateTimer);
            }
        }
    };

    private Runnable resetCanGenerate = new Runnable() {
        @Override
        public void run() {
            canGenerate = true;
        }
    };

    public void setRefreshRate(int multiplier){
        this.updateTimer = this.updateTimer * 1/multiplier;
    }

    private void updateState(){
        for (GameObject obj : gameView.getObjects()){
            obj.move();
        }
        if (this.canGenerate){
            this.gameView.generateObjects();
            this.canGenerate = false;
            this.generatorHandler.postDelayed(resetCanGenerate, GENERATION_TIMER);
        }
    }

    private void checkCollisions(){
        this.gameView.checkCollisions();
    }

    private void destroyDeadObjects(){
        this.gameView.setObjects(this.gameView.getObjects()
                                                .stream()
                                                .filter(gameObject -> gameObject.isAlive())
                                                .collect(Collectors.toList()));
    }

    @Override
    public void run() {
        doUpdate.run();
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
