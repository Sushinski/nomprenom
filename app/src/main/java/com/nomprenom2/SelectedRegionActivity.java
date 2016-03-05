package com.nomprenom2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nomprenom2.model.NameRecord;

import java.util.ArrayList;

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
         // todo get desired arrays
        // ArrayAdapter<NameRecord> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dbHelper.getAllNames(new ArrayList<Integer>()));

        //ListView names_list_view = (ListView) findViewById(R.id.select_region_list_view);
        //names_list_view.setAdapter(arrayAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    }
}
