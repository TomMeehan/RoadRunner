package com.ut3.roadrunner.game.threads;

import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

import com.ut3.roadrunner.game.GameView;

public class DrawThread extends Thread{

    private final int BASE_DRAW_TIMER = 1000/120;

    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private Canvas canvas;
    private Handler drawHandler;
    private boolean running;
    private int drawTimer = BASE_DRAW_TIMER;


    private Runnable doDraw = new Runnable() {
        @Override
        public void run() {
            if (running) {
                drawCanvas();
                drawHandler.postDelayed(this, drawTimer);
            }
        }
    };

    public DrawThread(android.view.SurfaceHolder surfaceHolder, GameView gameView){
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
        this.drawHandler = new Handler();
    }

    private void drawCanvas(){
        canvas = null;

        try {
            canvas = this.surfaceHolder.lockCanvas();
            synchronized(surfaceHolder) {
                this.gameView.draw(canvas);
            }
        } catch (Exception e) {}
        finally {
            if (canvas != null) {
                try {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setRefreshRate(int multiplier){
        if (this.drawTimer == BASE_DRAW_TIMER){
            this.drawTimer = this.drawTimer * 1/multiplier;
        }

    }

    @Override
    public void run(){
        doDraw.run();
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void resetDrawTimer() {
        this.drawTimer = BASE_DRAW_TIMER;
    }
}
