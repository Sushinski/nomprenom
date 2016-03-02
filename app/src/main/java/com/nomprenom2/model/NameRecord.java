package com.nomprenom2.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "names")
public class NameRecord extends Model{
    @Column(name = "name")
    public String name;

    @Column(name = "group") // todo: foreign key for the first time
    public GroupRecord group;
}
