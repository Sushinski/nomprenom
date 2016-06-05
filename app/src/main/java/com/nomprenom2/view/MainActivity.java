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


public class MainActivity extends AppCompatActivity implements
        FragmentItem.OnFragmentInteractionListener{
    public static final int GROUP_REQUEST = 1;
    public static final int SEARCH_RESULT = 2;
    public static final String PATRONYMIC = "com.nomprenom2.view.patronymic";
    public static final String SEX = "com.nomprenom2.view.sex";
    public static final String ZODIAC = "com.nomprenom2.view.zodiac";
    public static final String REGIONS = "com.nomprenom2.view.regions";
    private static String[] empty_arr_item;
    public ArrayAdapter<String> groups_adapter;
    public HashSet<String> regions;
    public String[] sex_sel;
    public String[] zod_sel;
    private Spinner sex_spinner;
    private Spinner zod_spinner;
    private AbsPresenter presenter;
    private EditText patr_tw;
    private List<WeakReference<Fragment>> frag_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        empty_arr_item =
                new String[]{getResources().getString(R.string.not_selected_region)};
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        patr_tw = (EditText) findViewById(R.id.input_first_name);
        setSupportActionBar(toolbar);
        sex_sel = getResources().getStringArray(R.array.sex_sels);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, sex_sel);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex_spinner = (Spinner) findViewById(R.id.sex_spinner);
        sex_spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        // zodiac spinner
        zod_sel = getResources().getStringArray(R.array.zod_months);
        ArrayAdapter<String> zod_adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, zod_sel);
        zod_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zod_spinner = (Spinner) findViewById(R.id.zodiac_spinner);
        zod_spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        zod_adapter,
                        R.layout.zodiac_spinner_row_nothing_selected,
                        this));
        // inject candidate
        presenter = new MainPresenter(this);
        regions=  new HashSet<String>();
        frag_list = new ArrayList<WeakReference<Fragment>>();
        if(savedInstanceState!=null)
            setGroupList();
    }


    @Override
    public void onAttachFragment(Fragment fragment) {
        frag_list.add(new WeakReference<>(fragment));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.title_screen3) {
            selectScreen3();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectRegion(View view) {
        Intent intent = new Intent(this, SelectedRegionActivity.class);
        if(!regions.isEmpty()) {
            String[] sa = new String[regions.size()];
            regions.toArray(sa);
            intent.putExtra(REGIONS, sa);
        }
        startActivityForResult(intent, GROUP_REQUEST);
    }

    public void searchNames(View view){
        Intent intent = new Intent(this, SearchResultActivity.class);
        if( !regions.isEmpty() ){
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
            intent.putExtra(ZODIAC, zod);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == GROUP_REQUEST) {
            regions.clear();
            if(data.hasExtra(REGIONS)) {
                regions.addAll(Arrays.asList(data.getStringArrayExtra(REGIONS)));
            }
            setGroupList();
        }
    }

    private void setGroupList(){
        if (findViewById(R.id.fragment_container) != null) {
            FragmentManager mngr = getFragmentManager();
            FragmentTransaction tr = mngr.beginTransaction();
            Fragment fr;
            for (String s: regions){
                fr = mngr.findFragmentByTag(s);
                if(fr == null)
                {
                    fr = FragmentItem.newInstance(s);
                    tr.add(R.id.fragment_container, fr, s);
                }
            }
            for(WeakReference<Fragment> f: frag_list){
                fr = f.get();
                if( fr != null ){
                    String tg = fr.getTag();
                    if(!regions.contains(tg))
                        tr.remove(fr);
                }else{
                    frag_list.remove(f);
                }
            }
             tr.commit();
        }
    }

    private void selectScreen3()
    {
        Intent intent = new Intent(this, SelectedNamesActivity.class);
        intent.putExtra(SEX, (String)sex_spinner.getSelectedItem());
        intent.putExtra(PATRONYMIC, patr_tw.getText().toString());
        startActivity(intent);
    }

    private void showToast(String str){
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void onFragmentInteraction(String frag_name){
        FragmentManager mngr = getFragmentManager();
        FragmentTransaction tr = mngr.beginTransaction();
        Fragment fr = mngr.findFragmentByTag(frag_name);
        if( fr != null ) {
            tr.remove(fr);
            regions.remove(frag_name);
        }
        tr.commit();
    }
}
