package com.nomprenom2.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

@Table(name = "NameRecord", id = "_id")
public class NameRecord extends Model{
    @Column(name = "name", unique = true)
    public String name;

    @Column(name = "from_group", onUpdate = Column.ForeignKeyAction.CASCADE,
            onDelete = Column.ForeignKeyAction.CASCADE)
    public GroupRecord from_group;

    public NameRecord(){
        super();
    }

    public NameRecord( String _name, GroupRecord _group){
        super();
        this.name = _name;
        this.from_group = _group;
    }

    public static List<NameRecord> getAll(ArrayList<Integer> group_ids) {
        return new Select()
                .from(NameRecord.class)
                .where("from_group in(?)", group_ids.toArray())
                .orderBy("name ASC")
                .execute();
    }

    public static List<NameRecord> getAll() {
        return new Select().all()
                .from(NameRecord.class)
                .orderBy("name ASC")
                .execute();
    }

    @Override
    public String toString(){
        return name;
    }

}
