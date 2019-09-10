package com.tang.binrry.criminalintent;

import android.support.v4.app.Fragment;
import android.util.Log;
//Unit Eigth Begin
public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        Log.i("Test","CrimeListActivity createF");
        return new CrimeListFragment();
    }
}
