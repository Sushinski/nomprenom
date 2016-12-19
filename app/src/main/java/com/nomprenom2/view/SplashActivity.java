package com.nomprenom2.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.nomprenom2.model.NameRecord;
import com.nomprenom2.model.PrefsRecord;
import com.nomprenom2.utils.ActionEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String last_name_id = PrefsRecord.getStringValue("base_version");
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
        Log.i("event res = ", event.getMessage());
        Intent intent = new Intent(this, SearchResultActivity.class);
        startActivity(intent);
        finish();
    }



}
