package com.nomprenom2.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "NameGroupRecord", id = "_id")
public class NameZodiacRecord extends Model {

    @Column(name = "zodiac_id", notNull = true)
    public long zodiac_id;

    @Column(name = "name_id", notNull = true)
    public long name_id;
}
