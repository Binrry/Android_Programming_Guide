package com.tang.binrry.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
    //Unit Ten Begin
    private static final int sREQUEST_CRIME=1;
    public void returnResult()
    {
        getActivity().setResult(Activity.RESULT_OK,null);
    }
    //Unit Ten End

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Test","CrimeListFragment oncreateview");
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view
                .findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if(mAdapter==null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }
        else
        {
            mAdapter.notifyDataSetChanged();
        }
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
            Intent intent=CrimeActivity.newIntent(getActivity(),mCrime.getId());
            startActivityForResult(intent,sREQUEST_CRIME);
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
    }
}
