package com.nomprenom2.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import com.nomprenom2.R;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.utils.AppToast;
import com.nomprenom2.utils.ColorUtils;
import java.util.ArrayList;
import java.util.List;

public class AddNameActivity extends AppCompatActivity {
    private NameParamsFragment param_frag;
    private EditText name_et;
    private EditText descr_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setAllowEnterTransitionOverlap(true);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_name);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ColorUtils.initTeamColors(this);
        }
        name_et = (EditText)findViewById(R.id.name_tw);
        descr_et = (EditText)findViewById(R.id.name_descr);
        param_frag = (NameParamsFragment) getSupportFragmentManager().
                        findFragmentById(R.id.name_params_fragment);
        param_frag.setSingleSel(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_name, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch(item.getItemId()) {
           case (R.id.add_name):
               if (!addName())
                   return false;
               Intent data = new Intent();
               setResult(RESULT_OK, data);
               finish();
               return true;
           case android.R.id.home:
               onBackPressed();
               return true;
       }
        return super.onOptionsItemSelected(item);
    }


    private boolean addName(){
        // todo get zodiac list
        AppToast toast = new AppToast(getApplicationContext());
        List<Integer> zod_list = new ArrayList<>();
        Integer z = param_frag.getSelectedZod();
        if( z > 0)
            zod_list.add(z);
        Integer sx = param_frag.getSelectedSex();
        List<String> gr_list = new ArrayList<>();
        for (String s : param_frag.regions ) {
            gr_list.add(s);
        }
        if( z == 0 ||
                sx == 0 ||
                gr_list.isEmpty() ||
                name_et.getText().length() == 0 ||
                descr_et.getText().length() == 0){
            toast.showToast(getString(R.string.must_set_all_param));
            return false;
        }

        if(NameRecord.saveName(name_et.getText().toString(), sx-1,
                zod_list, gr_list, descr_et.getText().toString())  < 0){
            toast.showToast(getString(R.string.err_duplicate_keys));
            return false;
        }
        toast.showToast(getString(R.string.save_ok));
        return true;
    }
}
