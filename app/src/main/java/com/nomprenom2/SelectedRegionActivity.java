package com.nomprenom2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nomprenom2.model.GroupRecord;
import com.nomprenom2.model.NameRecord;

import java.util.ArrayList;
import java.util.List;

public class SelectedRegionActivity extends AppCompatActivity {

    public SelectedRegionActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_region);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         // get regions names
        List<GroupRecord> lst =  GroupRecord.getAll();
        ArrayAdapter<GroupRecord> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                 lst);
        ListView region_list_view = (ListView) findViewById(R.id.select_region_list_view);
        region_list_view.setAdapter(arrayAdapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    }
}
