package com.ut3.roadrunner.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.ut3.roadrunner.game.model.Player;

public class LightSensor implements SensorEventListener {

    private float maxValue;
    private Player player;

    public LightSensor(Player player, float maximumRange) {
        this.player = player;
        this.maxValue = maximumRange;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float value = sensorEvent.values[0];
        this.player.setVision(value);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
