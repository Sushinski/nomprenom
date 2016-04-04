package com.nomprenom2.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nomprenom2.R;
import com.nomprenom2.model.GroupRecord;
import com.nomprenom2.model.NameRecord;

import java.util.List;

public class SearchResultActivity extends AppCompatActivity {
    ArrayAdapter<NameRecord> arrayAdapter;
    ListView result_list_view;
    public String[] regions;
    String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        result_list_view = (ListView) findViewById(R.id.names_result_list_view);
        Intent data  = getIntent();
        if(data.hasExtra("regions"))
            regions = data.getStringArrayExtra("regions");
        if(data.hasExtra("sex"))
            sex = data.getStringExtra("sex");

        List<NameRecord> lst =  NameRecord.getNames(regions, sex);
        arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice,
                lst);
        result_list_view.setAdapter(arrayAdapter);
        result_list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

}
