package com.arcsoft.arcfacedemo.util;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfigUtil {
    private static final String APP_NAME = "ArcFaceDemo";
    private static final String TRACK_ID = "trackID";

    public static void setTrackId(Context context, int trackId) {
        if (context == null) {
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putInt(TRACK_ID, trackId)
                .apply();
    }

    public static int getTrackId(Context context){
        if (context == null){
            return 0;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(TRACK_ID,0);
    }
}
