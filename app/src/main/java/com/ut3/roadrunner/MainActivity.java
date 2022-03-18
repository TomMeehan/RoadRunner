package com.ut3.roadrunner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ut3.roadrunner.game.GameActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);

    }

}