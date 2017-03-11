package com.nomprenom2.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table(name = "GroupRecord", id="_id")
public class GroupRecord extends Model{
    public static final String LIST_FIELD = "group_name";

    @SerializedName("group_name")
    @Expose
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
        GroupRecord gr = null;
        try{
            gr =  new Select().
                    from(GroupRecord.class).
                    where("group_name = ? collate nocase", group_name).
                    executeSingle();
        }catch (Exception e){
            e.printStackTrace();
        }
        if( gr == null )
            return createGroup(group_name);
        return gr;
    }

    @Nullable
    public static GroupRecord createGroup(String group_name){
        try {
            GroupRecord gr = new GroupRecord();
            gr.group_name = group_name;
            gr.save();
            return gr;
        }catch (Exception e){
            return null;
        }
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


    public static GroupRecord getGroupForName(NameRecord nm){
        return new Select().from(GroupRecord.class).
                innerJoin(NameGroupRecord.class).on("GroupRecord._id=NameGroupRecord.group_id").
                where("NameGroupRecord.name_id=?", nm.getId()).executeSingle();
    }


    @Override
    public String toString(){
        return group_name;
    }

}
