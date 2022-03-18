package com.ut3.roadrunner;

import android.app.Activity;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;

import androidx.appcompat.app.AppCompatActivity;

import com.ut3.roadrunner.model.Player;
import com.ut3.roadrunner.sensors.GyroSensor;

public class MainActivity extends Activity {

    private GyroSensor gyroSensor;
    private SensorManager sm = null;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        this.gyroSensor = new GyroSensor();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        this.player = new Player(size.x/2, size.y/2);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor mMagneticField = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(gyroSensor, mMagneticField, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop(){
        sm.unregisterListener(gyroSensor, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        super.onStop();
    }
}