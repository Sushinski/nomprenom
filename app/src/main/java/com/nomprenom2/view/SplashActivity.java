package com.nomprenom2.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.nomprenom2.R;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.model.PrefsRecord;
import com.nomprenom2.utils.ActionEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static com.nomprenom2.model.PrefsRecord.BASE_SERV_IP_ADDR;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        String last_name_id = PrefsRecord.getStringValue(PrefsRecord.LAST_UPD_NAME_ID);
        if (last_name_id == null)
            last_name_id = "0";
        String base_addr = PrefsRecord.getStringValue(BASE_SERV_IP_ADDR);
        NameRecord.refreshNamesCache(base_addr, last_name_id);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy(){
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(ActionEvent event) {
        if(event.getType() == ActionEvent.TYPE_LOAD_NEW_NAMES && event.isSuccessful())
            moveToNextActivity();
    }

    private void moveToNextActivity(){
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        findViewById(R.id.loading_text).setVisibility(View.GONE);
        Intent intent = new Intent(this, SearchResultActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final View androidRobotView = findViewById(R.id.tr_icon);
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(this, androidRobotView, "trans_icon");
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

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
