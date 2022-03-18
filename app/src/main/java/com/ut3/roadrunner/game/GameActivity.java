package com.ut3.roadrunner.game;

import android.app.Activity;
import android.graphics.Point;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {

    private GameView gameView;
    private SensorManager sm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Gyro
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        gameView = new GameView(this, size);
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
        super.onStop();
    }


}