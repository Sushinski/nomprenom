package com.nomprenom2;


import android.provider.BaseColumns;

public final class NamesBaseContract {
    // to prevent instantinating
    public NamesBaseContract(){}

    /* Inner class that defines columns name */
    public static abstract class NameRecord implements BaseColumns {
        public static final String TABLE_NAME = "NameRecord";
        public static final String COLUMN_NAMES_NAME = "name";
        public static final String COLUMN_NAMES_GROUP = "group_id";
    }

    public static abstract class GroupRecord implements BaseColumns {
        public static final String TABLE_NAME = "GroupRecord";
        public static final String COLUMN_GROUP_NAME = "name";
    }
}
