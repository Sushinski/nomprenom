package com.nomprenom2.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import com.nomprenom2.R;
import com.nomprenom2.utils.ColorUtils;
import com.nomprenom2.utils.SelectedNameAdapter;

public class NameDetailActivity extends AppCompatActivity {
    private String name_descr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_detail);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ColorUtils.initTeamColors(this);
        }

        TextView name_tw = (TextView) findViewById(R.id.text_fullname);
        TextView name_descr_tw = (TextView) findViewById(R.id.text_name_descr);
        Intent intent = getIntent();
        String name = intent.getStringExtra(SelectedNameAdapter.NAME);
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
