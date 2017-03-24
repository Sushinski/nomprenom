package com.nomprenom2.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.nomprenom2.R;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.presenter.AbsPresenter;
import com.nomprenom2.presenter.SearchResultPresenter;
import com.nomprenom2.utils.AppToast;
import com.nomprenom2.utils.ColorUtils;
import com.nomprenom2.utils.SearchedNamesAdapter;
import java.util.List;


public class SearchResultActivity extends AppCompatActivity {

    public static final int ADD_NAME_ID = 3;
    public static final int SEL_NAMES_ID = 4;
    private AbsPresenter presenter;
    public SearchedNamesAdapter arrayAdapter;
    public RecyclerView result_list_view;
    private String[] regions;
    private int sex;
    private String sex_str;
    private int zod;
    private String zod_str;
    private List<NameRecord> names; // [todo] move data to presenter
    private TextView search_result_descr_tw;
    private TextView empty_tw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setAllowEnterTransitionOverlap(true);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.main_icon_2);
            ColorUtils.initTeamColors(this);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            myToolbar.getChildAt(1).setTransitionName("trans_icon");
        }
        presenter = new SearchResultPresenter(this);
        result_list_view = (RecyclerView) findViewById(R.id.names_result_list_view);
        SearchView search_view_action = (SearchView) findViewById(R.id.search_view);
        SearchView.OnQueryTextListener text_change_listener =
                new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextChange(String newText)
            {
                names = presenter.getSuggestions(newText ,regions, sex, zod);
                arrayAdapter.name_list = names;
                arrayAdapter.notifyDataSetChanged();
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return true;
            }
        };
        search_view_action.setOnQueryTextListener(text_change_listener);
        search_view_action.setQueryHint(getResources().getString(R.string.search_hint));
        search_view_action.setIconifiedByDefault(false);
        ImageView searchIcon = (ImageView) search_view_action
                .findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchIcon.setImageResource(R.drawable.ic_find_in_page_black_24dp);

        search_result_descr_tw = (TextView) findViewById(R.id.search_result_descr);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        result_list_view.setLayoutManager(mLayoutManager);
        empty_tw = (TextView)findViewById(R.id.empty_list);
        init();
    }


    void init(){
        Intent data  = getIntent();
        String regions_str = getResources().getString(R.string.descr_regions);
        String sex_str = getResources().getString(R.string.descr_sex);
        String zod_str = getResources().getString(R.string.descr_zod);
        if(data.hasExtra(MainActivity.REGIONS)) {
            regions = data.getStringArrayExtra(MainActivity.REGIONS);
            regions_str += TextUtils.join(", ", regions );
        }else
            regions_str += getResources().getString(R.string.filter_all);
        if(data.hasExtra(MainActivity.SEX)) {
            sex = data.getIntExtra(MainActivity.SEX, -1);
            sex_str += sex != -1 ?
                    getResources().getStringArray(R.array.sex_sels)[sex] :
                    getResources().getString(R.string.filter_all);
        }else {
            sex = -1;
            sex_str += getResources().getString(R.string.filter_all);
        }
        if(data.hasExtra(MainActivity.ZODIAC)) {
            zod = data.getIntExtra(MainActivity.ZODIAC, -1);
            zod_str += zod > 0 ?
                    getResources().getStringArray(R.array.zod_sels)[zod-1] :
                    getResources().getString(R.string.filter_all);
        }else {
            zod = -1;
            zod_str += getResources().getString(R.string.filter_all);
        }
        String patronymic;
        if(data.hasExtra(MainActivity.PATRONYMIC)) {
            patronymic = data.getStringExtra(MainActivity.PATRONYMIC);
        }else
            patronymic = null;

        search_result_descr_tw.setText( regions_str + '\n'+
                                        sex_str + '\n' +
                                        zod_str +
                (patronymic != null ?
                '\n' + getResources().getString(R.string.descr_patr) + patronymic :
                "")
        );
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
                settingsAction();
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
        startActivityForResult(intent, SEL_NAMES_ID);
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
        switch(requestCode) {
            case(ADD_NAME_ID):
                if (resultCode == Activity.RESULT_OK) {
                    names = presenter.getNames(regions, sex, zod);
                    arrayAdapter.name_list = names;
                    arrayAdapter.notifyDataSetChanged();
                }
                break;
            case(SEL_NAMES_ID):
                if (resultCode == Activity.RESULT_OK) {
                    names = presenter.getNames(regions, sex, zod);
                    arrayAdapter.name_list = names;
                    arrayAdapter.notifyDataSetChanged();
                }
                break;
            default:break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        result_list_view.setVisibility(View.VISIBLE);
    }

    protected void settingsAction(){
        // settings is not implemented for now; show toast info
        AppToast toast = new AppToast(getApplicationContext());
        toast.showToast(getString(R.string.feature_not_implemented));
    }
}
