package com.example.spendwise;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class UserStore {

    private static final String PREFS = "spendwise_prefs";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_THEME = "theme_mode";

    public static final String DEMO_NAME = "Ali Student";
    public static final String DEMO_EMAIL = "student@uni.edu";
    public static final String DEMO_PASSWORD = "Student@123";

    private final SharedPreferences prefs;

    public UserStore(Context context) {
        prefs = context.getApplicationContext()
                .getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        if (!prefs.contains(KEY_EMAIL)) {          // seed hard-coded demo user
            saveUser(DEMO_NAME, DEMO_EMAIL, DEMO_PASSWORD);
        }
    }

    public void saveUser(String name, String email, String password) {
        prefs.edit()
                .putString(KEY_NAME, name)
                .putString(KEY_EMAIL, email)
                .putString(KEY_PASSWORD, password)
                .apply();
    }

    public boolean isEmailRegistered(String email) {
        return email.equalsIgnoreCase(prefs.getString(KEY_EMAIL, ""));
    }

    public boolean checkLogin(String email, String password) {
        return isEmailRegistered(email) && password.equals(prefs.getString(KEY_PASSWORD, ""));
    }

    public void updatePassword(String newPassword) {
        prefs.edit().putString(KEY_PASSWORD, newPassword).apply();
    }

    public String getName()  { return prefs.getString(KEY_NAME, ""); }
    public String getEmail() { return prefs.getString(KEY_EMAIL, ""); }

    public int getThemeMode() {
        return prefs.getInt(KEY_THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    public void setThemeMode(int mode) {
        prefs.edit().putInt(KEY_THEME, mode).apply();
    }
}