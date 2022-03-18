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

    private final int BASE_SCORE_PER_TICK = 1;
    private final int BASE_GENERATION_TIMER = 3000;
    private final int BASE_UPDATE_TIMER = 1000/40;

    private int updateTimer = BASE_UPDATE_TIMER;
    private int generationTimer = BASE_GENERATION_TIMER;

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
        if (this.updateTimer == BASE_UPDATE_TIMER){
            this.updateTimer = this.updateTimer * 1/multiplier;
            this.generationTimer = this.generationTimer/(multiplier+(multiplier/2));
        }

    }

    private void updateState(){
        for (GameObject obj : gameView.getObjects()){
            obj.move(this.gameView.getGameSpeed());
        }
        if (this.canGenerate){
            this.gameView.generateObjects();
            this.canGenerate = false;
            this.generatorHandler.postDelayed(resetCanGenerate, this.generationTimer);
        }
        if(this.gameView.getPlayer().getVision() <450){
            this.gameView.getPlayer().addScore(BASE_SCORE_PER_TICK*2);
        }else{
            this.gameView.getPlayer().addScore(BASE_SCORE_PER_TICK);
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

    public void resetUpdateTimer() {
        this.updateTimer = BASE_UPDATE_TIMER;
        this.generationTimer = BASE_GENERATION_TIMER;
    }
}
