package com.example.order_food;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MainApplication extends Application {
    private static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
