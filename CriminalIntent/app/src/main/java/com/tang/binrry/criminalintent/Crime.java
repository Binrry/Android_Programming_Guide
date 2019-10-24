package com.tang.binrry.criminalintent;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
//Unit Seven Begin
public class Crime {

    //Unit Twelve Challenge Begin
    private int mhour;
    private int mminute;
    public int getHour() {
        return mhour;
    }

    public void setHour(int mhour) {
        this.mhour = mhour;
    }

    public int getMinute() {
        return mminute;
    }

    public void setMinute(int minute) {
        this.mminute = minute;
    }
    //Unit Twelve Challenge End

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    //Unit Nine Challenge Begin
    private String mDateStr;
    //Unit Nine Challenge End

    //Unit Eight Challenge Begin
    private int mRequirePolice;
    public int getmRequirePolice() {
        return mRequirePolice;
    }

    public void setmRequirePolice(int mRequirePolice) {
        this.mRequirePolice = mRequirePolice;
    }
    //Unit Eight Challenge End

    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
        //Unit Nine Challenge Begin
        //"Sep 16,2019" DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE,MMMM dd,yyyy", Locale.ENGLISH);
        //"Monday,September 16,2019"
        mDateStr = dateFormat.format(mDate);
        //Unit Nine Challenge End
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(mDate);
        mhour=calendar.get(Calendar.HOUR_OF_DAY);
        mminute=calendar.get(Calendar.MINUTE);
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
        //Unit Twelve Begin
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE,MMMM dd,yyyy", Locale.ENGLISH);
        //"Monday,September 16,2019"
        mDateStr = dateFormat.format(mDate);
        //Unit Twelve End
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    //Unit Nine Challenge Begin
    public String getDateStr() {
        return mDateStr;
    }  //Unit Nine Challenge End
}
