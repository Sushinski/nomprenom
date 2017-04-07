/*
 * created by Pavel Golubev golubev.pavel.spb@gmail.com
 * no license applied
 * You may use this file without any restrictions
 */

package com.nomprenom2.model;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents region group for name
 */
@Table(name = "GroupRecord", id = "_id")
public class GroupRecord extends Model {

    @SerializedName("group_name")
    @Expose
    @Column(name = "group_name", unique = true)
    public String group_name;

    public GroupRecord() {
        super();
    }

    public GroupRecord(String _name) {
        super();
        group_name = _name;
    }

    /**
     * Gets all groups ordered by group name
     *
     * @return
     */
    public static List<GroupRecord> getAll() {
        return new Select().all()
                .from(GroupRecord.class)
                .orderBy("group_name ASC")
                .execute();
    }

    /**
     * Get certain group with name
     *
     * @param group_name Name of the group to get
     * @return
     */
    public static GroupRecord getGroup(String group_name) {
        GroupRecord gr = null;
        gr = new Select().
                from(GroupRecord.class). // uses case insensitive collation
                where("group_name = ? collate nocase", group_name).
                executeSingle();
        if (gr == null)
            return createGroup(group_name);
        return gr;
    }

    /**
     * Creates grouo with name
     *
     * @param group_name Name of the group to create
     * @return
     */
    @Nullable
    public static GroupRecord createGroup(String group_name) {
        GroupRecord gr = new GroupRecord();
        gr.group_name = group_name;
        gr.save();
        return gr;
    }

    /**
     * Get groups id for names
     *
     * @param group_names Array of names to get group ids for
     * @return
     */
    public static List<Long> groupIdForNames(String[] group_names) {
        String gr_names = "\'" + TextUtils.join("\',\'", group_names) + "\'";
        From q = new Select("_id")
                .from(GroupRecord.class)
                .where("group_name IN(" + gr_names + ")");
        List<GroupRecord> group_list = q.execute();
        List<Long> group_id_list = new ArrayList<>(group_list.size());
        for (GroupRecord g : group_list) {
            group_id_list.add(g.getId());
        }
        return group_id_list;
    }

    /**
     * Gets group model object for name model object
     *
     * @param nm - name model object to get group for
     * @return
     */
    public static GroupRecord getGroupForName(NameRecord nm) {
        return new Select().from(GroupRecord.class).
                innerJoin(NameGroupRecord.class).on("GroupRecord._id=NameGroupRecord.group_id").
                where("NameGroupRecord.name_id=?", nm.getId()).executeSingle();
    }

    /**
     * Represents group model object as string
     *
     * @return
     */
    @Override
    public String toString() {
        return group_name;
    }

}
