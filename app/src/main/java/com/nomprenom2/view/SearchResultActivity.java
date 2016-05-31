package com.nomprenom2.view;

import android.content.Intent;
import android.os.Bundle;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // // TODO: 06.04.16  inject candidate
        presenter = new SearchResultPresenter(this);
        checked_set = new HashSet<>();
        result_list_view = (ListView) findViewById(R.id.names_result_list_view);
        result_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView checkedTextView = ((CheckedTextView) view);
                NameRecord.setSelection(arrayAdapter.getItem(position),
                            checkedTextView.isChecked() ? 1:0);
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

        names =  presenter.getNames(regions, sex, zod);
        arrayAdapter = new SearchedNamesAdapter(this,
                R.layout.name_list_item_checked,
                names, patronymic, NameRecord.Sex.valueOf(sex).getId() == 1,
                ZodiacRecord.ZodMonth.valueOf(zod).getId());
        result_list_view.setAdapter(arrayAdapter);
        result_list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
