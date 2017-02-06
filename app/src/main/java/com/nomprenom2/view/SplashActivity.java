package com.nomprenom2.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeImageTransform;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.nomprenom2.R;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.model.PrefsRecord;
import com.nomprenom2.utils.ActionEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class SplashActivity extends AppCompatActivity {
    final static int FINISH_CODE  = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setAllowEnterTransitionOverlap(true);
        setContentView(R.layout.activity_splash);
        String last_name_id = PrefsRecord.getStringValue(PrefsRecord.LAST_UPD_NAME_ID);
        if(last_name_id == null)
            last_name_id = "0";
        NameRecord.refreshNamesCache(last_name_id);
    }

    @Override
    public void onResume(){
        EventBus.getDefault().register(this);
        super.onResume();
    }

    @Override
    public void onPause(){
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Subscribe
    public void onEvent(ActionEvent event){
       // Log.i("event res = ", event.getMessage());

        Intent intent = new Intent(this, SearchResultActivity.class);
        final View androidRobotView = findViewById(R.id.tr_icon);
        ActivityOptions options = ActivityOptions
                .makeSceneTransitionAnimation(this, androidRobotView, "trans_icon");
        // start the new activity
        startActivity(intent, options.toBundle());
        finish();
    }



}
