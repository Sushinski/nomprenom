package com.nomprenom2.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.nomprenom2.R;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.presenter.SelectedNamesPresenter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.nomprenom2.interfaces.IListItemDeleter;
import com.nomprenom2.utils.SelectedNameAdapter;

public class SelectedNamesActivity extends AppCompatActivity implements IListItemDeleter {
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
        String patr = getIntent().getStringExtra(MainActivity.PATRONYMIC);
        boolean isMale = true;
        arrayAdapter = new SelectedNameAdapter(this,
                R.layout.name_list_item,
                names, patr, isMale);
        //result_list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        result_list_view.setAdapter(arrayAdapter);

        checked_set = new HashSet<>();
        result_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
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


    public void onDeleteListItem(Object obj){
        String name = (String)obj;
        names.remove(name);
        checked_set.add(name);
        // arrayAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        if( checked_set.size() != 0) {
            String[] dels = new String[checked_set.size()];
            checked_set.toArray(dels);
            presenter.deselectNames(dels);
            checked_set.clear();
        }
    }

}
