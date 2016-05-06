package com.nomprenom2.view;

import android.content.Intent;
import android.os.Bundle;
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
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.model.SelectedName;
import com.nomprenom2.presenter.SelectedNamesPresenter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.nomprenom2.utils.SelectedNameAdapter;

public class SelectedNamesActivity extends AppCompatActivity {
    private ArrayList<String> names;
    private SelectedNameAdapter arrayAdapter;
    private ListView result_list_view;
    private SelectedNamesPresenter presenter;
    public Set<String> checked_set;

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
        arrayAdapter = new SelectedNameAdapter(this,
                R.layout.name_list_item,
                names);
        result_list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        result_list_view.setAdapter(arrayAdapter);

        checked_set = new HashSet<>();
        result_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Intent in = new Intent(view.getContext(), NameDetailActivity.class);
                startActivity(in);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_selected_names, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_delete:
                presenter.deselectNames(getSelectedNames());
                for (String s : checked_set) {
                    names.remove(s);
                }
                checked_set.clear();
                result_list_view.clearChoices();
                arrayAdapter.notifyDataSetChanged();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public String[] getSelectedNames(){
        if(checked_set.size() != 0) {
            String[] checked = new String[checked_set.size()];
            int j = 0;
            for (String s : checked_set) {
                checked[j++] = s;
            }
            return checked;
        }
        return null;
    }
}
