package com.nomprenom2.model;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


@Table(name = "SearchRecord", id = "_id")
public class SearchRecord extends Model {
    @Column(name = "patronymic")
    public String patronymic;

    @Column(name = "sex")
    public int sex;

    public SearchRecord(String _patronymic, NameRecord.Sex _sex){
        this.patronymic = _patronymic;
        this.sex = _sex.getId();
    }
}
