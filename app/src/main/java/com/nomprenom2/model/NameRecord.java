/*
 * created by Pavel Golubev golubev.pavel.spb@gmail.com
 * no license applied
 * You may use this file without any restrictions
 */

package com.nomprenom2.model;

import android.text.TextUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nomprenom2.utils.RestInteractionWorker;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents name itself
 */
@Table(name = "NameRecord", id = "_id")
public class NameRecord extends Model {

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

    /**
     * Name`s gender enum
     */
    public enum Sex {
        Girl(0), Boy(1),;
        private final int sex_id;

        Sex(int id) {
            this.sex_id = id;
        }

        public final int getId() {
            return sex_id;
        }

        static final Sex[] vals = Sex.values();

        public static String fromInt(int val) {
            return vals[val].toString();
        }
    }

    /**
     * Name`s selection enum
     */
    public enum Check {
        Unchecked(0), Checked(1);
        private final int check_id;

        Check(int id) {
            this.check_id = id;
        }

        public final int getId() {
            return check_id;
        }
    }

    public NameRecord() {
        super();
    }

    /**
     * Gets Name model object for certain name
     *
     * @param name Name to get object for
     * @return
     */
    public static NameRecord get(String name) {
        return new Select().from(NameRecord.class).where("name = ?", name).executeSingle();
    }

    /**
     * Creates Name model object and save it to database
     *
     * @param name Name to create database record for
     * @return
     */
    private static NameRecord create(String name) {
        NameRecord rec = new NameRecord();
        rec.name = name;
        rec.save();
        return rec;
    }

    /**
     * Refreshes local database with new names from rest service.
     * Uses Retrofit library to process request an responce
     *
     * @param base_addr
     * @param last_version
     */
    public static void refreshNamesCache(String base_addr, String last_version) {
        RestInteractionWorker worker = new RestInteractionWorker(base_addr);
        worker.getNamesUpdate(last_version);
    }

    /**
     * Gets name model objects list for parameters
     *
     * @param groups Array of group names to get names for
     * @param sex    Gender of names to get
     * @param zod    Zodiac (month as integer) to get names for
     * @return List of name model objects
     */
    public static List<NameRecord> getNames(String[] groups, int sex, int zod) {
        String _where = "", add = "";
        if (groups != null) { // collect group id`s for filtering if presented
            _where = "NameRecord._id in (select name_id from NameGroupRecord where group_id in ";
            List<Long> group_id_list = GroupRecord.groupIdForNames(groups);
            String str = "(" + TextUtils.join(",", group_id_list) + "))";
            _where += str;
            add = " and ";
        }
        if (sex > 0) { // filter gender id if presented
            _where += add + "NameRecord.sex=" + Integer.toString(sex - 1);
            add = " and ";
        }
        if (zod > 0) { // filter zodiacs month if presented
            _where += add + "NameRecord._id in (select name_id from NameZodiacRecord a inner join " +
                    " ZodiacRecord b on a.zodiac_id = b._id where" +
                    " b.zod_month=" + Integer.toString(zod) + ")";
        }
        if (!_where.equals("")) // apply select filted if it holds some
            return new Select()
                    .from(NameRecord.class)
                    .where(_where)
                    .orderBy("NameRecord.name ASC")
                    .execute();
        else
            return new Select().all() // else return all records
                    .from(NameRecord.class)
                    .orderBy("NameRecord.name ASC")
                    .execute();
    }

    /**
     * Gets last inserted name record
     *
     * @return Name model object
     */
    public static NameRecord getLast() {
        return new Select()
                .from(NameRecord.class)
                .orderBy("id DESC")
                .executeSingle();
    }

    /**
     * Sets/zeroes selection field for certain name
     *
     * @param name      name to process selection field for
     * @param selection 1 sets, 0 - resets selection
     */
    public static void setSelection(String name, int selection) {
        new Update(NameRecord.class)
                .set("selected = ?", String.valueOf(selection))
                .where("name = ?", name)
                .execute();
    }

    /**
     * Gets list of selected/deselected name records
     *
     * @param selected 1 for get selected, 0 - deselected
     * @return List of name model objects with certain criteria
     */
    public static List<NameRecord> getSelected(int selected) {
        return new Select()
                .from(NameRecord.class)
                .where("selected=?", selected)
                .orderBy("name ASC")
                .execute();
    }

    /**
     * Creates Name model object for certain name with parameters and save it to database
     *
     * @param name    Name to save
     * @param sex     Name`s gender
     * @param zodiacs List of name`s zodiacs
     * @param groups  List of name`s region groups
     * @param descr   Name`s description
     * @return Name record id in database
     */
    public static long saveName(String name, Integer sex,
                                List<Integer> zodiacs, List<String> groups, String descr) {
        boolean created = false;
        NameRecord rec = get(name);
        if (rec == null) {
            if ((rec = create(name)) == null)
                return -1;
            created = true;
        }
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
        if (created)
            return rec.getId();
        else
            return -1;
    }

    /**
     * Gets string name representation
     *
     * @return Name as String
     */
    @Override
    public String toString() {
        return name;
    }

}
