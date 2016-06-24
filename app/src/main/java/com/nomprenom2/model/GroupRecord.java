package com.nomprenom2.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table(name = "GroupRecord", id="_id")
public class GroupRecord extends Model{
    public static final String LIST_FIELD = "group_name";

    @Column(name = "group_name", unique = true)
    public String group_name;

    public GroupRecord(){
        super();
    }

    public GroupRecord(String _name){
        super();
        group_name = _name;
    }

    public static List<GroupRecord> getAll() {
        return new Select().all()
                .from(GroupRecord.class)
                .orderBy("group_name ASC")
                .execute();
    }

    public static GroupRecord getGroup(String group_name){
        return new Select().
                from(GroupRecord.class).
                where("group_name = ?", group_name).
                executeSingle();
    }

    public static List<Long> groupIdForNames(String[] group_names){
        String gr_names = "\'" + TextUtils.join("\',\'", group_names) + "\'";
        From q = new Select("_id")
                .from(GroupRecord.class)
                .where("group_name IN(" + gr_names + ")");
        List<GroupRecord> group_list = q.execute();
        List<Long> group_id_list = new ArrayList<>(group_list.size());
        for (GroupRecord g: group_list) {
                group_id_list.add(g.getId());
            }
        return group_id_list;
    }

    @Override
    public String toString(){
        return group_name;
    }

}
