package com.nomprenom2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import com.nomprenom2.model.GroupRecord;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.nomprenom2.utils.ListUtils.getCheckedPositions;

public class SelectedRegionActivity extends AppCompatActivity {

    public ListView region_list_view;
    public ArrayAdapter<GroupRecord> arrayAdapter;
    public Set<Integer> checked_set;

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
        region_list_view = (ListView) findViewById(R.id.select_region_list_view);
        // set clicks handler
        checked_set = new HashSet<>();
        region_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView checkedTextView = ((CheckedTextView) view);
                checkedTextView.setChecked(!checkedTextView.isChecked());
                if(checkedTextView.isChecked()){
                    checked_set.add(position);
                }else{
                    checked_set.remove(position);
                }
            }
        });
        List<GroupRecord> lst =  GroupRecord.getAll();
        arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice,
                 lst);
        region_list_view.setAdapter(arrayAdapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        List<GroupRecord> checked = new ArrayList<>();
        for (Integer i : checked_set) {
            GroupRecord rec = arrayAdapter.getItem(i);
            checked.add(rec);
        }
        data.putExtra("regions", checked.toArray());
        setResult(RESULT_OK, data);
        super.finish();
    }
}
