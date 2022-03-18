package com.ut3.roadrunner.game.threads;

import android.os.Handler;
import android.util.Log;

import com.ut3.roadrunner.game.GameView;
import com.ut3.roadrunner.game.model.Direction;

import java.util.List;
import java.util.Random;

public class UpdateThread extends Thread {

    private int updateTimer = 1000/20;

    private boolean running = false;
    private Handler updateHandler;

    private final GameView gameView;

    public UpdateThread(GameView gameView) {
        super();
        this.updateHandler = new Handler();
        this.gameView = gameView;
    }

    private Runnable doUpdate = new Runnable() {
        @Override
        public void run() {
            if (running){
                updateState();
                updateHandler.postDelayed(this, updateTimer);
            }
        }
    };

    public void setRefreshRate(int multiplier){
        this.updateTimer = this.updateTimer * 1/multiplier;
    }

    private void updateState(){this.gameView.getObj().move(Direction.DOWN);}

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
