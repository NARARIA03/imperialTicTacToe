package com.example.secondproject_2;

import static java.lang.Thread.sleep;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class HardAiGameActivity extends AppCompatActivity implements View.OnClickListener{
    private Button[] buttons = new Button[9]; // 1차원 배열로 3 by 3 버튼을 저장

    private boolean playerTurn = false; // 유저 턴인지, AI 턴인지 체크한다

    private int roundCount; // 비기는 상황 계산을 위한 변수

    // AI 대전 : P1 - 사람, P2 - AI
    // 2인 대전 : P1, P2
    private int playerPoints;
    private int AiPoints;
    private TextView textViewPlayer;
    private TextView textViewAi;
    public String[] board = {"", "", "", "", "", "", "", "", ""};

    private int round;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aigame);
        SoundManager.playClickSound(getApplicationContext());



        textViewPlayer = findViewById(R.id.text_view_p1);
        textViewAi = findViewById(R.id.text_view_p2);
        textViewPlayer.setText("Player : 0");
        textViewAi.setText("AI : 0");

        // 3판 2선, 5판 3선 데이터를 가져옴
        Intent getIntent = new Intent(getIntent());
        round = getIntent.getIntExtra("round", -1);

        // 3 By 3 버튼을 1차원 배열에 저장
        for (int i = 0; i < 9; i++) {
            String buttonID = "button_" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = findViewById(resID);
            buttons[i].setOnClickListener(this);
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
        // AI가 O를 마킹하며 선공
        if (!playerTurn) {
            // AI의 첫 수는 랜덤하게 작동
            int pos = (int) (Math.random() * 10000) % 9; // 위치 랜덤하게 0 ~ 8 사이 값으로 지정
            buttons[pos].setText("O"); // 그 위치 버튼에 text O 삽입
            roundCount++; // 라운드 수 1 증가
            playerTurn = true; // 유저 턴으로 변경

            // board 배열 업데이트
            for (int i = 0; i < 9; i++) {
                board[i] = buttons[i].getText().toString();
            }

            // 승무패 여부 체크
            if (check_win(board, "O")) {
                aiWins();
            } else if (check_win(board, "X")) {
                playerWins();
            } else if (roundCount == 9) {
                draw();
            }
        }
    }

    @Override
    public void onClick(View v) {
        // 클릭한 버튼에 문자가 채워져 있으면 함수 종료
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        // 유저 턴 (클릭하면 그 위치에 X 마킹)
        if (playerTurn) {
            ((Button) v).setText("X"); // 플레이어 턴이면 클릭한 버튼 V에 X 삽입
            roundCount++; // 라운드 수 1 증가
            playerTurn = false; // AI 턴으로 변경
            // board 배열 업데이트
            for (int i = 0; i < 9; i++) {
                board[i] = buttons[i].getText().toString();
            }

            // 승무패 여부 체크
            if (check_win(board, "O")) {
                aiWins();
            } else if(check_win(board, "X")) {
                playerWins();
            } else if(roundCount == 9) {
                draw();
            }
        }
        // onclick이 작동한 뒤에 실행될 코드 -> 유저가 클릭해서 X를 넣으면, 1초 뒤에 AI가 O를 입력한다
        // 배우지 않은 내용!
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 유저가 X를 마킹하면, 현재 보드 상태를 토대로 AI가 O를 마킹
                if (!playerTurn) {
                    // Ai가 수를 둠
                    int pos = minimax(board, 9, true)[0]; // minimax 알고리즘 통해 최적의 위치를 반환받음
                    if (pos != -1) {
                        buttons[pos].setText("O"); // 그 위치 버튼에 text O 삽입
                        roundCount++; // 라운드 수 1 증가
                        playerTurn = true; // 유저 턴으로 변경

                        // board 배열 업데이트
                        for (int i = 0; i < 9; i++) {
                            board[i] = buttons[i].getText().toString();
                        }

                        // 승무패 여부 체크
                        if (check_win(board, "O")) {
                            aiWins();
                        } else if(check_win(board, "X")) {
                            playerWins();
                        } else if(roundCount == 9) {
                            draw();
                        }
                    }
                }
            }
        }, 1000); // 1초간 지연
    }

    private void playerWins() {
        playerPoints++;
        if(playerPoints == round) {
            Intent intent = new Intent(HardAiGameActivity.this, WinnerActivity.class);
            intent.putExtra("winner", "Player");
            updatePointsText();
            resetBoard();
            startActivity(intent);
        } else {
            updatePointsText();
            resetBoard();
        }
    }

    private void aiWins() {
        AiPoints++;
        if(AiPoints == round) {
            Intent intent = new Intent(HardAiGameActivity.this, WinnerActivity.class);
            intent.putExtra("winner", "AI");
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

    private void updatePointsText() {
        textViewPlayer.setText("Player : " + playerPoints);
        textViewAi.setText("AI : " + AiPoints);
    }

    // 게임판 버튼 리스트와 스트링 리스트 리셋
    private void resetBoard() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
            board[i] = buttons[i].getText().toString();
        }
        roundCount = 0; // 라운드 0으로 초기화
        playerTurn = false; // AI 선공 유지

        // AI가 O를 마킹하며 선공
        if (!playerTurn) {
            // AI의 첫 수는 랜덤하게 작동
            int pos = (int) (Math.random() * 10000) % 9; // 위치 랜덤하게 0 ~ 8 사이 값으로 지정
            buttons[pos].setText("O"); // 그 위치 버튼에 text O 삽입
            roundCount++; // 라운드 수 1 증가
            playerTurn = true; // 유저 턴으로 변경

            // board 배열 업데이트
            for (int i = 0; i < 9; i++) {
                board[i] = buttons[i].getText().toString();
            }
            // 승무패 여부 체크
            if (check_win(board, "O")) {
                aiWins();
            } else if(check_win(board, "X")) {
                playerWins();
            } else if(roundCount == 9) {
                draw();
            }
        }

    }

    // 1차원 리스트로 구현된 게임판에서 player가 승리 시 true를 반환하는 메소드
    // board는 1차원으로 축소한 게임판 문자열, player는 O 또는 X,
    public boolean check_win(String[] board, String player) {
        if (board[0].equals(player) && board[1].equals(player) && board[2].equals(player)) {
            return true;
        } else if (board[3].equals(player) && board[4].equals(player) && board[5].equals(player)) {
            return true;
        } else if (board[6].equals(player) && board[7].equals(player) && board[8].equals(player)) {
            return true;
        } else if (board[0].equals(player) && board[3].equals(player) && board[6].equals(player)) {
            return true;
        } else if (board[1].equals(player) && board[4].equals(player) && board[7].equals(player)) {
            return true;
        } else if (board[2].equals(player) && board[5].equals(player) && board[8].equals(player)) {
            return true;
        } else if (board[0].equals(player) && board[4].equals(player) && board[8].equals(player)) {
            return true;
        } else {
            return (board[2].equals(player) && board[4].equals(player) && board[6].equals(player));
        }
    }

    // minimax 알고리즘에서 가중치를 메기기 위한 평가 함수
    public int evaluate(String[] board) {
        int score;
        if (check_win(board, "X")) { // 유저가 승리
            score = -1;
        } else if (check_win(board, "O")) { // AI가 승리
            score = 1;
        } else {
            score = 0;
        }
        return score;
    }

    // minimax 알고리즘 (AI 구현)
    // board는 1차원 배열로 구성된 String형 게임판, depth는 게임판 남은 공간 정도로 생각?
    public int[] minimax(String[] board, int depth, boolean AiTurn) {
        // 재귀 시마다 evaluate 값을 평가해서
        int score = evaluate(board);
        if (score == 1) {
            return new int[]{-1, score}; // AI가 이기면 이 값 반환
        }
        if (score == -1) {
            return new int[]{-1, score}; // 유저가 이기면 이 값 반환
        }
        if (depth == 0) {
            return new int[]{-1, 0}; // 비기면 이 값 반환
        }

        // AI 턴이라고 가정하면, 승리확률이 가장 높은 Max case를 선택해야 함
        if(AiTurn) {
            int bestScore = Integer.MIN_VALUE; // bestScore 값을 -무한대로 설정
            int bestMove = -1; // bestScore 값을 가지는 게임판에서의 위치를 우선 -1로 초기화

            // 모든 비어있는 셀에 대한 경우의 수를 체크하며 minimax 메소드를 재귀로 호출하며 스코어를 평가한다
            // 여기서는 AITurn이므로 MAX case를 선택한다
            for(int i=0; i<9; i++) {
                if(board[i].equals("")) {
                    board[i] = "O"; // 비어있는 셀 중 하나에 O를 삽입(AI턴)

                    // O를 삽입한 케이스에 대해 유저의 MIN case를 다시 체크해 Score값 반환
                    int currentScore = minimax(board, depth - 1, false)[1];
                    // O를 삽입한 하나의 케이스가 끝나면, board를 원래대로 돌려놓음
                    board[i] = "";

                    // 이제, 위에서 수행한 O 배치 하나당 가장 높은 승률을 가질 때 점수값과, 그 위치 index를 저장
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestMove = i;
                    }
                }
            }
            return new int[]{bestMove, bestScore}; // 최종적으로 위치와 스코어를 반환
        } else {
            // 여기부터는 AiTrun값이 False일 때 실행된다
            // 유저 턴이라고 가정하면, AI의 승리확률이 가장 낮은 MIN case를 선택해야 함
            int bestScore = Integer.MAX_VALUE; // bestScore 값을 +무한대로 설정
            int bestMove = -1; // bestScore 값을 가지는 게임판에서의 위치를 우선 -1로 초기화

            // 모든 비어있는 셀에 대한 경우의 수를 체크하며 minimax 메소드를 재귀로 호출하며 스코어를 평가한다
            // 여기서는 유저 Turn이므로, MIN case를 선택한다
            for(int i = 0; i < 9; i++) {
                if(board[i].equals("")) {
                    board[i] = "X"; // 비어있는 셀 중 하나에 X를 삽입(유저 턴)

                    // 유저가 X를 삽입한 케이스에 대해 AI의 MAX case를 다시 체크해 Score값 반환
                    int currentScore = minimax(board, depth - 1, true)[1];
                    // X를 삽입한 하나의 케이스가 끝나면, board를 원래대로 돌려놓음
                    board[i] = "";

                    // 이제, 위에서 수행한 X 배치 하나당 가장 낮은 승률을 가질 때 점수값과, 그 위치 index를 저장
                    if (currentScore < bestScore) {
                        bestScore = currentScore;
                        bestMove = i;
                    }
                }
            }
            return new int[]{bestMove, bestScore};
        }
    }
}

