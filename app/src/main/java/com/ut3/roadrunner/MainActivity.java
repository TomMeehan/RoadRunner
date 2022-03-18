package com.ut3.roadrunner;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.ut3.roadrunner.game.GameActivity;
import com.ut3.roadrunner.game.RulesActivity;

public class MainActivity extends Activity {


    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 0);
        }

        TextView scoreValue = findViewById(R.id.scoreText);
        SharedPreferences sharedScore = this.getSharedPreferences("score", Context.MODE_PRIVATE);
        int score = sharedScore.getInt("score",0);
        scoreValue.setText(scoreValue.getText()+String.valueOf(score));

        mediaPlayer = MediaPlayer.create(this, R.raw.elevator);
        mediaPlayer.setVolume(100,100);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void startRulesActivity(View view) {
        Intent intent = new Intent(this, RulesActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop(){
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onStop();
    }

}