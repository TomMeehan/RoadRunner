package com.ut3.roadrunner.sensors;

import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import com.ut3.roadrunner.game.model.Player;

public class LightSensor implements SensorEventListener {

    private final int MAX_VALUE = 800;

    private Player player;
    private Point windowSize;

    public LightSensor(Player player, Point windowSize) {
        this.player = player;
        this.windowSize = windowSize;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float value = sensorEvent.values[0];
        value = (value/MAX_VALUE*windowSize.y);
        value = value < windowSize.y/4 ? windowSize.y/4 : value;
        this.player.setVision(value);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
