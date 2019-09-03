package com.tang.binrry.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {


    //Unit Three Begin
    private static final String sTAG="QuizActivity";
    private static final String sKEY_INDEX = "index";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(sTAG,"onSaveInstanceState");
        outState.putInt(sKEY_INDEX,mCurrentIndex);
    }
    private boolean[] answered={false,false,false,false,false,false};
    private int sum=0,count=0;
    //Unit Three End

    //Unit Two Begin
    private Button mNextButton;
    private TextView mQuestionTextView;
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia,true),
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_mideast,false),
            new Question(R.string.question_africa,false),
            new Question(R.string.question_americas,true),
            new Question(R.string.question_asia,true)
    };

    private int mCurrentIndex = 0;
    //Unit Two End
    //Unit Two Challenge Begin
    private Button mbtn_pre;
    private ImageButton mimg_pre;
    private ImageButton mimg_next;
    // Unit Two Challenge End

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
                //Unit Two Begin Toast.makeText(QuizActivity.this,R.string.correct_toast,Toast.LENGTH_SHORT).show();
                checkAnswer(true);

                //Unit Two End
            }
        });
        mbtn_false=findViewById(R.id.btn_false);
        mbtn_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Unit Two Begin
              //.setGravity(Gravity.TOP,10,10);
                Toast toast=Toast.makeText(QuizActivity.this,R.string.incorrect_toast,Toast.LENGTH_SHORT);
                //Unit One Challenge
                toast.setGravity(Gravity.TOP,0,0);
                toast.show();
                */
                checkAnswer(false);
                //Unit Two End
            }
        });

        //Unit Two Begin
        mQuestionTextView=findViewById(R.id.question_textview);
        updateQuestion();
        mNextButton=findViewById(R.id.btn_next);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex=(mCurrentIndex+1)%mQuestionBank.length;
                updateQuestion();
            }
        });
        //Unit Two End

        //Unit Two Challenge Begin
        mbtn_pre=findViewById(R.id.btn_prev);
        mbtn_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentIndex==0) mCurrentIndex=mQuestionBank.length-1;
                else mCurrentIndex=(mCurrentIndex-1)%mQuestionBank.length;
                updateQuestion();
            }
        });

        mimg_pre=findViewById(R.id.img_prev);
        mimg_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentIndex==0) mCurrentIndex=mQuestionBank.length-1;
                else mCurrentIndex=(mCurrentIndex-1)%mQuestionBank.length;
                updateQuestion();
            }
        });
        mimg_next=findViewById(R.id.img_next);
        mimg_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex=(mCurrentIndex+1)%mQuestionBank.length;
                updateQuestion();
            }
        });
        // Unit Two Challenge End

        //Unit Three Begin
        if(savedInstanceState!=null)
        {
            mCurrentIndex=savedInstanceState.getInt(sKEY_INDEX,0);
        }

        //Unit Three End
    }

    //Unit Three Challenge Begin
    private void btnforbid(boolean isanswer)
    {
        if(isanswer==true)
    {
        mbtn_true.setEnabled(false);
        mbtn_false.setEnabled(false);
    }
        else
    {
        mbtn_true.setEnabled(true);
        mbtn_false.setEnabled(true);
    }
    }
    //Unit Three Challenge End

    //Unit Two Begin
    private void updateQuestion() {
        int question=mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        //Unit Three Challenge Begin
        boolean isanswer=answered[mCurrentIndex];
        btnforbid(isanswer);
        //Unit Three Challenge End
    }
    private void checkAnswer(boolean userPress)
    {
        boolean answerTrue=mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId=0;
        if(userPress==answerTrue)
        {
            //Unit Three Challenge Begin
            sum+=1;
            messageResId=R.string.correct_toast;
            //Unit Three Challenge End
        }
        else
        {
            messageResId=R.string.incorrect_toast;
        }
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
        //Unit Three Challenge Begin
        answered[mCurrentIndex]=true;
        count++;
        boolean isanswer=answered[mCurrentIndex];
        btnforbid(isanswer);
        if(count==mQuestionBank.length)
        {
            String s="你已答完所有题目，评分为："+sum*10/mQuestionBank.length*10+"%";
            Toast toast=Toast.makeText(this,s,Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP,30,500);
            toast.show();
        }
        //Unit Three Challenge End

    }
    //Unit Two End


}
