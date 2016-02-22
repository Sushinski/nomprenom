package com.nomprenom2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nomprenom2.NamesBaseContract.GroupRecord;
import com.nomprenom2.NamesBaseContract.NameRecord;

public class SelectRegionActivity extends AppCompatActivity {

    private DbHelper mDbHelper;
    public SelectRegionActivity() {
        super();
        mDbHelper = new DbHelper(this);
        // pre populate tables
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GroupRecord.COLUMN_GROUP_REGION, "Europe");
        long newRowId= db.insert(
                GroupRecord.TABLE_NAME,
                null,
                values);
        values.clear();
        values.put(NameRecord.COLUMN_NAMES_GROUP, newRowId);
        values.put(NameRecord.COLUMN_NAMES_NAME, "John");
        db.insert(
                NameRecord.TABLE_NAME,
                null,
                values);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_region_actvity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
