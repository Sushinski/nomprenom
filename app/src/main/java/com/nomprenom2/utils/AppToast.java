package com.nomprenom2.utils;

import android.content.Context;
import android.widget.Toast;


public class AppToast {
    private Context context;

    public AppToast(Context con){ context = con; }

    public void showToast(String str){
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        toast.show();
    }
}
