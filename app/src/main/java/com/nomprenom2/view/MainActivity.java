package com.nomprenom2.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import com.nomprenom2.R;
import com.nomprenom2.utils.AppToast;
import com.nomprenom2.utils.ColorUtils;


public class MainActivity extends AppCompatActivity {
    public static final int GROUP_REQUEST = 1;
    public static final String PATRONYMIC = "com.nomprenom2.view.patronymic";
    public static final String SEX = "com.nomprenom2.view.sex";
    public static final String ZODIAC = "com.nomprenom2.view.zodiac";
    public static final String REGIONS = "com.nomprenom2.view.regions";
    public static final String SINGLE_REGION = "com.nomprenom2.view.single_region";

    private EditText patr_tw;
    private TextView title_txt;
    private NameParamsFragment param_frag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ColorUtils.initTeamColors(this);
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
        intent.putExtra(SEX, param_frag.getSelectedSex());
        intent.putExtra(ZODIAC, param_frag.getSelectedZod());
        String patr = patr_tw.getText().toString();
        if(!patr.equals("")){
            intent.putExtra(PATRONYMIC, patr);
        }
        if(intent.getExtras() == null){
            AppToast toast = new AppToast(getApplicationContext());
            toast.showToast(getString(R.string.sel_alert));
            return;
        }
        startActivity(intent);
    }
}
