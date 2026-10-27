package com.example.spendwise;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

public class SpendWiseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(new UserStore(this).getThemeMode());
    }
}