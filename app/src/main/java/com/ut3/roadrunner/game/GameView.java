package com.ut3.roadrunner.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.ut3.roadrunner.R;
import com.ut3.roadrunner.game.model.GameObject;
import com.ut3.roadrunner.game.model.Obstacle;
import com.ut3.roadrunner.game.model.Player;
import com.ut3.roadrunner.game.threads.DrawThread;
import com.ut3.roadrunner.game.threads.UpdateThread;
import com.ut3.roadrunner.sensors.GyroSensor;
import com.ut3.roadrunner.sensors.LightSensor;

import java.util.LinkedList;
import java.util.List;

public class GameView  extends SurfaceView implements SurfaceHolder.Callback {

    private final DrawThread drawThread;
    private final UpdateThread updateThread;
    private Player player;

    private Point windowSize;

    private ObjectGenerator generator;
    private List<GameObject> objects;

    private GyroSensor gyroSensor;
    private LightSensor lightSensor;

    public GameView(Context context, Point windowSize){
        super(context);

        //Surface
        getHolder().addCallback(this);
        setFocusable(true);

        //Window Size
        this.windowSize = windowSize;

        //Threads
        drawThread = new DrawThread(getHolder(), this);
        updateThread = new UpdateThread(this);

        //Ojects
        this.generator = new ObjectGenerator(windowSize);
        this.objects = new LinkedList<>();
        this.player = new Player(R.drawable.ic_purzen_a_cartoon_moon_rocket, windowSize.x/2, windowSize.y/2, 100, 100);


        //TESTS
        this.objects.add(new Obstacle(R.drawable.ic_rock, windowSize.x/2, 0, 200, 200));
        this.setGameSpeed(3);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        Path path = new Path();
        path.addCircle(this.player.getX() + this.player.getWidth()/2,this.player.getY() - this.player.getHeight()/2, this.player.getVision(), Path.Direction.CW);
        canvas.clipPath(path);

        if (canvas != null) {
            canvas.drawColor(Color.GRAY);
            for (GameObject obj : objects){
                VectorDrawableCompat graphics = VectorDrawableCompat.create(getContext().getResources(), obj.getResId(), null);
                graphics.setBounds(obj.getX(), obj.getY(),obj.getX() + obj.getWidth(), obj.getY() + obj.getHeight());
                canvas.translate(0, 0);
                graphics.draw(canvas);
            }
            VectorDrawableCompat graphics = VectorDrawableCompat.create(getContext().getResources(), this.player.getResId(), null);
            graphics.setBounds(this.player.getX() - this.player.getWidth()/2, this.player.getY(),this.player.getX() + this.player.getWidth()/2, this.player.getY() + this.player.getHeight());
            canvas.translate(0, 0);
            Paint score_text = new Paint(Color.rgb(255,0,0));
            score_text.setTextSize(100);
            canvas.drawText("Score "+String.valueOf(this.player.getScore()), (this.windowSize.x/3), 100,  score_text);
            graphics.draw(canvas);
        }
    }

    public void generateObjects() {
        this.generator.generate(this.objects);
    }

    public void checkCollisions(){
        // MAXIME IF COLLISION
        //player.handleCollision(object);
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
        sm.registerListener(this.gyroSensor, mMagneticField, SensorManager.SENSOR_DELAY_GAME);

        Sensor mLightSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        this.lightSensor = new LightSensor(this.player,mLightSensor.getMaximumRange());
        sm.registerListener(this.lightSensor,mLightSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public void stopSensors(SensorManager sm){
        sm.unregisterListener(this.gyroSensor, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        sm.unregisterListener(this.lightSensor,sm.getDefaultSensor(Sensor.TYPE_LIGHT));
    }

}
