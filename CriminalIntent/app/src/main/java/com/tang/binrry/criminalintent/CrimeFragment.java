package com.tang.binrry.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.UUID;

import static android.widget.CompoundButton.*;
//Unit Seven Begin
public class CrimeFragment extends Fragment {

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckbox;
    //Unit Eleven Challenge Begin
    private Button mbtn_to_first;
    private Button mbtn_to_last;

    //Unit Eleven Challenge End

    //Unit Ten Begin
    private static final String sARG_CRIME_ID = "crime_id";
    public static CrimeFragment newInstance(UUID crimeId)
    {
        Bundle args=new Bundle();
        args.putSerializable(sARG_CRIME_ID,crimeId);
        CrimeFragment fragment=new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    //Unit Ten End

    //Unit Twelve Begin
    private static final String sDIALOG_DATE="DialogDate";
    private static final int sREQUEST_DATE=0;
    private Button mTimeButton;
    private static final String sDIALOG_TIME="DialogTime";
    private static final int sREQUEST_TIME=0;
    //Unit Twelve End

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Unit Ten Begin
        UUID crimeId=(UUID)getArguments().getSerializable(sARG_CRIME_ID);
        mCrime=CrimeLab.get(getContext()).getCrime(crimeId);
        //Unit Ten End
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        //Unit Ten Begin
        mTitleField.setText(mCrime.getTitle());
        //Unit Ten End
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = (Button) v.findViewById(R.id.crime_date);
        updateDate();
        //Unit Twelve Begin
        //mDateButton.setEnabled(false);
        mDateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager=getFragmentManager();
                DatePickerFragment datePickerFragment= new DatePickerFragment().newInstance(mCrime.getDate());
                datePickerFragment.setTargetFragment(CrimeFragment.this,sREQUEST_DATE);
                datePickerFragment.show(manager,sDIALOG_DATE);
            }
        });
        //Unit Twelve End
        //Unit Twelve Challenge Begin
        mTimeButton=v.findViewById(R.id.crime_time);
        updateTime();
        mTimeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager=getFragmentManager();
                TimePickerFragment timePickerFragment=new TimePickerFragment().newInstance(mCrime.getDate());
                timePickerFragment.setTargetFragment(CrimeFragment.this,sREQUEST_TIME);
                timePickerFragment.show(manager,sDIALOG_TIME);
            }
        });
        //Unit Twelve Challenge End

        mSolvedCheckbox = (CheckBox) v.findViewById(R.id.crime_solved);
        //Unit Ten Begin
        mSolvedCheckbox.setChecked(mCrime.isSolved());
        //Unit Ten End
        mSolvedCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, 
                    boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        //Unit Eleven Challenge Begin
        mbtn_to_first=(Button) v.findViewById(R.id.btn_to_first);
        if(mCrime.getId().equals(CrimeListFragment.mcrime_num_first))
        {
            mbtn_to_first.setEnabled(false);
        }
        mbtn_to_first.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=CrimePagerActivity.newIntent(getActivity(),CrimeLab.getUUID(0));
                startActivity(intent);
            }
        });


        mbtn_to_last=(Button) v.findViewById(R.id.btn_to_last);
        if(mCrime.getId().equals(CrimeListFragment.mcrime_num_last))
        {
            mbtn_to_last.setEnabled(false);
        }
        mbtn_to_last.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=CrimePagerActivity.newIntent(getActivity(),CrimeLab.getUUID(CrimeListFragment.mcrime_num-1));
                startActivity(intent);
            }
        });
        //Unit Eleven Challenge End
        return v;
    }

    //Unit Twelve Begin
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Unit Twelve Challenge Begin
        if(resultCode==TimePickerFragment.sTIME_OK)
        {
            int hour=(int)data.getSerializableExtra(TimePickerFragment.sEXTRA_HOUR);
            int minute=(int)data.getSerializableExtra(TimePickerFragment.sEXTRA_MINUTE);
            mCrime.setHour(hour);
            mCrime.setMinute(minute);
            Log.i("Timer2",hour+":"+minute);
            updateTime();
        }
        //Unit Twelve Challenge End

        if(resultCode!= Activity.RESULT_OK)
        {
            return;
        }
        if(requestCode==sREQUEST_DATE)
        {
            Date date= (Date) data.getSerializableExtra(DatePickerFragment.sEXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        }

    }

    private void updateTime() {
        Log.i("Timer3",mCrime.getHour()+":"+mCrime.getMinute());
        mTimeButton.setText(mCrime.getHour()+":"+mCrime.getMinute());
    }

    private void updateDate() {
        mDateButton.setText(mCrime.getDateStr());
    }
    //Unit Twelve End
}
