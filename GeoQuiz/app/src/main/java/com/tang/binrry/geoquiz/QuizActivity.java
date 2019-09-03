package com.tang.binrry.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mbtn_true;
    private Button mbtn_false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mbtn_true=findViewById(R.id.btn_true);
        mbtn_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuizActivity.this,R.string.correct_toast,Toast.LENGTH_SHORT).show();
            }
        });
        mbtn_false=findViewById(R.id.btn_false);
        mbtn_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //.setGravity(Gravity.TOP,10,10);
                Toast toast=Toast.makeText(QuizActivity.this,R.string.incorrect_toast,Toast.LENGTH_SHORT);
                //Challenge
                toast.setGravity(Gravity.TOP,0,0);
                toast.show();
            }
        });
    }
}
