package com.tang.binrry.criminalintent;
//Unit Twelve Challenge Begin
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.Timer;

public class TimePickerFragment extends DialogFragment {

    private static final String sARG_TIME="time";
    public static final String sEXTRA_HOUR="com.tang.binrry.criminalintent.hour";
    public static final String sEXTRA_MINUTE="com.tang.binrry.criminalintent.minute";
    public static final String sEXTRA_DAT="com.tang.binrry.criminalintent.dat";
    public static final int sTIME_OK=2;

    private TimePicker mTimePicker;

    public static TimePickerFragment newInstance(Date timer)
    {
        Bundle args=new Bundle();
        args.putSerializable(sARG_TIME,timer);
        TimePickerFragment fragment=new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Date time= (Date) getArguments().getSerializable(sARG_TIME);
        final Calendar calendar=Calendar.getInstance();
        calendar.setTime(time);
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time,null);
        mTimePicker=v.findViewById(R.id.dialog_time_picker);
        mTimePicker.setHour(hour);
        mTimePicker.setMinute(minute);
        return new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.time_picker_title).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int hour = mTimePicker.getHour();
                int minute=mTimePicker.getMinute();
                calendar.set(Calendar.HOUR_OF_DAY,hour);
                calendar.set(Calendar.MINUTE,minute);
                Log.i("Timer",hour+":"+minute);
                sendResult(sTIME_OK, calendar);
            }
        }).create();
    }

    private void sendResult(int resultCode,Calendar calendar)
    {
        if (getTargetFragment()==null)
        {
            return;
        }
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        Log.i("Timer1",hour+":"+minute);
        Intent intent=new Intent();
        intent.putExtra(sEXTRA_HOUR,hour);
        intent.putExtra(sEXTRA_MINUTE,minute);
       // intent.putExtra();
        intent.putExtra(sEXTRA_DAT, calendar.getTime());

        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }

}
