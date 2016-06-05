package com.nomprenom2.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import com.nomprenom2.R;
import com.nomprenom2.model.GroupRecord;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.model.ZodiacRecord;
import com.nomprenom2.presenter.AbsPresenter;
import com.nomprenom2.presenter.SearchResultPresenter;
import com.nomprenom2.utils.SearchedNamesAdapter;
import com.nomprenom2.utils.SelectedNameAdapter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchResultActivity extends AppCompatActivity {
    private AbsPresenter presenter;
    private ArrayAdapter<String> arrayAdapter;
    private ListView result_list_view;
    private String[] regions;
    private String sex;
    private String zod;
    private String patronymic;
    private List<String> names;
    public Set<Integer> checked_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // // TODO: 06.04.16  inject candidate
        presenter = new SearchResultPresenter(this);
        checked_set = new HashSet<>();
        result_list_view = (ListView) findViewById(R.id.names_result_list_view);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent data  = getIntent();
        if(data.hasExtra(MainActivity.REGIONS))
            regions = data.getStringArrayExtra(MainActivity.REGIONS);
        if(data.hasExtra(MainActivity.SEX))
            sex = data.getStringExtra(MainActivity.SEX);
        if(data.hasExtra(MainActivity.ZODIAC))
            zod = data.getStringExtra(MainActivity.ZODIAC);
        if(data.hasExtra(MainActivity.PATRONYMIC))
            patronymic = data.getStringExtra(MainActivity.PATRONYMIC);
        if(regions != null ||
                sex != null ||
                zod != null ||
                patronymic != null)
            fab.setVisibility(View.GONE);

        names =  presenter.getNames(regions, sex, zod);
        arrayAdapter = new SearchedNamesAdapter(this,
                R.layout.name_list_item_checked,
                names, patronymic, sex, zod);
        result_list_view.setAdapter(arrayAdapter);
        result_list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        result_list_view.setFastScrollEnabled(true);
        result_list_view.setFastScrollAlwaysVisible(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.title_screen3: {
                Intent intent = new Intent(this, SelectedNamesActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.search_names: {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
