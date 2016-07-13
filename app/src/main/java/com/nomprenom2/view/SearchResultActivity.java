package com.nomprenom2.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.nomprenom2.R;
import com.nomprenom2.interfaces.ActivityListener;
import com.nomprenom2.model.GroupRecord;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.model.ZodiacRecord;
import com.nomprenom2.presenter.AbsPresenter;
import com.nomprenom2.presenter.SearchResultPresenter;
import com.nomprenom2.utils.SearchedNamesAdapter;
import com.nomprenom2.utils.SelectedNameAdapter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchResultActivity extends AppCompatActivity {
    public static final int SELECTED_NAMES_ID=1;
    public static final int SEARCH_NAMES_ID=2;
    public static final int ADD_NAME_ID=3;
    private AbsPresenter presenter;
    private SearchedNamesAdapter arrayAdapter;
    private RecyclerView result_list_view;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] regions;
    private String sex;
    private String zod;
    private String patronymic;
    private List<String> names;
    private TextView search_result_descr_tw;
    private TextView title_tw;
    private TextView empty_tw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // // TODO: 06.04.16  inject candidate
        presenter = new SearchResultPresenter(this);
        result_list_view = (RecyclerView) findViewById(R.id.names_result_list_view);
        search_result_descr_tw = (TextView) findViewById(R.id.search_result_descr);
        mLayoutManager = new LinearLayoutManager(this);
        result_list_view.setLayoutManager(mLayoutManager);
        title_tw = (TextView)findViewById(R.id.search_result_title);
        empty_tw = (TextView)findViewById(R.id.empty_list);
    }

    @Override
    public void onResume(){
        Intent data  = getIntent();
        String search_descr = "";
        if(data.hasExtra(MainActivity.REGIONS)) {
            regions = data.getStringArrayExtra(MainActivity.REGIONS);
            search_descr = getResources().getString(R.string.descr_regions) +
                    TextUtils.join(",", regions ) + "\n";
        }
        if(data.hasExtra(MainActivity.SEX)) {
            sex = data.getStringExtra(MainActivity.SEX);
            search_descr += getResources().getString(R.string.descr_sex) +
                    sex + "\n";
        }
        if(data.hasExtra(MainActivity.ZODIAC)) {
            zod = data.getStringExtra(MainActivity.ZODIAC);
            search_descr += getResources().getString(R.string.descr_zod) +
                    zod + "\n";
        }
        if(data.hasExtra(MainActivity.PATRONYMIC)) {
            patronymic = data.getStringExtra(MainActivity.PATRONYMIC);
            search_descr += getResources().getString(R.string.descr_patr) +
                    patronymic;
        }
        if(regions != null || sex != null || zod != null ||patronymic != null) {
            title_tw.setText(getResources().getText(R.string.search_results_for));
        }else{
            title_tw.setText(getResources().getText(R.string.names_list));
        }

        search_result_descr_tw.setText(search_descr);
        names =  presenter.getNames(regions, sex, zod);
        if( names.isEmpty() )
            empty_tw.setVisibility(View.VISIBLE);
        else
            empty_tw.setVisibility(View.GONE);

        arrayAdapter = new SearchedNamesAdapter(this,
                R.layout.name_list_item_checked,
                names, patronymic, sex, zod);
        result_list_view.setAdapter(arrayAdapter);
        super.onResume();
    }

    @Override
    public void onNewIntent(Intent intent){
        setIntent(intent);
        super.onNewIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.add_name:
                addName();
                return true;
            case R.id.title_screen3: {
                showSelectedNames();
                return true;
            }
            case R.id.search_names: {
                searchNames();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    protected void showSelectedNames(){
        Intent intent = new Intent(this, SelectedNamesActivity.class);
        startActivity(intent);
    }

    protected void searchNames(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    protected void addName(){
        Intent intent = new Intent(this, AddNameActivity.class);
        startActivityForResult(intent, ADD_NAME_ID);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == ADD_NAME_ID) {
            names =  presenter.getNames(regions, sex, zod);
            arrayAdapter.notifyDataSetChanged();
        }
    }
}
