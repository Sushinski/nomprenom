package com.nomprenom2.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.nomprenom2.presenter.AbsPresenter;
import com.nomprenom2.presenter.MainPresenter;


public class MainActivity extends AppCompatActivity{
    public static final int GROUP_REQUEST = 1;
    public static final int SEARCH_RESULT = 2;
    public static final String PATRONYMIC = "com.nomprenom2.view.patronymic";
    public static final String SEX = "com.nomprenom2.view.sex";
    public static final String ZODIAC = "com.nomprenom2.view.zodiac";
    public static final String REGIONS = "com.nomprenom2.view.regions";

    private AbsPresenter presenter;
    protected EditText patr_tw;
    protected TextView title_txt;
    protected Button search_btn;
    protected NameParamsFragment param_frag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title_txt = (TextView) findViewById(R.id.title_text);
        patr_tw = (EditText) findViewById(R.id.input_first_name);
        search_btn = (Button) findViewById(R.id.search_name_button);
        setSupportActionBar(toolbar);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.searchNames(v);
            }
        });
        param_frag =
                (NameParamsFragment) getSupportFragmentManager().
                        findFragmentById(R.id.name_params_fragment);
        // inject candidate
        presenter = new MainPresenter(this);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onNewIntent(Intent intent){
        setIntent(intent);
        super.onNewIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.names_list) {
            showNameListScreen();
            return true;
        }
        else if (id == R.id.title_screen3) {
            selectScreen3();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void searchNames(View view){
        Intent intent = new Intent(this, SearchResultActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if( !param_frag.regions.isEmpty() ){
            String[] sa = new String[param_frag.regions.size()];
            param_frag.regions.toArray(sa);
            intent.putExtra(REGIONS, sa);
        }
        String sex = (String)param_frag.sex_spinner.getSelectedItem();
        if(sex != null) {
            intent.putExtra(SEX, sex);
        }else{
            showToast(getResources().getString(R.string.to_fill_sex));
            return;
        }
        String zod = (String)param_frag.zod_spinner.getSelectedItem();
        if(zod != null)
            intent.putExtra(ZODIAC, zod);
        String patr = patr_tw.getText().toString();
        if(patr.equals("")){
            showToast(getResources().getString(R.string.to_fill_patr));
            return;
        }
        else
            intent.putExtra(PATRONYMIC, patr);
        startActivity(intent);
    }


    private void showNameListScreen(){
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void selectScreen3(){
        Intent intent = new Intent(this, SelectedNamesActivity.class);
        intent.putExtra(PATRONYMIC, patr_tw.getText().toString());
        startActivity(intent);
    }

    private void showToast(String str){
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        toast.show();
    }

}
