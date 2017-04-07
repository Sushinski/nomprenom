/*
 * created by Pavel Golubev golubev.pavel.spb@gmail.com
 * no license applied
 * You may use this file without any restrictions
 */

package com.nomprenom2.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import com.crashlytics.android.Crashlytics;
import com.nomprenom2.R;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.presenter.SelectedNamesPresenter;
import java.util.List;
import com.nomprenom2.interfaces.IListItemDeleter;
import com.nomprenom2.utils.ColorUtils;
import com.nomprenom2.utils.SelectedNameAdapter;
import io.fabric.sdk.android.Fabric;

/**
 * Shows bookmarked names and allows to share it
 */
public class SelectedNamesActivity extends AppCompatActivity implements IListItemDeleter {
    private List<NameRecord> names;
    private String patr;
    private int sex;
    private int zod;
    FloatingActionButton fab;

    /**
     * Initializes activity with data
     * Assigns share button click listener to share function via intent
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_selected_names);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ColorUtils.initTeamColors(this);
        }

        RecyclerView result_list_view = (RecyclerView) findViewById(R.id.selected_names_list_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        result_list_view.setLayoutManager(mLayoutManager);
        // todo inject candidate
        SelectedNamesPresenter presenter = new SelectedNamesPresenter(this);
        names = NameRecord.getSelected(NameRecord.Check.Checked.getId());
        Intent intent = getIntent();
        if(intent.hasExtra(MainActivity.PATRONYMIC))
            patr = intent.getStringExtra(MainActivity.PATRONYMIC);
        if(intent.hasExtra(MainActivity.SEX))
            sex = intent.getIntExtra(MainActivity.SEX, -1);
        if(intent.hasExtra(MainActivity.ZODIAC))
            zod = intent.getIntExtra(MainActivity.ZODIAC, -1);
        SelectedNameAdapter arrayAdapter = new SelectedNameAdapter(this,
                R.layout.name_list_item,
                names, patr, sex, zod);
        result_list_view.setAdapter(arrayAdapter);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String fmt = getResources().getString(R.string.send_names_title);
                String txt = String.format(fmt, getResources().getString(R.string.app_name));
                intent.putExtra(Intent.EXTRA_SUBJECT,
                        txt);
                intent.putExtra(Intent.EXTRA_TEXT, getNamesString());
                String title = getResources().getString(R.string.chooser_title);
                Intent chooser = Intent.createChooser(intent, title);
                if(intent.resolveActivity(getPackageManager()) != null)
                    startActivity(chooser);
            }
        });

    }

    /**
     * Prepares share string
     * @return
     */
    private String getNamesString(){
        String res = String.format(getResources().getString(R.string.names_string_prefix),
                getResources().getString(R.string.app_name)) + "\n";
        res += TextUtils.join(", ", names) + ". ";
        res +=  getResources().getString(R.string.names_string_postfix);
        return res;
    }

    /**
     * Remove item from selected names list
     * @param obj
     */
    public void onDeleteListItem(Object obj){
        NameRecord nr = (NameRecord)obj;
        names.remove(nr);
        NameRecord.setSelection(nr.name, 0);
    }

    /**
     * Processes menu item selection
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent d = new Intent();
                setResult(RESULT_OK, d);
                finish();
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Saves from data
     * @param bundle
     */
    @Override
    protected void onSaveInstanceState(Bundle bundle){
        bundle.putString(MainActivity.PATRONYMIC, patr);
        bundle.putInt(MainActivity.SEX, sex);
        bundle.putInt(MainActivity.ZODIAC, zod);
        super.onSaveInstanceState(bundle);
    }

    /**
     * Restores form data
     * @param savedInstance
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstance){
        super.onRestoreInstanceState(savedInstance);
        patr = savedInstance.getString(MainActivity.PATRONYMIC);
        sex = savedInstance.getInt(MainActivity.SEX);
        zod = savedInstance.getInt(MainActivity.ZODIAC);
    }
}
