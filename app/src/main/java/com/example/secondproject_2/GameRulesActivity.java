package com.example.secondproject_2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GameRulesActivity extends AppCompatActivity {
    Button backbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gamerules);
        SoundManager.playClickSound(getApplicationContext());



        backbtn = (Button) findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {
                finish();
                SoundManager.playClickSound(getApplicationContext());
            }
        });

    }


}