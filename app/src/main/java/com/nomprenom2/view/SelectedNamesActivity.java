package com.nomprenom2.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nomprenom2.R;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.presenter.SelectedNamesPresenter;

import java.util.List;

public class SelectedNamesActivity extends AppCompatActivity {
    private List<String> names;
    private ArrayAdapter<String> arrayAdapter;
    private ListView result_list_view;
    private SelectedNamesPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_names);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        result_list_view = (ListView)findViewById(R.id.selected_names_list_view);

        // todo inject candidate
        presenter = new SelectedNamesPresenter(this);
        names = presenter.getNames(NameRecord.Check.Checked.getId());
        // todo add custom adapter
        arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice,
                names);
        result_list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        result_list_view.setAdapter(arrayAdapter);
    }

}
