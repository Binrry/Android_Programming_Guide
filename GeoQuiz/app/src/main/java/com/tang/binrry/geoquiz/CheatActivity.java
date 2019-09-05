package com.tang.binrry.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//Unit Five Begin
public class CheatActivity extends AppCompatActivity {

    //Unit Six Challenge Begin
    private TextView mVersion_tv;
    //Unit Six Challenge End

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

                //Unit Six Begin
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                int cx=mbtn_showanswer.getWidth()/2;
                int cy=mbtn_showanswer.getHeight()/2;
                float radius=mbtn_showanswer.getWidth();
                Animator anim=ViewAnimationUtils.createCircularReveal(mbtn_showanswer,cx,cy,radius,0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                       // Toast.makeText(CheatActivity.this, "End", Toast.LENGTH_LONG).show();
                        mbtn_showanswer.setVisibility(View.INVISIBLE);
                    }
                });
                anim.start();
                }
                else
                {
                    mbtn_showanswer.setVisibility(View.INVISIBLE);
                }
                //Unit Six End
            }
        });
        //Unit Six Challenge Begin
        mVersion_tv=findViewById(R.id.tv_version);
        mVersion_tv.setText("API Level "+Build.VERSION.SDK_INT);
        //Unit Six Challenge End
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
