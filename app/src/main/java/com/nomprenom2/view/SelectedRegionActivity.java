package com.nomprenom2.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.nomprenom2.R;
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
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         // get regions names
        region_list_view = (ListView) findViewById(R.id.select_region_list_view);
        // set clicks handler
        checked_set = new HashSet<>();
        List<GroupRecord> lst =  GroupRecord.getAll();
        arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice,
                 lst);
        region_list_view.setAdapter(arrayAdapter);
        region_list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        Intent intent = getIntent();
        if(intent.hasExtra(MainActivity.REGIONS)){
            String[] selected_rgns = intent.getStringArrayExtra(MainActivity.REGIONS);
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
        if(intent.hasExtra(MainActivity.SINGLE_REGION)){
            if(intent.getBooleanExtra(MainActivity.SINGLE_REGION, false)){
                region_list_view.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            }

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setResult(){
        Intent data = new Intent();
        long[] ch_ids = region_list_view.getCheckedItemIds();
        if(ch_ids.length != 0) {
            String[] checked = new String[ch_ids.length];
            int j = 0;
            for (Long i : ch_ids) {
                GroupRecord rec = arrayAdapter.getItem((int)i);
                checked[j++] = rec.group_name;
            }
            data.putExtra(MainActivity.REGIONS, checked);
        }
        setResult(RESULT_OK, data);
    }

    @Override
    public void onBackPressed(){
        setResult();
        super.onBackPressed();
    }
}
