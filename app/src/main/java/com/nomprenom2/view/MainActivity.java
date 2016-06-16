package com.nomprenom2.view;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nomprenom2.R;
import com.nomprenom2.presenter.AbsPresenter;
import com.nomprenom2.presenter.MainPresenter;
import com.nomprenom2.utils.NothingSelectedSpinnerAdapter;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


public class MainActivity extends AppCompatActivity{
    public static final int GROUP_REQUEST = 1;
    public static final int SEARCH_RESULT = 2;
    public static final String PATRONYMIC = "com.nomprenom2.view.patronymic";
    public static final String SEX = "com.nomprenom2.view.sex";
    public static final String ZODIAC = "com.nomprenom2.view.zodiac";
    public static final String REGIONS = "com.nomprenom2.view.regions";

    private AbsPresenter presenter;
    protected EditText patr_tw;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        patr_tw = (EditText) findViewById(R.id.input_first_name);
        setSupportActionBar(toolbar);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
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
       /* if( !regions.isEmpty() ){
            String[] sa = new String[regions.size()];
            regions.toArray(sa);
            intent.putExtra(REGIONS, sa);
        }
        String sex = (String)sex_spinner.getSelectedItem();
        if(sex != null) {
            intent.putExtra(SEX, sex);
        }else{
            showToast(getResources().getString(R.string.to_fill_sex));
            return;
        }
        String zod = (String)zod_spinner.getSelectedItem();
        if(!zod.equals(""))
            intent.putExtra(ZODIAC, zod);*/
        String patr = patr_tw.getText().toString();
        if(patr.equals("")){
            showToast(getResources().getString(R.string.to_fill_patr));
            return;
        }
        else
            intent.putExtra(PATRONYMIC, patr);
        // todo save search param-s to base
        startActivity(intent);
    }


    private void showNameListScreen(){
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void selectScreen3(){
        Intent intent = new Intent(this, SelectedNamesActivity.class);
        //intent.putExtra(SEX, (String)sex_spinner.getSelectedItem());
        intent.putExtra(PATRONYMIC, patr_tw.getText().toString());
        startActivity(intent);
    }

    private void showToast(String str){
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        toast.show();
    }

}
