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
    private String patr;
    private boolean isMale;
    private int zod;


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
        Intent intent = getIntent();
        if(intent.hasExtra(MainActivity.PATRONYMIC))
            patr = intent.getStringExtra(MainActivity.PATRONYMIC);
        if(intent.hasExtra(MainActivity.SEX))
            isMale = intent.getBooleanExtra(MainActivity.SEX, true);
        if(intent.hasExtra(MainActivity.ZODIAC))
            zod = intent.getIntExtra(MainActivity.ZODIAC, 0);
        arrayAdapter = new SelectedNameAdapter(this,
                R.layout.name_list_item,
                names, patr, isMale, zod);
        result_list_view.setAdapter(arrayAdapter);

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
        NameRecord.setSelection(name, 0);
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
    protected void onSaveInstanceState(Bundle bundle){
        bundle.putString(MainActivity.PATRONYMIC, patr);
        bundle.putBoolean(MainActivity.SEX, isMale);
        bundle.putInt(MainActivity.ZODIAC, zod);
        super.onSaveInstanceState(bundle);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstance){
        super.onRestoreInstanceState(savedInstance);
        patr = savedInstance.getString(MainActivity.PATRONYMIC);
        isMale = savedInstance.getBoolean(MainActivity.SEX);
        zod = savedInstance.getInt(MainActivity.ZODIAC);
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

}
