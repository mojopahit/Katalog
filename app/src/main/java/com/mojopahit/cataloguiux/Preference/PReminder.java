package com.mojopahit.cataloguiux.Preference;

import android.content.Context;
import android.content.SharedPreferences;

public class PReminder {
    private String REMINDER_DAILY = "daily";
    private String REMINDER_TODAY = "today";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public PReminder(Context context) {
        String PREFS_NAME = "UserPref";
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setDaily(boolean status) {
        editor = preferences.edit();
        editor.putBoolean(REMINDER_DAILY, status);
        editor.apply();
    }

    public void setToday(boolean status) {
        editor = preferences.edit();
        editor.putBoolean(REMINDER_TODAY, status);
        editor.apply();
    }

    public boolean isDaily() {
        return preferences.getBoolean(REMINDER_DAILY, false);
    }

    public boolean isToday() {
        return preferences.getBoolean(REMINDER_TODAY, false);
    }
}
