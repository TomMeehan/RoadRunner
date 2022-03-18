package com.ut3.roadrunner.game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.ut3.roadrunner.R;
import com.ut3.roadrunner.game.model.Bonus;
import com.ut3.roadrunner.game.model.GameObject;
import com.ut3.roadrunner.game.model.MovingObstacle;
import com.ut3.roadrunner.game.model.Player;
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

    private int gameSpeed = 1;

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
        this.player = new Player(R.drawable.ic_car, windowSize.x/2 - 50 , windowSize.y/2  - 50,  generator.getSIZE()/2, generator.getSIZE()/2, windowSize);


    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        Path path = new Path();
        path.addCircle(this.player.getX() + this.player.getWidth()/2,this.player.getY() - this.player.getHeight()/2, this.player.getVision(), Path.Direction.CW);
        canvas.clipPath(path);

        if (canvas != null) {
            canvas.drawColor(Color.DKGRAY);
            for (GameObject obj : objects){
                VectorDrawableCompat graphics = VectorDrawableCompat.create(getContext().getResources(), obj.getResId(), null);
                graphics.setBounds(obj.getX(), obj.getY(),obj.getX() + obj.getWidth(), obj.getY() + obj.getHeight());
                canvas.translate(0, 0);
                graphics.draw(canvas);
            }
            VectorDrawableCompat graphics = VectorDrawableCompat.create(getContext().getResources(), this.player.getResId(), null);
            graphics.setBounds(this.player.getX(), this.player.getY(),this.player.getX() + this.player.getWidth(), this.player.getY() + this.player.getHeight());
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
        Rect playerRect = new Rect(player.getX(), player.getY(), player.getX() + player.getWidth(), player.getY() + player.getHeight());
        Rect oRect;
        for (GameObject o : objects) {
            if (playerRect.intersect(new Rect(o.getX(), o.getY(), o.getX() + o.getWidth(), o.getY() + o.getHeight()))) {
                this.handleCollision(o);
                return;
            }
        }
    }

    public void handleCollision(GameObject o) {
        if (o instanceof Obstacle) {
            endGame();
        } else if (o instanceof MovingObstacle) {
            endGame();
        } else if (o instanceof Bonus) {
            Bonus bonus = (Bonus) o;
            Log.d("handleCollision", "BONUS");

            /*
            player.setScoreMultiplier(bonus.getScoreMultiplier());
            Handler endScoreBonusHandler = new Handler();
            endScoreBonusHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    player.setScoreMultiplier(Player.BASE_SCORE_MULTIPLIER);
                }
            }, Bonus.DURATION);*/
        }
    }

    public int getGameSpeed() {
        return gameSpeed;
    }

    public void setGameSpeed(int multiplier) {
        this.drawThread.setRefreshRate(multiplier);
        this.updateThread.setRefreshRate(multiplier);
        this.gameSpeed = multiplier;
    }

    public void resetGameSpeed() {
        this.gameSpeed = 1;
        this.drawThread.resetDrawTimer();
        this.updateThread.resetUpdateTimer();
    }

    public List<GameObject> getObjects() {
        return objects;
    }

    public void setObjects(List<GameObject> objects) {
        this.objects = objects;
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

    public Player getPlayer() {
        return player;
    }

    public void endGame(){
        SharedPreferences sharedScore = getContext().getSharedPreferences("score",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedScore.edit();
        editor.putInt("score", player.getScore());
        editor.apply();
        Intent intent = new Intent(getContext(), EndingActivity.class);
        getContext().startActivity(intent);

        boolean retry = true;
        while (retry) {
            try {
                drawThread.setRunning(false);
                drawThread.join();
                updateThread.setRunning(false);
                updateThread.join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }
}
