package com.tang.binrry.criminalintent;

import android.support.v4.app.Fragment;
//Unit Seven Begin
public class CrimeActivity extends SingleFragmentActivity {
    //Unit Eigth Begin
    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
    //Unit Eigth End
}
