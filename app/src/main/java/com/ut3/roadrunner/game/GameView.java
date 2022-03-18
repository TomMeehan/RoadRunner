package com.ut3.roadrunner.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.ut3.roadrunner.R;
import com.ut3.roadrunner.game.model.GameObject;
import com.ut3.roadrunner.game.model.Player;
import com.ut3.roadrunner.game.threads.DrawThread;
import com.ut3.roadrunner.game.threads.UpdateThread;
import com.ut3.roadrunner.sensors.GyroSensor;

import java.util.LinkedList;
import java.util.List;

public class GameView  extends SurfaceView implements SurfaceHolder.Callback {

    private final DrawThread drawThread;
    private final UpdateThread updateThread;

    private Player player;
    private Point windowSize;

    private List<GameObject> objects;

    private GyroSensor gyroSensor;

    public GameView(Context context, Point windowSize){
        super(context);

        //Surface
        getHolder().addCallback(this);
        setFocusable(true);

        this.windowSize = windowSize;

        drawThread = new DrawThread(getHolder(), this);
        updateThread = new UpdateThread(this);

        this.objects = new LinkedList<>();

        this.player = new Player(R.drawable.ic_rock, windowSize.x/2, windowSize.y/2, 100, 100);
        this.gyroSensor = new GyroSensor(this.player);

        //TESTS
        this.objects.add(new GameObject(R.drawable.ic_rock, windowSize.x/2, 0, 200, 200));
        this.setGameSpeed(3);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (canvas != null) {
            canvas.drawColor(Color.GRAY);
            for (GameObject obj : objects){
                VectorDrawableCompat graphics = VectorDrawableCompat.create(getContext().getResources(), obj.getResId(), null);
                graphics.setBounds(obj.getX() - obj.getWidth()/2, obj.getY(),obj.getX() + obj.getWidth()/2, obj.getY() + obj.getHeight());
                canvas.translate(0, 0);
                graphics.draw(canvas);
            }

        }
    }

    public void setGameSpeed(int multiplier) {
        this.drawThread.setRefreshRate(multiplier);
        this.updateThread.setRefreshRate(multiplier);
    }

    public List<GameObject> getObjects() {
        return objects;
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

    public void initializeSensors(SensorManager sm){
        this.gyroSensor = new GyroSensor(this.player);
        Sensor mMagneticField = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(gyroSensor, mMagneticField, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopSensors(SensorManager sm){
        sm.unregisterListener(gyroSensor, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }

}