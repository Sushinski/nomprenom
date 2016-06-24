package com.nomprenom2.view;

import android.content.Intent;
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
import com.nomprenom2.model.GroupRecord;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.model.ZodiacRecord;

import java.util.ArrayList;
import java.util.List;

public class AddNameActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.title_txt.setText(getResources().getString(R.string.add_name));
        this.patr_tw.setHint(getResources().getString(R.string.add_name));
        search_btn.setVisibility(View.GONE);
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
            addName();
            Intent data = new Intent();
            setResult(RESULT_OK, data);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addName(){
        // todo get zodiac list
        List<String> zod_list = new ArrayList<>();
        String z = (String)param_frag.zod_spinner.getSelectedItem();
        zod_list.add(z);
        List<String> gr_list = new ArrayList<>();
        for (String s : param_frag.regions ) {
            gr_list.add(s);
        }
        NameRecord.saveName(patr_tw.getText().toString(),
                param_frag.sex_spinner.getSelectedItem().toString(),
                zod_list, gr_list);
    }

}
