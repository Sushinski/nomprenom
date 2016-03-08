package com.nomprenom2.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "GroupRecord", id = "_id")
public class GroupRecord extends Model {
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

    @Override
    public String toString(){
        return group_name;
    }
}
