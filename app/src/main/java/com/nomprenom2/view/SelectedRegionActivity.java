/*
 * created by Pavel Golubev golubev.pavel.spb@gmail.com
 * no license applied
 * You may use this file without any restrictions
 */

package com.nomprenom2.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.crashlytics.android.Crashlytics;
import com.nomprenom2.R;
import com.nomprenom2.model.GroupRecord;
import com.nomprenom2.utils.ColorUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import io.fabric.sdk.android.Fabric;

/**
 * Selects regions from list
 */
public class SelectedRegionActivity extends AppCompatActivity {

    public ListView region_list_view;
    public ArrayAdapter<GroupRecord> arrayAdapter;
    public Set<Integer> checked_set;

    public SelectedRegionActivity() {
        super();
    }


    /**
     * Initializes activity with data, sets toolbar.
     *Sets list checks fpr selected items
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_selected_region);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ColorUtils.initTeamColors(this);
        }
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

    /**
     * Processes menu item selection
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Sets activity result
     */
    private void setResult(){
        Intent data = new Intent();
        SparseBooleanArray checkedItemPositions = region_list_view.getCheckedItemPositions();
        ArrayList<String> checked = new ArrayList<>();
        for( int i = 0; i < checkedItemPositions.size(); i++){
            int key = checkedItemPositions.keyAt(i);
            if(checkedItemPositions.get(key)){
                GroupRecord rec = arrayAdapter.getItem(key);
                if (rec != null) {
                    checked.add(rec.group_name);
                }
            }
        }
        if(checked.size() != 0){
            data.putExtra(MainActivity.REGIONS, checked);
        }
        setResult(RESULT_OK, data);
    }

    /**
     * Processes back button
     */
    @Override
    public void onBackPressed(){
        setResult();
        super.onBackPressed();
    }
}
