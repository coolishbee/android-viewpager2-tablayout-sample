package com.viewpager.tablayout.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "ViewPagerLog";

    private SharedPreferences mPref;

    public static final String PREF_DAY = "PREF_DAY";
    public static final String PREFERENCES_NAME = "view_pager_preference";
    private static final String DATE_FORMAT = "dd";

    private String strSDFormatDay = "0";
    private boolean bCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        long currentTime = System.currentTimeMillis();
        Date TodayDate = new Date(currentTime);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        strSDFormatDay = sdf.format(TodayDate);

        Log.d(TAG, strSDFormatDay);

        mPref = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        String strDay = mPref.getString(PREF_DAY, "0");

        if((Integer.parseInt(strSDFormatDay) - Integer.parseInt(strDay)) != 0)
        {
            new ViewPagerDialog.Builder(this)
                    .OnPositiveClicked((isChecked) -> {
                        if(bCheck)
                        {
                            mPref.edit().putString(PREF_DAY, strSDFormatDay).apply();
                        }
                    })
                    .OnTodayCloseClicked( (isChecked) -> {
                        Log.d(TAG, String.valueOf(isChecked));
                        bCheck = isChecked;
                    }).build();
        }
    }
}