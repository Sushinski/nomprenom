package com.nomprenom2.view;

import android.animation.Animator;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.widget.TextView;

import com.nomprenom2.R;
import com.nomprenom2.interfaces.RestApi;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.pojo.NamePojo;
import com.nomprenom2.presenter.AbsPresenter;
import com.nomprenom2.presenter.SearchResultPresenter;
import com.nomprenom2.utils.ActionEvent;
import com.nomprenom2.utils.SearchedNamesAdapter;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.ActionBar.DISPLAY_SHOW_HOME;


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
    private List<NameRecord> names;
    private TextView search_result_descr_tw;
    private TextView title_tw;
    private TextView empty_tw;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        final View androidRobotView = findViewById(R.id.tr_icon);

        androidRobotView.setTransitionName("tr_icon");
        getWindow().setAllowEnterTransitionOverlap(true);

       // ab.setHomeAsUpIndicator(R.drawable.transform_icon);
        //ab.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_HOME | android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        presenter = new SearchResultPresenter(this);
        result_list_view = (RecyclerView) findViewById(R.id.names_result_list_view);


        search_result_descr_tw = (TextView) findViewById(R.id.search_result_descr);
        mLayoutManager = new LinearLayoutManager(this);
        result_list_view.setLayoutManager(mLayoutManager);
        title_tw = (TextView)findViewById(R.id.search_result_title);
        empty_tw = (TextView)findViewById(R.id.empty_list);

        init();
    }


    void init(){
        Intent data  = getIntent();
        String search_descr = "";
        if(data.hasExtra(MainActivity.REGIONS)) {
            regions = data.getStringArrayExtra(MainActivity.REGIONS);
            search_descr = getResources().getString(R.string.descr_regions) +
                    TextUtils.join(",", regions ) + "\n";
        }else
            regions = null;
        if(data.hasExtra(MainActivity.SEX)) {
            sex = data.getStringExtra(MainActivity.SEX);
            search_descr += getResources().getString(R.string.descr_sex) +
                    sex + "\n";
        }else
            sex = null;
        if(data.hasExtra(MainActivity.ZODIAC)) {
            zod = data.getStringExtra(MainActivity.ZODIAC);
            search_descr += getResources().getString(R.string.descr_zod) +
                    zod + "\n";
        }else
            zod = null;
        if(data.hasExtra(MainActivity.PATRONYMIC)) {
            patronymic = data.getStringExtra(MainActivity.PATRONYMIC);
            search_descr += getResources().getString(R.string.descr_patr) +
                    patronymic;
        }else
            patronymic = null;
        if(regions != null || sex != null || zod != null ||patronymic != null) {
            title_tw.setText(getResources().getText(R.string.search_results_for));
        }else{
            title_tw.setText(getResources().getText(R.string.names_list));
            search_descr = getResources().getString(R.string.empty_filter);
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

    @Override
    protected void onResume() {
        super.onResume();
        result_list_view.setVisibility(View.VISIBLE);
    }
}
