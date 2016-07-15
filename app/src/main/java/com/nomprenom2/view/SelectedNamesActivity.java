package com.nomprenom2.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
    private RecyclerView result_list_view;
    private RecyclerView.LayoutManager mLayoutManager;
    private SelectedNamesPresenter presenter;
    private String patr;
    private String sex;
    private String zod;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_names);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        result_list_view = (RecyclerView)findViewById(R.id.selected_names_list_view);
        mLayoutManager = new LinearLayoutManager(this);
        result_list_view.setLayoutManager(mLayoutManager);
        // todo inject candidate
        presenter = new SelectedNamesPresenter(this);
        names = presenter.getNames(NameRecord.Check.Checked.getId());
        Intent intent = getIntent();
        if(intent.hasExtra(MainActivity.PATRONYMIC))
            patr = intent.getStringExtra(MainActivity.PATRONYMIC);
        if(intent.hasExtra(MainActivity.SEX))
            sex = intent.getStringExtra(MainActivity.SEX);
        if(intent.hasExtra(MainActivity.ZODIAC))
            zod = intent.getStringExtra(MainActivity.ZODIAC);
        arrayAdapter = new SelectedNameAdapter(this,
                R.layout.name_list_item,
                names, patr, sex, zod);
        result_list_view.setAdapter(arrayAdapter);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String txt = String.format(getResources().getString(R.string.send_names_title),
                        getResources().getString(R.string.app_name));
                intent.putExtra(Intent.EXTRA_SUBJECT,
                        txt);
                intent.putExtra(Intent.EXTRA_TEXT, getNamesString());
                String title = getResources().getString(R.string.chooser_title);
                Intent chooser = Intent.createChooser(intent, title);
                if(intent.resolveActivity(getPackageManager()) != null)
                    startActivity(chooser);
            }
        });
    }


    private String getNamesString(){
        String res = String.format(getResources().getString(R.string.names_string_prefix),
                getResources().getString(R.string.app_name));
        res += TextUtils.join(",", names) + ".";
        res +=  getResources().getString(R.string.names_string_postfix);
        return res;
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
            case R.id.search_names: {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.names_list: {
                Intent intent = new Intent(this, SearchResultActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle){
        bundle.putString(MainActivity.PATRONYMIC, patr);
        bundle.putString(MainActivity.SEX, sex);
        bundle.putString(MainActivity.ZODIAC, zod);
        super.onSaveInstanceState(bundle);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstance){
        super.onRestoreInstanceState(savedInstance);
        patr = savedInstance.getString(MainActivity.PATRONYMIC);
        sex = savedInstance.getString(MainActivity.SEX);
        zod = savedInstance.getString(MainActivity.ZODIAC);
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

}
