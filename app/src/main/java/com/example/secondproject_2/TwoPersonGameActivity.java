package com.example.secondproject_2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TwoPersonGameActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[][] buttons = new Button[3][3]; // 2차원 배열로 3 by 3 버튼을 저장

    private boolean player1Turn = true; // 유저 턴인지, AI 턴인지 체크한다

    private int roundCount; // 비기는 상황 계산을 위한 변수

    private int player1Points;
    private int player2Points;

    private String userName1;
    private String userName2;

    private int round;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    public TwoPersonGameActivity() {
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twopersongame);
        SoundManager.playClickSound(getApplicationContext());


        // InputName 액티비티에서 유저 이름값을 받아온다
        Intent intent = getIntent();
        userName1 = intent.getStringExtra("USERNAME1");
        userName2 = intent.getStringExtra("USERNAME2");
        round = intent.getIntExtra("round", -1);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);
        textViewPlayer1.setText(userName1 + " : 0");
        textViewPlayer2.setText(userName2 + " : 0");

        // 3 By 3 버튼을 2차원 배열에 저장
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        // 우상단의 뒤로가기 버튼
        Button backButton = findViewById(R.id.backbtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                finish();
                SoundManager.playClickSound(getApplicationContext());
            }
        });
    }

    @Override
    public void onClick(View v) {
        // 클릭한 버튼에 문자가 채워져 있으면 함수 종료, 문자가 없으면 O 또는 X를 순서에 따라 집어넣음
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }
        roundCount++;
        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
            SoundManager.playClickSound(getApplicationContext());
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }

    private void player1Wins() {
        player1Points++;
        if(player1Points == round) {
            Intent intent = new Intent(TwoPersonGameActivity.this, WinnerActivity.class);
            intent.putExtra("winner", userName1);
            updatePointsText();
            resetBoard();
            startActivity(intent);
        } else {
            updatePointsText();
            resetBoard();
        }
    }

    private void player2Wins() {
        player2Points++;
        if(player2Points == round) {
            Intent intent = new Intent(TwoPersonGameActivity.this, WinnerActivity.class);
            intent.putExtra("winner", userName2);
            updatePointsText();
            resetBoard();
            startActivity(intent);
        } else {
            updatePointsText();
            resetBoard();
        }
    }

    private void draw() {
        Toast.makeText(this, "비겼습니다!", Toast.LENGTH_LONG).show();
        resetBoard();
    }

    @SuppressLint("SetTextI18n")
    private void updatePointsText() {
        textViewPlayer1.setText(userName1 + " : " + player1Points);
        textViewPlayer2.setText(userName2 + " : " + player2Points);

    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
    }
}

