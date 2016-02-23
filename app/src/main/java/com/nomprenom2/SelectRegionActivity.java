package com.nomprenom2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nomprenom2.NamesBaseContract.GroupRecord;
import com.nomprenom2.NamesBaseContract.NameRecord;
import com.nomprenom2.models.name_record;

import java.util.ArrayList;

public class SelectRegionActivity extends AppCompatActivity {
    private ListView obj;
    public SelectRegionActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_region_actvity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DbHelper hlpr = new DbHelper(this);
        ArrayList<name_record> lst = hlpr.getAllNames(new ArrayList<Integer>());
        ArrayAdapter<name_record> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lst);
        obj = (ListView)findViewById(R.id.select_region_list_view);
        obj.setAdapter(arrayAdapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}
