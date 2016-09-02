package com.nomprenom2.model;

import android.text.TextUtils;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nomprenom2.utils.RestInteractionWorker;

import java.util.ArrayList;
import java.util.List;


@Table(name = "NameRecord", id = "_id")
public class NameRecord extends Model{

    @SerializedName("_id")
    @Expose
    public int _id;

    @SerializedName("name")
    @Expose
    @Column(name = "name", unique = true, notNull = true)
    public String name;

    @SerializedName("sex")
    @Expose
    @Column(name = "sex", notNull = true)
    public int sex;

    @Column(name = "selected", notNull = true)
    public int selected;

    @SerializedName("description")
    @Expose
    @Column(name = "description")
    public String description;

    @SerializedName("groups")
    @Expose
    public List<GroupRecord> groups = new ArrayList<>();

    @SerializedName("zodiacs")
    @Expose
    public List<ZodiacRecord> zodiacs = new ArrayList<>();

    public enum Sex{
        Girl(0), Boy(1), ;
        private final int sex_id;
        Sex( int id ){ this.sex_id = id; }
        public final int getId(){
            return sex_id;
        }
        static final Sex[] vals = Sex.values();
        public static String fromInt(int val){ return vals[val].toString(); }
    }

    public enum Check{
        Unchecked( 0 ), Checked( 1 );
        private final int check_id;
        Check( int id ){ this.check_id = id; }
        public final int getId(){
            return check_id;
        }
    }


    public NameRecord(){
        super();
    }

    public static void refreshNamesCache(){
        RestInteractionWorker worker = new RestInteractionWorker();
        worker.perfomServerOperation("0", "2", "all");
    }

    public static List<NameRecord> getNames(String[] groups, String sex, String zod) {

        String _where = "", add = "";
        if( groups != null) {
            _where = "NameRecord._id in (select name_id from NameGroupRecord where group_id in ";
            List<Long> group_id_list = GroupRecord.groupIdForNames(groups);
            String str  = "(" + TextUtils.join(",", group_id_list) + "))";
            _where += str;
            add = " and ";
        }
        if( sex != null ) {
            _where += add + "NameRecord.sex=" + Sex.valueOf(sex).getId();
            add = " and ";
        }
        From sel = new Select()
                .from(NameRecord.class);
        if( zod != null ) {
            _where += add + "NameRecord._id in (select name_id from NameZodiacRecord a inner join " +
                    " ZodiacRecord b on a.zodiac_id = b._id where" +
                    " b.zod_month=" + ZodiacRecord.ZodMonth.valueOf(zod).getId() + ")";
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
        try {
            rec.name = name;
            rec.sex = Sex.valueOf(sex).getId();
            rec.description = descr;
            rec.save();
        }catch(Exception e){
            e.printStackTrace();
            return;
        }

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
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
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
