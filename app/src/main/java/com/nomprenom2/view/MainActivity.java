package com.nomprenom2.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.nomprenom2.R;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.model.ZodiacRecord;
import com.nomprenom2.presenter.AbsPresenter;
import com.nomprenom2.presenter.MainPresenter;


public class MainActivity extends AppCompatActivity {
    public static final int GROUP_REQUEST = 1;
    public static final int SEARCH_RESULT = 2;
    public static final String PATRONYMIC = "com.nomprenom2.view.patronymic";
    public static final String SEX = "com.nomprenom2.view.sex";
    public static final String ZODIAC = "com.nomprenom2.view.zodiac";
    public static final String REGIONS = "com.nomprenom2.view.regions";
    public static final String SINGLE_REGION = "com.nomprenom2.view.single_region";

    protected EditText patr_tw;
    protected TextView title_txt;
    protected NameParamsFragment param_frag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.transform_icon);
        }

        title_txt = (TextView) findViewById(R.id.title_text);
        patr_tw = (EditText) findViewById(R.id.input_first_name);
        param_frag =
                (NameParamsFragment) getSupportFragmentManager().
                        findFragmentById(R.id.name_params_fragment);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    @Override
    public void onNewIntent(Intent intent){
        setIntent(intent);
        super.onNewIntent(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_search:
                searchNames();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void searchNames(){
        Intent intent = new Intent(this, SearchResultActivity.class);
        if( !param_frag.regions.isEmpty() ){
            String[] sa = new String[param_frag.regions.size()];
            param_frag.regions.toArray(sa);
            intent.putExtra(REGIONS, sa);
        }
        String sex = NameRecord.Sex.fromInt(param_frag.getSelectedSex());
        if(sex != null) {
            intent.putExtra(SEX, sex);
        }
        String zod = ZodiacRecord.ZodMonth.fromInt(param_frag.getSelectedZod());
        if(zod != null)
            intent.putExtra(ZODIAC, zod);
        String patr = patr_tw.getText().toString();
        if(!patr.equals("")){
            intent.putExtra(PATRONYMIC, patr);
        }
        if(intent.getExtras() == null){
            showToast(getString(R.string.sel_alert));
            return;
        }
        startActivity(intent);
    }



    private void showToast(String str){
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        toast.show();
    }

}
