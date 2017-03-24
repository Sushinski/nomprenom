package com.nomprenom2.utils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class ColorUtils {
    private static int active_ab_color;
    private final static String TEAM_COLORS[] = new String[]{
            "#00838F", "#f41d6c", "#611df4", "#58ecb3", "#00beec"
    };

    public static  void initTeamColors(AppCompatActivity activity){
        active_ab_color = Color.parseColor(TEAM_COLORS[4]);
        initActionBarColor(activity, active_ab_color);
        initStatusBarColor(activity, active_ab_color);
    }

    private static void initStatusBarColor(AppCompatActivity activity, int teamColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().setStatusBarColor(calcStatusBarColor(teamColor));
        }
    }

    private static void initActionBarColor(AppCompatActivity activity, int teamColor){
        ActionBar ab = activity.getSupportActionBar();
        if( ab != null ) {
            ab.setBackgroundDrawable(new ColorDrawable(teamColor));
        }
    }

    private static int calcStatusBarColor(int actionBarColor) {
        float[] hsv = new float[3];
        Color.colorToHSV(actionBarColor, hsv);
        hsv[1] = hsv[1] + 0.1f;
        hsv[2] = hsv[2] - 0.1f;
        int argbColor = Color.HSVToColor(hsv);
        String hexColor = String.format("#%08X", argbColor);
        return Color.parseColor(hexColor);
    }

}
