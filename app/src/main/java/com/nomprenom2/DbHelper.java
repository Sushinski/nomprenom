package com.nomprenom2;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.jar.Attributes;

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
                    NamesBaseContract.GroupRecord.TABLE_NAME + " (" +
                    NamesBaseContract.GroupRecord._ID + " INTEGER PRIMARY KEY," +
                    NamesBaseContract.GroupRecord.COLUMN_GROUP_NAME + TEXT_TYPE + COMMA_SEP +
                    NamesBaseContract.GroupRecord.COLUMN_GROUP_REGION + TEXT_TYPE +  " );";
    private static final String SQL_CREATE_NAMES_TBL =
                    "CREATE TABLE " +
                    NamesBaseContract.NameRecord.TABLE_NAME + " (" +
                    NamesBaseContract.NameRecord._ID + " INTEGER PRIMARY KEY," +
                    NamesBaseContract.NameRecord.COLUMN_NAMES_NAME + TEXT_TYPE + COMMA_SEP +
                    NamesBaseContract.NameRecord.COLUMN_NAMES_GROUP + INT_TYPE + FOREIGN_KEY_TYPE +
                    NamesBaseContract.GroupRecord.TABLE_NAME + "(" +
                            NamesBaseContract.NameRecord._ID + "));";

    private static final String SQL_DELETE_TABLES =
            "DROP TABLE IF EXISTS " + NamesBaseContract.GroupRecord.TABLE_NAME + "; " +
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
}
