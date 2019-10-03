package com.tang.binrry.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
        mDateButton.setText(mCrime.getDate().toString());
        mDateButton.setEnabled(false);

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
        if(mCrime.getTitle().endsWith("#0"))
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
        if(mCrime.getTitle().endsWith("#99"))
        {
            mbtn_to_last.setEnabled(false);
        }
        mbtn_to_last.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=CrimePagerActivity.newIntent(getActivity(),CrimeLab.getUUID(99));
                startActivity(intent);
            }
        });

        //Unit Eleven Challenge End
        return v;
    }
}
