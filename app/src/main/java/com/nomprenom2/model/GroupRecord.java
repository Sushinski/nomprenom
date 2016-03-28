package com.nomprenom2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
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

    public static GroupRecord getOne(){
        return new Select().from(GroupRecord.class).executeSingle();
    }

    public static List<GroupRecord> groupForNames(String[] group_names){
        return new Select()
                .from(GroupRecord.class)
                .where("name in(?)", group_names)
                .execute();
    }

    @Override
    public String toString(){
        return group_name;
    }

}
