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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nomprenom2.R;
import com.nomprenom2.presenter.AbsPresenter;
import com.nomprenom2.presenter.MainPresenter;
import com.nomprenom2.utils.NothingSelectedSpinnerAdapter;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final int GROUP_REQUEST = 1;
    public static final int SEARCH_RESULT = 2;
    public static String PATRONYMIC = "patronymic";
    private static String[] empty_arr_item;
    public ArrayAdapter<String> groups_adapter;
    public String[] regions;
    public String[] sex_sel;
    public String[] zod_sel;
    private Spinner sex_spinner;
    private Spinner zod_spinner;
    private AbsPresenter presenter;
    EditText patr_tw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        empty_arr_item =
                new String[]{getResources().getString(R.string.not_selected_region)};
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        patr_tw = (EditText) findViewById(R.id.input_first_name);
        setSupportActionBar(toolbar);
        sex_sel = getResources().getStringArray(R.array.sex_sels);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, sex_sel);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex_spinner = (Spinner) findViewById(R.id.sex_spinner);
        sex_spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        // zodiac spinner
        zod_sel = getResources().getStringArray(R.array.zod_sels);
        ArrayAdapter<String> zod_adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, zod_sel);
        zod_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zod_spinner = (Spinner) findViewById(R.id.zodiac_spinner);
        zod_spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        zod_adapter,
                        R.layout.zodiac_spinner_row_nothing_selected,
                        this));
        // inject candidate
        presenter = new MainPresenter(this);
        setGroupList();
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
        String sex = (String)sex_spinner.getSelectedItem();
        if(sex != null)
            intent.putExtra("sex", sex);
        String zod = (String)zod_spinner.getSelectedItem();
        if(zod != null)
            intent.putExtra("zod", zod);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == GROUP_REQUEST) {
            if(data.hasExtra("regions")){
                regions = data.getStringArrayExtra("regions");
            }else{
                regions = null;
            }
            setGroupList();
        }
    }

    private void setGroupList(){
        groups_adapter = new ArrayAdapter<>(this,
                R.layout.rowlayout, R.id.label,
                (regions == null) ?
                        empty_arr_item :
                        regions);
        ListView region_list_view = (ListView) findViewById(R.id.selected_groups_list_view);
        region_list_view.setAdapter(groups_adapter);
    }

    private void selectScreen3()
    {
        Intent intent = new Intent(this, SelectedNamesActivity.class);
        intent.putExtra(PATRONYMIC,patr_tw.getText());
        startActivity(intent);
    }
}
