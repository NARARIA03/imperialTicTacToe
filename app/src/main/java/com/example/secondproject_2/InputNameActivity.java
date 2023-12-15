package com.example.secondproject_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class InputNameActivity extends AppCompatActivity {
    TextView titleText;

    private int round;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputname);

        titleText = (TextView) findViewById(R.id.titleText);
        titleText.setText("플레이어 이름 입력");
        titleText.setTextSize(40);

        Intent intent = new Intent(getIntent());
        round = intent.getIntExtra("round", -1);
    }

    public void onSubmit(View view) {
        // Get the entered names
        EditText userName1 = findViewById(R.id.userName1);
        EditText userName2 = findViewById(R.id.userName2);

        String userNameStr1 = userName1.getText().toString();
        String userNameStr2 = userName2.getText().toString();

        // Intent로 2인 플레이 모드로 이름 정보 넘겨준다
        Intent intent = new Intent(this, TwoPersonGameActivity.class);
        intent.putExtra("USERNAME1", userNameStr1);
        intent.putExtra("USERNAME2", userNameStr2);
        intent.putExtra("round", round);
        startActivity(intent);
    }
}