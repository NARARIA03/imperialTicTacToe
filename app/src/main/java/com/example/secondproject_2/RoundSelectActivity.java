package com.example.secondproject_2;

import static android.view.View.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class RoundSelectActivity extends AppCompatActivity {

    TextView title;
    Button bo5, bo3, back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roundselect);
        SoundManager.playClickSound(getApplicationContext());



        title = (TextView) findViewById(R.id.titleText);
        title.setText("라운드 수 선택");
        title.setTextSize(50);

        bo3 = (Button) findViewById(R.id.bo3);
        bo3.setText("3판 2선승");
        bo5 = (Button) findViewById(R.id.bo5);
        bo5.setText("5판 3선승");
        back = (Button) findViewById(R.id.backbtn);
        back.setText("뒤로가기");



        Intent getIntent = new Intent(getIntent());
        int gamemode = getIntent.getIntExtra("gamemode", -1);


        bo3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gamemode == 0) {
                    Intent intent = new Intent(RoundSelectActivity.this, AiGameActivity.class);
                    intent.putExtra("round", 2);
                    startActivity(intent);
                } else if (gamemode == 1) {
                    Intent intent = new Intent(RoundSelectActivity.this, HardAiGameActivity.class);
                    intent.putExtra("round", 2);
                    startActivity(intent);
                } else if (gamemode == 2) {
                    Intent intent = new Intent(RoundSelectActivity.this, InputNameActivity.class);
                    intent.putExtra("round", 2);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(RoundSelectActivity.this, MainActivity.class);
                    intent.putExtra("round", 2);
                    startActivity(intent);


                }
                SoundManager.playClickSound(getApplicationContext());
            }
        });

        bo5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gamemode == 0) {
                    Intent intent = new Intent(RoundSelectActivity.this, AiGameActivity.class);
                    intent.putExtra("round", 3);
                    startActivity(intent);
                } else if (gamemode == 1) {
                    Intent intent = new Intent(RoundSelectActivity.this, HardAiGameActivity.class);
                    intent.putExtra("round", 3);
                    startActivity(intent);
                } else if (gamemode == 2) {
                    Intent intent = new Intent(RoundSelectActivity.this, InputNameActivity.class);
                    intent.putExtra("round", 3);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(RoundSelectActivity.this, MainActivity.class);
                    intent.putExtra("round", 3);
                    startActivity(intent);


                }
                SoundManager.playClickSound(getApplicationContext());
            }
        });

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                SoundManager.playClickSound(getApplicationContext());

            }

        });
    }
}
