package com.ut3.roadrunner.game.threads;

import android.os.Handler;
import android.util.Log;

import java.util.List;
import java.util.Random;

public class UpdateThread extends Thread {

    private int updateTimer = 1000/60;

    private boolean running = false;
    private Handler updateHandler;

    public UpdateThread() {
        super();
        this.updateHandler = new Handler();
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


    private void updateState(){}

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
