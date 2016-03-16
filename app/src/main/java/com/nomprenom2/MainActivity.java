package com.nomprenom2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.nomprenom2.model.GroupRecord;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.model.android_metadata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public final static String GROUP_ID_MSG = "com.nomprenom2.GROUP_ID";
    public static final int GROUP_REQUEST = 1;
    public ArrayAdapter<String> groups_adapter;
    public String[] regions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.title_screen3) {
            selectScreen3();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void selectRegion(View view) {
        Intent intent = new Intent(this, SelectedRegionActivity.class);
        intent.putExtra("regions", regions);
        startActivityForResult(intent, GROUP_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == GROUP_REQUEST) {
            if(data.hasExtra("regions")){
                regions = data.getStringArrayExtra("regions");
                setGroupList();
            }
        }
    }

    private void setGroupList(){
        groups_adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                regions);
        ListView region_list_view = (ListView) findViewById(R.id.selected_groups_list_view);
        region_list_view.setAdapter(groups_adapter);
    }

    private void selectScreen3()
    {
         Intent intent = new Intent(this, Screen3Activity.class);
         startActivity(intent);
    }
}
