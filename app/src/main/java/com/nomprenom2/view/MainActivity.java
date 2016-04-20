package com.nomprenom2.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.nomprenom2.R;
import com.nomprenom2.presenter.AbsPresenter;
import com.nomprenom2.presenter.MainPresenter;
import com.nomprenom2.utils.NothingSelectedSpinnerAdapter;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final int GROUP_REQUEST = 1;
    public static final int SEARCH_RESULT = 2;
    public ArrayAdapter<String> groups_adapter;
    public String[] regions;
    public String[] sex_sel;
    private Spinner spinner;
    private AbsPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sex_sel = getResources().getStringArray(R.array.sex_sels);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, sex_sel);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.sex_spinner);
        spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        // inject candidate
        presenter = new MainPresenter(this);
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

    public void searchNames(View view){
        Intent intent = new Intent(this, SearchResultActivity.class);
        if( regions != null )
            intent.putExtra("regions", regions);
        String sex = (String)spinner.getSelectedItem();
        if(sex != null)
            intent.putExtra("sex", sex);
        startActivity(intent);
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
         Intent intent = new Intent(this, SelectedNamesActivity.class);
         startActivity(intent);
    }
}
