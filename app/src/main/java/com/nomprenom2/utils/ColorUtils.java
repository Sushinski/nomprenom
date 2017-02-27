package com.nomprenom2.utils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.nomprenom2.R;

public class ColorUtils {
    private static int active_ab_color;
    public final static String TEAM_COLORS[] = new String[]{
            "#833706", "#CE6902", "#F8900C", "#FCAF10", "#FDCE42",
            "#A70A0A", "#F00E0B", "#FF573D", "#FF7E5E", "#FAA089",
            "#9E014E", "#D90759", "#F4396A", "#FF659A", "#FF90B2",
            "#3200B9", "#550FE9", "#703DFF", "#785FFF", "#A392FF",
            "#004297", "#0868C8", "#0A82EC", "#52ADF8", "#78BBF8",
            "#0A5564", "#00838F", "#0495B3", "#14B0D1", "#3CCEED",
            "#1A854F", "#1CA963", "#34CB7F", "#56D394", "#8AE6B8",
            "#128D71", "#18AE8C", "#19CFA6", "#6BE0C5", "#99EFDC"
    };

    public static  void initTeamColors(AppCompatActivity activity){
        active_ab_color = Color.parseColor(TEAM_COLORS[26]);
        initActionBarColor(activity, active_ab_color);
        initStatusBarColor(activity, active_ab_color);
    }

    public static void initStatusBarColor(AppCompatActivity activity, int teamColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().setStatusBarColor(calcStatusBarColor(teamColor));
        }
    }

    public static void initActionBarColor(AppCompatActivity activity, int teamColor){
        ActionBar ab = activity.getSupportActionBar();
        if( ab != null ) {
            ab.setBackgroundDrawable(new ColorDrawable(teamColor));
        }
    }

    public static int calcStatusBarColor(int actionBarColor) {
        float[] hsv = new float[3];
        Color.colorToHSV(actionBarColor, hsv);
        hsv[1] = hsv[1] + 0.1f;
        hsv[2] = hsv[2] - 0.1f;
        int argbColor = Color.HSVToColor(hsv);
        String hexColor = String.format("#%08X", argbColor);
        return Color.parseColor(hexColor);
    }

    public static int getActiveColor(){
        return active_ab_color;
    }
}
