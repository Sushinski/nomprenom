package com.nomprenom2.model;

/**
 * Created by android on 29.02.16.
 */
public class NameRecord {

    public NameRecord(){}

    public NameRecord(int _id, String name, int group_id) {
        this._id = _id;
        this.name = name;
        this.group_id = group_id;
    }

    public int _id;
    public String name;
    public int group_id;
}
