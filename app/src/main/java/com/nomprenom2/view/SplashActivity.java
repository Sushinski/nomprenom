package com.nomprenom2.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeImageTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
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
        Intent intent = new Intent(this, SearchResultActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final View androidRobotView = findViewById(R.id.tr_icon);
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(this, androidRobotView, "trans_icon");
            startActivity(intent, options.toBundle());
        }else{
            // start the new activity
            startActivity(intent);
        }


    }



}
