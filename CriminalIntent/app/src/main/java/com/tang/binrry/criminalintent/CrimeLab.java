package com.tang.binrry.criminalintent;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
//Unit Eigth Begin
public class CrimeLab {
    private static CrimeLab sCrimeLab;

    //Unit Eleven Challenge Begin
    private static UUID[] sUUIDS;
    //Unit Eleven Challenge End
    private List<Crime> mCrimes;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }

        return sCrimeLab;
    }

    public static UUID getUUID(int i)
    {
           return sUUIDS[i];
    }

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
        sUUIDS=new UUID[100];
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            //Unit Eleven Challenge Begin
            sUUIDS[i]=crime.getId();
            //Unit Eleven Challenge End
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            //Unit Eight Challenge Begin
            crime.setmRequirePolice(i % 13 == 0 ? 1 : 0);
            //Unit Eight Challenge End
            mCrimes.add(crime);
        }
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }

        return null;
    }


}
