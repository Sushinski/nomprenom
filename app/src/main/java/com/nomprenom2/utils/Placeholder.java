package com.nomprenom2.utils;
import android.text.TextUtils;

public class Placeholder {
    public static String getWherePlaceholders(int len){
        String[] placeholdersArray = new String[len];
        for (int i = 0; i < len; i++) {
            placeholdersArray[i] = "?";
        }
        return TextUtils.join(",", placeholdersArray);
    }
}
