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
    public String group;

    @SerializedName("zodiacs")
    @Expose
    public List<Integer> zodiacs = new ArrayList<>();

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

    public static NameRecord get(String name){
        try {
            return new Select().from(NameRecord.class).where("name = ?", name).executeSingle();
        }
        catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }

    private static NameRecord create(String name){
        try{
            NameRecord rec = new NameRecord();
            rec.name = name;
            rec.save();
            return rec;
        }
        catch(Exception e){
            return null;
        }
    }

    public static void refreshNamesCache( String base_addr, String last_version){
        RestInteractionWorker worker = new RestInteractionWorker(base_addr);
        worker.getNamesUpdate(last_version);
    }

    public static List<NameRecord> getNames(String[] groups, int sex, int zod) {

        String _where = "", add = "";
        if( groups != null) {
            _where = "NameRecord._id in (select name_id from NameGroupRecord where group_id in ";
            List<Long> group_id_list = GroupRecord.groupIdForNames(groups);
            String str  = "(" + TextUtils.join(",", group_id_list) + "))";
            _where += str;
            add = " and ";
        }
        if( sex != -1 ) {
            _where += add + "NameRecord.sex=" + sex;
            add = " and ";
        }
        if( zod != -1 ) {
            _where += add + "NameRecord._id in (select name_id from NameZodiacRecord a inner join " +
                    " ZodiacRecord b on a.zodiac_id = b._id where" +
                    " b.zod_month=" + Integer.toString(zod) + ")";
        }
        try {
            if(!_where.equals(""))
                return new Select()
                        .from(NameRecord.class)
                        .where(_where)
                        .orderBy("NameRecord.name ASC")
                        .execute();
            else
                return  new Select().all().from(NameRecord.class).execute();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static NameRecord getLast() {
        return new Select()
                .from(NameRecord.class)
                .orderBy("id DESC")
                .executeSingle();
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


    public static long saveName(String name, Integer sex,
                                List<Integer> zodiacs, List<String> groups, String descr ){
        boolean created = false;
        NameRecord rec = get(name);
        if( rec == null ){
            if( (rec = create(name)) == null)
                return -1;
            created = true;
        }
        try {
            rec.sex = sex;
            rec.description = descr;
            rec.save();
            for (Integer z : zodiacs) {
                NameZodiacRecord nzr = new NameZodiacRecord();
                nzr.name_id = rec.getId();
                nzr.zodiac_id = ZodiacRecord.getZodiacRec(z).getId();
                nzr.save();
            }

            for (String g : groups) {
                NameGroupRecord ngr = new NameGroupRecord();
                ngr.name_id = rec.getId();
                ngr.group_id = GroupRecord.getGroup(g).getId();
                ngr.save();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if( created )
            return rec.getId();
        else
            return -1;
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
