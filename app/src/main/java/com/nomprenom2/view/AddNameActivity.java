package com.nomprenom2.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.nomprenom2.R;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.model.ZodiacRecord;

import java.util.ArrayList;

public class AddNameActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.patr_tw.setHint(getResources().getString(R.string.add_name));
        search_btn.setText(getResources().getString(R.string.add_name));
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNameActivity.this.addName(v);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_name, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_name) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addName(View v){
        // todo get zodiac list
        ArrayList<ZodiacRecord>
        NameRecord.saveName(patr_tw.getText().toString(),
                NameRecord.Sex.valueOf(param_frag.sex_spinner.getSelectedItem().toString()),
                param_frag.zod_spinner.getSelectedItem().toString());
    }

}
