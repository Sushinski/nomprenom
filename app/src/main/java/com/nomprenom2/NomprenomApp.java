/*
 * created by Pavel Golubev golubev.pavel.spb@gmail.com
 * no license applied
 * You may use this file without any restrictions
 */

package com.nomprenom2;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Main application class
 */
public class NomprenomApp extends Application {
    /**
     * Initiates Crashlitics and Activeandroid libraries
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        ActiveAndroid.initialize(this);
    }

    /**
     * Deactivates Activeandroid library
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

}
