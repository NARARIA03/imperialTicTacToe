package com.example.secondproject_2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WinnerActivity extends AppCompatActivity {





    TextView title, name;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);
        SoundManager.stopBackgroundSound();
        SoundManager.playClickSound(getApplicationContext());

        SoundManager.playWinSound(getApplicationContext());

        Intent getIntent = new Intent(getIntent());
        String winner = getIntent.getStringExtra("winner");

        title = (TextView) findViewById(R.id.titleText);
        title.setText("Winner");
        title.setTextSize(60);

        name = (TextView)findViewById(R.id.nameText);
        name.setText(winner);
        name.setTextSize(40);

        back = (Button) findViewById(R.id.backbtn);
        back.setText("메인 화면으로");
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WinnerActivity.this, MainActivity.class);
                startActivity(intent);
                SoundManager.playClickSound(getApplicationContext());
            }
        });



    }
}
