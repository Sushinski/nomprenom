package com.nomprenom2.view;

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
import com.nomprenom2.presenter.SelectedNamesPresenter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SelectedNamesActivity extends AppCompatActivity {
    private List<String> names;
    private ArrayAdapter<String> arrayAdapter;
    private ListView result_list_view;
    private SelectedNamesPresenter presenter;
    public Set<Integer> checked_set;

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

        checked_set = new HashSet<>();
        result_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView checkedTextView = ((CheckedTextView) view);
                if (checkedTextView.isChecked()) {
                    checked_set.add(position);
                } else {
                    checked_set.remove(position);
                }
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
                for (Integer i : checked_set) {
                    names.remove(i.intValue());
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
            for (Integer i : checked_set) {
                checked[j++] = arrayAdapter.getItem(i);
            }
            return checked;
        }
        return null;
    }
}
