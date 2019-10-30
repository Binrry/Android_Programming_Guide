package com.tang.binrry.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tang.binrry.criminalintent.R;

import java.util.List;
import java.util.UUID;

//Unit Eigth Begin
public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private boolean mSubtitleVisible;
    private static final String SAVED_SUBTITLE_VISIBLE="subtitle";
    //Unit Ten Begin
    private static final int sREQUEST_CRIME=1;
    public void returnResult()
    {
        getActivity().setResult(Activity.RESULT_OK,null);
    }
    //Unit Ten End

    public static final String sCRIMENUMBER="crime_number";
    public static final String sCRIME_NUMBER_FIRST="crime_number_first";
    public static final String sCRIME_NUMBER_LAST="crime_number_last";
    public static final int sCRIME_NUMBER=4;
    public static int mcrime_num;
    public static int mcrime_num_first;
    public static int mcrime_num_last;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Test","CrimeListFragment oncreateview");
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view
                .findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState!=null)
        {
            mSubtitleVisible=savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        updateUI();
        Log.i("CimesNum",mcrime_num+":"+mcrime_num_first+":"+mcrime_num_last);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
        Log.i("CimesNum1",mcrime_num+":"+mcrime_num_first+":"+mcrime_num_last);
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        Log.i("CimesNums",crimes.size()+"");

        if(mAdapter==null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);

        }
        else
        {
            mAdapter.notifyDataSetChanged();
        }
        mcrime_num=crimes.size();
        mcrime_num_first=(int)mAdapter.getItemId(0);
        mcrime_num_last=(int)mAdapter.getItemId(mAdapter.getItemCount());
        Log.i("CimesNum*",mcrime_num+":"+mcrime_num_first+":"+mcrime_num_last);

        updateSubtitle();
    }

    private class CrimeHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Crime mCrime;

        private TextView mTitleTextView;
        private TextView mDateTextView;
        //Unit Nine Begin
        private ImageView mSolvedImageView;
        private Button mButton;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mSolvedImageView=itemView.findViewById(R.id.crime_solved);
            //Unit Eight Challenge Begin
            mButton=itemView.findViewById(R.id.button);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),"Calling police!", +Toast.LENGTH_SHORT).show();
                }
            });
            //Unit Eight Challenge End
        }

        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDateStr());
            mSolvedImageView.setVisibility(crime.isSolved()?View.VISIBLE:View.GONE);
            mButton.setVisibility(crime.getmRequirePolice()==1?View.VISIBLE:View.GONE);

        }

        @Override
        public void onClick(View view) {
            //Unit Ten Begin
           // Intent intent=CrimeActivity.newIntent(getActivity(),mCrime.getId());
            // startActivityForResult(intent,sREQUEST_CRIME);

            //Unit Eleven Begin
            Intent intent=CrimePagerActivity.newIntent(getActivity(),mCrime.getId());
            startActivity(intent);
            //Unit Eleven End

            //Unit Ten End
            
            //Unit Ten Challenge Begin
            int position=mCrimeRecyclerView.getChildViewHolder(view).getAdapterPosition();
            Log.i("butt", "click " + position);
            mCrimeRecyclerView.getAdapter().notifyItemChanged(position);

            //Unit Ten Challenge End

        }


    }
    
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CrimeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }
    }

    //Unit Thirteen Begin

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list,menu);

        MenuItem subtitleItem=menu.findItem(R.id.show_subtitle);
        if(mSubtitleVisible)
        {
            subtitleItem.setTitle(R.string.hide_subtitle);
        }
        else
        {
            subtitleItem.setTitle(R.string.show_subtitle);
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.new_crime:
                Crime crime=new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent intent=CrimePagerActivity.newIntent(getActivity(),crime.getId());
                startActivity(intent);
                return true;
            case R.id.show_subtitle:
                mSubtitleVisible=!mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle()
    {
        CrimeLab crimeLab=CrimeLab.get(getActivity());
        int crimeCount=crimeLab.getCrimes().size();
        String subtitle=getString(R.string.subtitle_format,crimeCount);
        if(!mSubtitleVisible)
        {
            subtitle=null;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE,mSubtitleVisible);
    }
}
