package com.nomprenom2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nomprenom2.model.Test;

public class SelectedRegionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_region);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView names_list_view = (ListView) findViewById(R.id.select_region_list_view);
        names_list_view.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Test.names));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    }

}
