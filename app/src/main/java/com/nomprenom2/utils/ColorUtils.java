/*
 * created by Pavel Golubev golubev.pavel.spb@gmail.com
 * no license applied
 * You may use this file without any restrictions
 */

package com.nomprenom2.utils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Helper class for working with app colors
 */
public class ColorUtils {
    private final static String TEAM_COLORS[] = new String[]{
            "#00838F", "#f41d6c", "#611df4", "#58ecb3", "#00beec"
    };

    /**
     * inits team color
     *
     * @param activity
     */
    public static void initTeamColors(AppCompatActivity activity) {
        int active_ab_color = Color.parseColor(TEAM_COLORS[4]);
        initActionBarColor(activity, active_ab_color);
        initStatusBarColor(activity, active_ab_color);
    }

    /**
     * Init activity status bar with color
     *
     * @param activity
     * @param teamColor
     */
    private static void initStatusBarColor(AppCompatActivity activity, int teamColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().setStatusBarColor(calcStatusBarColor(teamColor));
        }
    }

    /**
     * inits activity action bar with color
     *
     * @param activity
     * @param teamColor
     */
    private static void initActionBarColor(AppCompatActivity activity, int teamColor) {
        ActionBar ab = activity.getSupportActionBar();
        if (ab != null) {
            ab.setBackgroundDrawable(new ColorDrawable(teamColor));
        }
    }

    /**
     * calculates color offtet
     *
     * @param actionBarColor
     * @return
     */
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
