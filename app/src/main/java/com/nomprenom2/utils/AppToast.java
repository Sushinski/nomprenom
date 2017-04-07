/*
 * created by Pavel Golubev golubev.pavel.spb@gmail.com
 * no license applied
 * You may use this file without any restrictions
 */

package com.nomprenom2.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast message wrapper class
 */
public class AppToast {
    private Context context;

    public AppToast(Context con) {
        context = con;
    }

    /**
     * Shows toast with message
     *
     * @param str Message to show
     */
    public void showToast(String str) {
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        toast.show();
    }
}
