package com.ut3.roadrunner;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

        SharedPreferences sharedScore = this.getSharedPreferences("scores", Context.MODE_PRIVATE);
        TextView scoreValue = findViewById(R.id.scoreText);
        int bestScore = sharedScore.getInt("bestScore",0);
        scoreValue.setText(scoreValue.getText()+String.valueOf(bestScore));
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void startRulesActivity(View view) {
        Intent intent = new Intent(this, RulesActivity.class);
        startActivity(intent);
    }

}