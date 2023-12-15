package com.example.secondproject_2;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // 시작 화면 구현 (메인 텍스트, 초보AI, 고수AI, 2인 플레이 모드)
    Button howtoplay, easyAI, hardAI, twoPlay, exit;
    SoundManager mySoundManager = new SoundManager();
    TextView titleText;





    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SoundManager.playBackgroundSound(getApplicationContext());
        SoundManager.playClickSound(getApplicationContext());


        titleText = (TextView) findViewById(R.id.titleText);

        howtoplay = (Button)findViewById(R.id.howtoplay);
        easyAI = (Button) findViewById(R.id.easyAI);
        hardAI = (Button) findViewById(R.id.hardAI);
        twoPlay = (Button) findViewById(R.id.twoPlay);
        exit = (Button) findViewById(R.id.exit);






        // 메인 화면 버튼과 텍스트뷰에 텍스트 넣고 효과 적용
        titleText.setText("무적의 틱텍토");
        titleText.setTextSize(60);

        howtoplay.setText("How To Play");

        easyAI.setText("AI 대전 (쉬움)");

        hardAI.setText("AI 대전 (어려움)");

        twoPlay.setText("2인 플레이");

        exit.setText("게임 종료");


        // gamemode : 0 = 쉬운 AI 모드
        // gamemode : 1 = 어려운 AI 모드
        // gamemode : 2 = 2인 모드
        ;






        howtoplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameRulesActivity.class);
                startActivity(intent);
                SoundManager.playClickSound(getApplicationContext());


            }
        });


        easyAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RoundSelectActivity.class);
                intent.putExtra("gamemode", 0);
                startActivity(intent);

            }
        });

        hardAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RoundSelectActivity.class);
                intent.putExtra("gamemode", 1);
                startActivity(intent);

            }
        });

        twoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RoundSelectActivity.class);
                intent.putExtra("gamemode", 2);
                startActivity(intent);

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.runFinalization();
                System.exit(0);
                SoundManager.playClickSound(getApplicationContext());

            }
        });







    }



    }


