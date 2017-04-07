/*
 * created by Pavel Golubev golubev.pavel.spb@gmail.com
 * no license applied
 * You may use this file without any restrictions
 */

package com.nomprenom2.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.nomprenom2.R;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.model.PrefsRecord;
import com.nomprenom2.utils.ActionEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.fabric.sdk.android.Fabric;

import static com.nomprenom2.model.PrefsRecord.BASE_SERV_IP_ADDR;

/**
 * Shows logo and database update progress bar
 */
public class SplashActivity extends AppCompatActivity {
    public static final String TRANSITION_NAME = "com.nomprenom.view.trans_icon";
    /**
     * Initiates activity; reads database version and send request for updated base content;
     * Registers EventBus subscriber for get base updating finish acknowledgement
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        String last_name_id = PrefsRecord.getStringValue(PrefsRecord.LAST_UPD_NAME_ID);
        if (last_name_id == null)
            last_name_id = "0";
        String base_addr = PrefsRecord.getStringValue(BASE_SERV_IP_ADDR);
        NameRecord.refreshNamesCache(base_addr, last_name_id);
        EventBus.getDefault().register(this);
    }

    /**
     * Unregisters EventBus subscribe
     */
    @Override
    public void onDestroy(){
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * Processes EventBus subscription event
     * @param event - event type (only one TYPE_LOAD_NEW_NAMES is used )
     */
    @Subscribe
    public void onEvent(ActionEvent event) {
        if(event.getType() == ActionEvent.TYPE_LOAD_NEW_NAMES)
            moveToNextActivity();
    }

    /**
     * Hides base updating progress
     * Initiates app icon transition animation
     */
    private void moveToNextActivity(){
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        findViewById(R.id.loading_text).setVisibility(View.GONE);
        Intent intent = new Intent(this, SearchResultActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final View anim_view = findViewById(R.id.tr_icon);
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(this, anim_view, TRANSITION_NAME);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    /**
     * Sets custom layout when orientation chaged
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_splash_land);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_splash);
        }
    }
}
