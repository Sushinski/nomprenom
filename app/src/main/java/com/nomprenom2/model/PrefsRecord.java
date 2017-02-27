package com.nomprenom2.model;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;


@Table(name = "PrefsRecord", id = "_id")
public class PrefsRecord extends Model{
    public static final String LAST_UPD_NAME_ID = "last_upd_name_id";

    @Column(name = "name", unique = true, notNull = true)
    public String name;

    @Column(name="value")
    public String value;

    public static Integer getIntValue(String name){
        PrefsRecord rec = getRecord(name);
        if(rec != null)
            return Integer.getInteger(rec.value);
        else
            return null;
    }

    public static String getStringValue(String name){
        PrefsRecord rec = getRecord(name);
        if(rec != null)
            return rec.value;
        else
            return null;
    }

    public static void saveStringValue(String name, String value){
        PrefsRecord rec = getRecord(name);
        if( rec == null ){
            rec = new PrefsRecord();
            rec.name = name;
        }
        rec.value = value;
        try {
            rec.save();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static PrefsRecord getRecord(String name) {
        try {
            return new Select().
                    from(PrefsRecord.class).where("name=?", name).executeSingle();
        } catch (Exception e) {
            return null;
        }
    }
}
