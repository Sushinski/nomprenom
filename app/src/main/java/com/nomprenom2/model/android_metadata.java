package com.nomprenom2.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.activeandroid.util.SQLiteUtils;

import java.util.List;

@Table(name = "android_metadata", id="_id")
class android_metadata extends Model {
    @Column(name="locale")
    public String locale;

    public android_metadata(){
        super();
        locale = "en_US";
    }

    public android_metadata(String loc){
        super();
        locale = loc;
    }

    public static List<android_metadata> getAll(){
        return new Select().all().from(android_metadata.class).execute();
    }

    public static android_metadata getOne(){
        try {
            List<android_metadata> md =
                    SQLiteUtils.rawQuery(android_metadata.class, "SELECT * from android_metadata;", new String[]{});
            return md.get(0);
        }catch (Exception ex) {
            return  null;
        }
    }
}
