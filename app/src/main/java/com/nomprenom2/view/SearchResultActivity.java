package com.nomprenom2.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import com.nomprenom2.R;
import com.nomprenom2.model.GroupRecord;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.presenter.AbsPresenter;
import com.nomprenom2.presenter.SearchResultPresenter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchResultActivity extends AppCompatActivity {
    private AbsPresenter presenter;
    private ArrayAdapter<String> arrayAdapter;
    private ListView result_list_view;
    private String[] regions;
    private String sex;
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
                if(checkedTextView.isChecked()){
                    checked_set.add(position);
                }else{
                    checked_set.remove(position);
                }
            }
        });

        Intent data  = getIntent();
        if(data.hasExtra("regions"))
            regions = data.getStringArrayExtra("regions");
        if(data.hasExtra("sex"))
            sex = data.getStringExtra("sex");

        names =  presenter.getNames(regions, sex);
        arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice,
                names);
        result_list_view.setAdapter(arrayAdapter);
        result_list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    public void saveNames(View view){
        // // TODO: 06.04.16 get checked name & update 'selected' field
        //presenter.onOk();
        int j = 0;
        String[] checked = new String[checked_set.size()];
        for (Integer i : checked_set) {
            checked[j++] = arrayAdapter.getItem(i);
        }
        NameRecord.setSelection(checked, NameRecord.Check.Checked.getId());
    }

}
