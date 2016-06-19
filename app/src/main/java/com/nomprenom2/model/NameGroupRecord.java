package com.nomprenom2.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


@Table(name = "NameGroupRecord", id = "_id")
public class NameGroupRecord extends Model {

    @Column(name = "group_id", notNull = true)
    public long group_id;

    @Column(name = "name_id", notNull = true)
    public long name_id;
}
