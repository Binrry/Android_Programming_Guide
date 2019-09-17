package com.tang.binrry.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

//Unit Seven Begin
public class CrimeActivity extends SingleFragmentActivity {
    //Unit Eigth Begin
    @Override
    protected Fragment createFragment() {
        //Unit Ten Begin
        UUID crimeId=(UUID)getIntent().getSerializableExtra(sEXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
        //Unit Ten End
    }
    //Unit Eigth End

    //Unit Ten Begin
    private static final String sEXTRA_CRIME_ID="Crime_Id";
    public static Intent newIntent(Context context, UUID crimeId)
    {
        Intent intent=new Intent(context,CrimeActivity.class);
        intent.putExtra(sEXTRA_CRIME_ID,crimeId);
        return intent;
    }
    //Unit Ten End
}
