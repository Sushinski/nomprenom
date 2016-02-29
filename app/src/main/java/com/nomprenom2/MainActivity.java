package com.nomprenom2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.nomprenom2.model.DbHelper;

public class MainActivity extends AppCompatActivity {
    //private DbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        DbHelper dbHelper = new DbHelper(this);
        dbHelper.setNames();

        /*
        mDbHelper = new DbHelper(this);
        // pre populate tables
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NamesBaseContract.GroupRecord.COLUMN_GROUP_NAME, "Europe");
        long newRowId= db.insert(
                NamesBaseContract.GroupRecord.TABLE_NAME,
                null,
                values);
        values.clear();
        values.put(NamesBaseContract.NameRecord.COLUMN_NAMES_GROUP, newRowId);
        values.put(NamesBaseContract.NameRecord.COLUMN_NAMES_NAME, "John");
        db.insert(
                NamesBaseContract.NameRecord.TABLE_NAME,
                null,
                values);
        */
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

        return super.onOptionsItemSelected(item);
    }

    public void selectRegion(View view) {
        Intent intent = new Intent(this, SelectedRegionActivity.class);
        startActivity(intent);
    }
}
