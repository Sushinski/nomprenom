package com.nomprenom2.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "groups", id = "_id")
public class GroupRecord extends Model {
    @Column(name = "group_name", unique = true)
    public String group_name;

    public List<NameRecord> items() {
        return getMany(NameRecord.class, "groups");
    }

    public GroupRecord(){
        super();
    }
}
