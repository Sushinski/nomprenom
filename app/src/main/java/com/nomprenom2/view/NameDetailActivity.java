package com.nomprenom2.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nomprenom2.R;
import com.nomprenom2.utils.SelectedNameAdapter;

public class NameDetailActivity extends AppCompatActivity {
    private String name;
    private String name_descr;
    private TextView name_tw;
    private TextView name_descr_tw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_detail);
        name_tw = (TextView)findViewById(R.id.text_fullname);
        name_descr_tw = (TextView)findViewById(R.id.text_name_descr);
        Intent intent = getIntent();
        name = intent.getStringExtra(SelectedNameAdapter.NAME);
        if( name.equals("") ) {
            name = getResources().getString(R.string.empty_name);
        }
        if( intent.hasExtra(SelectedNameAdapter.NAME_DESCR)){
            name_descr = intent.getStringExtra(SelectedNameAdapter.NAME_DESCR);
        }
        name_tw.setText(name);
        name_descr_tw.setText(name_descr);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
