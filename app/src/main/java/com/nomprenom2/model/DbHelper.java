package com.nomprenom2.model;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.nomprenom2.model.NamesBaseContract.GroupRecord;

import java.util.ArrayList;



public class DbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Nomprenom.db";
    private static final String TEXT_TYPE = " TEXT ";
    private static final String INT_TYPE = " INTEGER ";
    private static final String FOREIGN_KEY_TYPE = " FOREIGN KEY REFERENCES ";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_GROUPS_TBL =
            "CREATE TABLE " +
                    GroupRecord.TABLE_NAME + " (" +
                    GroupRecord._ID + " INTEGER PRIMARY KEY," +
                    GroupRecord.COLUMN_GROUP_NAME + TEXT_TYPE + " );";
    private static final String SQL_CREATE_NAMES_TBL =
                    "CREATE TABLE " +
                    NamesBaseContract.NameRecord.TABLE_NAME + " (" +
                    NamesBaseContract.NameRecord._ID + " INTEGER PRIMARY KEY," +
                    NamesBaseContract.NameRecord.COLUMN_NAMES_NAME + TEXT_TYPE + COMMA_SEP +
                    NamesBaseContract.NameRecord.COLUMN_NAMES_GROUP + INT_TYPE + COMMA_SEP +
                    "FOREIGN KEY(" + NamesBaseContract.NameRecord.COLUMN_NAMES_GROUP + ") " +
                    "REFERENCES " + GroupRecord.TABLE_NAME + "(" +
                            NamesBaseContract.NameRecord._ID + "));";

    private static final String SQL_DELETE_TABLES =
            "DROP TABLE IF EXISTS " + GroupRecord.TABLE_NAME + "; " +
            "DROP TABLE IF EXISTS " + NamesBaseContract.NameRecord.TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        // due to foreign keys relationships
        // create tables in strict order
        db.execSQL(SQL_CREATE_GROUPS_TBL); // name groups table(regions)
        db.execSQL(SQL_CREATE_NAMES_TBL); // name table
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // [ToDo] define upgrade policy if needed
        db.execSQL(SQL_DELETE_TABLES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // [ToDo] define downgrade policy if needed
        onUpgrade(db, oldVersion, newVersion);
    }

    public ArrayList<NameRecord> getAllNames(ArrayList<Integer> group_ids)
    {
        ArrayList<NameRecord> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from NameRecord";
        if( group_ids.size() != 0 )
            query += " where group_id in " + group_ids.toString() + ";";
        Cursor res =  db.rawQuery(query, null);
        res.moveToFirst();

        while(!res.isAfterLast()){
            NameRecord rec = new NameRecord();
            rec._id = res.getInt(res.getColumnIndex(NamesBaseContract.NameRecord._ID));
            rec.name = res.getString(res.getColumnIndex(NamesBaseContract.NameRecord.COLUMN_NAMES_NAME));
            rec.group_id = res.getInt(res.getColumnIndex(NamesBaseContract.NameRecord.COLUMN_NAMES_GROUP));
            array_list.add(rec);
            res.moveToNext();
        }
        res.close();
        return array_list;
    }
}
