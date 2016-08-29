package com.nomprenom2.view;

import android.app.Activity;
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
import android.widget.TextView;

import com.nomprenom2.R;
import com.nomprenom2.interfaces.RestApi;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.pojo.NamePojo;
import com.nomprenom2.presenter.AbsPresenter;
import com.nomprenom2.presenter.SearchResultPresenter;
import com.nomprenom2.utils.SearchedNamesAdapter;


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


public class SearchResultActivity extends AppCompatActivity {
    public static final int SELECTED_NAMES_ID=1;
    public static final int SEARCH_NAMES_ID=2;
    final String base_url = "http://85.143.215.126/names/";
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

    public class LoggingInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            okhttp3.Response response = chain.proceed(request);
            return response;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // // TODO: 06.04.16  inject candidate
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
            search_descr = getResources().getString(R.string.empty_filter);
        }

        search_result_descr_tw.setText(search_descr);
        if( regions != null ) {
            for (String group : regions) {
                cacheRepoNames(zod != null ? zod : "0",  sex != null ? sex : "2", group);
            }
        }else{
            cacheRepoNames(zod != null ? zod : "0", sex != null ? sex : "2", "all");
        }
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
    public void onResume(){
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


    void cacheRepoNames(String zod, String sex, String group) {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(new LoggingInterceptor())
                .build();



        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        RestApi service = retrofit.create(RestApi.class);

        final Call<List<NameRecord>> call = service.getName(zod, sex, group);


        call.enqueue(new Callback<List<NameRecord>>() {
            @Override
            public void onResponse(Call<List<NameRecord>> call, Response<List<NameRecord>> response) {
                Debug.waitForDebugger();
                if(response.isSuccessful())
                {
                    try {
                        for (NameRecord n : response.body()) {
                            Log.i("Name", n.name);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<NameRecord>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
