package com.nomprenom2;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

public class NomprenomApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

}
