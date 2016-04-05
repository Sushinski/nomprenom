package com.nomprenom2.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.nomprenom2.R;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.presenter.AbsPresenter;
import com.nomprenom2.presenter.SearchResultPresenter;

import java.util.List;

public class SearchResultActivity extends AppCompatActivity {
    private AbsPresenter presenter;
    private ArrayAdapter<String> arrayAdapter;
    private ListView result_list_view;
    private String[] regions;
    private String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // // TODO: 06.04.16  inject candidate
        presenter = new SearchResultPresenter(this);
        result_list_view = (ListView) findViewById(R.id.names_result_list_view);
        Intent data  = getIntent();
        if(data.hasExtra("regions"))
            regions = data.getStringArrayExtra("regions");
        if(data.hasExtra("sex"))
            sex = data.getStringExtra("sex");

        List<String> lst =  presenter.getNames(regions, sex);
        arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice,
                lst);
        result_list_view.setAdapter(arrayAdapter);
        result_list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    public void saveNames(){
        // // TODO: 06.04.16 get checked name & update 'selected' field 
        //presenter.onOk();
    }

}
