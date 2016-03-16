package com.nomprenom2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import com.nomprenom2.model.GroupRecord;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        region_list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        String[] selected_rgns = getIntent().getStringArrayExtra("regions");
        if( selected_rgns != null) {
            int pos = 0;
            List<String> sel_lst = Arrays.asList(selected_rgns);
            for (GroupRecord rec : lst) {
                if (sel_lst.contains(rec.group_name)) {
                    region_list_view.setItemChecked(pos, true);
                    checked_set.add(pos);
                }
                pos++;
            }
        }
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
        String[] checked = new String[checked_set.size()];
        int j = 0;
        for (Integer i : checked_set) {
            GroupRecord rec = arrayAdapter.getItem(i);
            checked[j++] = rec.group_name;
        }
        data.putExtra("regions", checked);
        setResult(RESULT_OK, data);
        super.finish();
    }
}
