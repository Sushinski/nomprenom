/*
 * created by Pavel Golubev golubev.pavel.spb@gmail.com
 * no license applied
 * You may use this file without any restrictions
 */
package com.nomprenom2.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * Represents preferencies database table
 */
@Table(name = "PrefsRecord", id = "_id")
public class PrefsRecord extends Model {
    /**
     * Last updated name id request field key
     */
    public static final String LAST_UPD_NAME_ID = "last_upd_name_id";
    /**
     * Rest service server ip address request field key
     */
    public static final String BASE_SERV_IP_ADDR = "serv_ip";

    @Column(name = "name", unique = true, notNull = true)
    public String name;

    @Column(name = "value")
    public String value;

    /**
     * Gets integer preference for key with name
     *
     * @param name Preference name
     * @return Preference integer value
     */
    public static Integer getIntValue(String name) {
        PrefsRecord rec = getRecord(name);
        if (rec != null)
            return Integer.getInteger(rec.value);
        else
            return null;
    }

    /**
     * Gets string preference for key with name
     *
     * @param name Preference name
     * @return Preference string value
     */
    public static String getStringValue(String name) {
        PrefsRecord rec = getRecord(name);
        if (rec != null)
            return rec.value;
        else
            return null;
    }

    /**
     * Saves string preference value with name
     *
     * @param name  Name of the preference to save
     * @param value Value of the preference to save
     */
    public static void saveStringValue(String name, String value) {
        PrefsRecord rec = getRecord(name);
        if (rec == null) {
            rec = new PrefsRecord();
            rec.name = name;
        }
        rec.value = value;
        rec.save();
    }

    /**
     * Gets preference model object for name
     *
     * @param name Name of the preference to get
     * @return Preference model object
     */
    private static PrefsRecord getRecord(String name) {
        try {
            return new Select().
                    from(PrefsRecord.class).where("name=?", name).executeSingle();
        } catch (Exception e) {
            return null;
        }
    }
}
