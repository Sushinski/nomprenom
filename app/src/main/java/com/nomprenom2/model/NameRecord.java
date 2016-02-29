package com.nomprenom2.model;

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
