package com.ut3.roadrunner.game;

import android.content.Context;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.ut3.roadrunner.game.threads.DrawThread;
import com.ut3.roadrunner.game.threads.UpdateThread;

public class GameView  extends SurfaceView implements SurfaceHolder.Callback {

    private final DrawThread drawThread;
    private final UpdateThread updateThread;

    public GameView(Context context, Point WindowSize){
        super(context);
        drawThread = new DrawThread(getHolder(), this);
        updateThread = new UpdateThread();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        drawThread.start();
        drawThread.setRunning(true);
        updateThread.start();
        updateThread.setRunning(true);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                updateThread.setRunning(false);
                updateThread.join();
                drawThread.setRunning(false);
                drawThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }
}
