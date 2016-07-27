package com.nomprenom2.model;

import android.app.DownloadManager;
import android.database.Cursor;
import android.text.TextUtils;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

import java.util.ArrayList;
import java.util.List;

@Table(name = "NameRecord", id = "_id")
public class NameRecord extends Model{

    @Column(name = "name", unique = true, notNull = true)
    public String name;

    @Column(name = "sex", notNull = true)
    public int sex;

    @Column(name = "selected", notNull = true)
    public int selected;

    @Column(name = "description")
    public String description;

    public enum Sex{
        Boy( 1 ), Girl( 0 );
        private final int sex_id;
        Sex( int id ){ this.sex_id = id; }
        public final int getId(){
            return sex_id;
        }
        static final Sex[] vals = Sex.values();
        public static String fromInt(int val){ return vals[val].toString(); }
    }

    public enum Check{
        Checked( 1 ), Unchecked( 0 );
        private final int check_id;
        Check( int id ){ this.check_id = id; }
        public final int getId(){
            return check_id;
        }
    }


    public NameRecord(){
        super();
    }


    public static List<NameRecord> getNames(String[] groups, String s, String z) {
        String _where = "", add = "";

        if( groups != null) {
            _where = "NameRecord._id in (select name_id from NameGroupRecord where group_id in ";
            List<Long> group_id_list = GroupRecord.groupIdForNames(groups);
            String str  = "(" + TextUtils.join(",", group_id_list) + "))";
            _where += str;
            add = " and ";
        }
        if( s != null ) {
            _where += add + "NameRecord.sex=" + Sex.valueOf(s).getId();
            add = " and ";
        }
        From sel = new Select("_id, name, sex, selected")
                .from(NameRecord.class);
        if( z != null ) {
            _where += add + "NameRecord._id in (select name_id from NameZodiacRecord a inner join " +
                    " ZodiacRecord b on a.zodiac_id = b._id where" +
                    " b.zod_month=" + ZodiacRecord.ZodMonth.valueOf(z).getId() + ")";
        }
        sel.where(_where).orderBy("NameRecord.name ASC");
        try {
            return sel.execute();
        }catch (Exception e){
            return new ArrayList<>();
        }

    }

    public static List<NameRecord> getAll() {
        return new Select().all()
                .from(NameRecord.class)
                .orderBy("name ASC")
                .execute();
    }

    public static void setSelection(String name, int selection){
        new Update(NameRecord.class)
                .set("selected = ?",  String.valueOf(selection))
                .where("name = ?", name )
                .execute();
    }

    public static List<NameRecord> getSelected(int selected){
        return new Select()
                .from(NameRecord.class)
                .where("selected=?", selected)
                .orderBy("name ASC")
                .execute();
    }


    public static void saveName(String name, String sex,
                                List<String> zodiacs, List<String> groups, String descr ){
        NameRecord rec = new NameRecord();
        rec.name = name;
        rec.sex = Sex.valueOf(sex).getId();
        rec.description = descr;
        rec.save();
        ActiveAndroid.beginTransaction();
        try {
            for (String z : zodiacs) {
                NameZodiacRecord nzr = new NameZodiacRecord();
                nzr.name_id = rec.getId();
                ZodiacRecord.ZodMonth m = ZodiacRecord.ZodMonth.valueOf(z);
                nzr.zodiac_id = ZodiacRecord.getZodiacRec(m).getId();
                nzr.save();
            }

            for (String g : groups) {
                NameGroupRecord ngr = new NameGroupRecord();
                ngr.name_id = rec.getId();
                ngr.group_id = GroupRecord.getGroup(g).getId();
                ngr.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        }finally {
            ActiveAndroid.endTransaction();
        }
    }


    public static String getNameDescr(String name){
        NameRecord nr =  new Select()
                .from(NameRecord.class)
                .where("name=?", name)
                .orderBy("name ASC")
                .executeSingle();
        return nr.description;
    }

    @Override
    public String toString(){
        return name;
    }

}
