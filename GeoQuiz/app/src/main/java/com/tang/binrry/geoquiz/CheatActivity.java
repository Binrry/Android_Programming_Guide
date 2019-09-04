package com.tang.binrry.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private Button mbtn_showanswer;
    private TextView mAnswer_tv;
    private boolean mAnswerIsTrue;
    private boolean mAnswerIsShown;

    private static final String EXTRA_ANSWER_IS_TRUE="ANSWER_IS_TRUE";
    private static final String EXTRA_ANSWER_SHOWN="ANSWER_SHOWN";
    private static final String sANSWER_IS_TRUE="ANSWER_IS_TRUE";
    private static final String sANSWER_SHOWN="ANSWER_SHOWN";

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean(sANSWER_IS_TRUE,mAnswerIsTrue);
        outState.putBoolean(sANSWER_SHOWN,mAnswerIsShown);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        if(savedInstanceState!=null)
        {
            mAnswerIsTrue=savedInstanceState.getBoolean(sANSWER_IS_TRUE,false);
            mAnswerIsShown=savedInstanceState.getBoolean(sANSWER_SHOWN,false);
            if(mAnswerIsTrue)
            {
                mAnswer_tv.setText(R.string.true_button);
            }
            else
            {
                mAnswer_tv.setText(R.string.false_button);
            }
        }
        mAnswerIsTrue=getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);
        mAnswer_tv=findViewById(R.id.answer_tv);

        mbtn_showanswer=findViewById(R.id.btn_showanswer);
        mbtn_showanswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerIsTrue)
                {
                    mAnswer_tv.setText(R.string.true_button);
                }
                else
                {
                    mAnswer_tv.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });
    }

    private void setAnswerShownResult(boolean b) {
        Intent data=new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN,b);
        mAnswerIsShown=b;
        setResult(RESULT_OK,data);
    }

    public static boolean wasAnswerShown(Intent result)
    {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }

    public static Intent newIntent(Context packageContext,boolean answerIsTrue)
    {
        Intent intent=new Intent(packageContext,CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue);
        return intent;
    }

}
