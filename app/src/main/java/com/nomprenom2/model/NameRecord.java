package com.nomprenom2.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "names", id = "_id")
public class NameRecord extends Model{
    @Column(name = "name", unique = true)
    public String name;

    @Column(name = "from_group", onUpdate = Column.ForeignKeyAction.CASCADE,
            onDelete = Column.ForeignKeyAction.CASCADE) // todo: foreign key for the first time
    public GroupRecord from_group;

    public NameRecord(){
        super();
    }

    public NameRecord( String _name, GroupRecord _group){
        super();
        this.name = _name;
        this.from_group = _group;
    }
}
