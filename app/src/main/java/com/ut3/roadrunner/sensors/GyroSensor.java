package com.ut3.roadrunner.sensors;

import static java.lang.Math.abs;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import com.ut3.roadrunner.game.model.Direction;
import com.ut3.roadrunner.game.model.Player;


public class GyroSensor implements SensorEventListener {

    private float[] firstValues = new float[2];
    private Player player;

    public GyroSensor(Player player) {
        this.player = player;
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        int sensor = sensorEvent.sensor.getType();
        float[] values = sensorEvent.values;

        synchronized (this) {
            if (sensor == Sensor.TYPE_ACCELEROMETER) {
                if(firstValues==null){
                    for (int i = 0; i < 2; i++) {
                        firstValues[i] = values[i];
                    }
                }
                float x = values[0];
                float y = values[1];

                if ( abs(x - firstValues[0]) > 2 && x>firstValues[0] ) {
                   Log.d("Gyro", String.valueOf(Direction.LEFT));
                   this.player.move(Direction.LEFT);
                }else if ( abs(x - firstValues[0]) >2 && x<firstValues[0]) {
                    Log.d("Gyro", String.valueOf(Direction.RIGHT));
                    this.player.move(Direction.RIGHT);
                }

                if ( abs(y - firstValues[1]) > 2 && y < firstValues[1] ) {
                    Log.d("Gyro", String.valueOf(Direction.UP));
                    this.player.move(Direction.UP);
                }else if ( abs(y - firstValues[1]) > 2 && y > firstValues[1] ) {
                    Log.d("Gyro", String.valueOf(Direction.DOWN));
                    this.player.move(Direction.DOWN);
                }

            }
        }
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
