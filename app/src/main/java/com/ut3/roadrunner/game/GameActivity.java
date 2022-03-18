package com.ut3.roadrunner.game;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import com.ut3.roadrunner.R;

public class GameActivity extends Activity {

    private GameView gameView;
    private SensorManager sm = null;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        //musique
        mediaPlayer = MediaPlayer.create(this, R.raw.bigrock);
        mediaPlayer.setVolume(100,100);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        gameView = new GameView(this, size);
        int score = gameView.getPlayer().getScore();
        SharedPreferences sharedScore = this.getSharedPreferences("score", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedScore.edit();
        editor.putInt("score", score);
        editor.apply();

        // Gyro
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);


        this.setContentView(gameView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.initializeSensors(sm);
    }

    @Override
    protected void onStop(){
        gameView.stopSensors(sm);
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onStop();
    }


}