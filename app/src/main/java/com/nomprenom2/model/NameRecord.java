package com.nomprenom2.model;

import android.app.DownloadManager;
import android.text.TextUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.nomprenom2.utils.Placeholder;

import java.util.ArrayList;
import java.util.List;

@Table(name = "NameRecord", id = "_id")
public class NameRecord extends Model{

    @Column(name = "name", unique = true, notNull = true)
    public String name;

    @Column(name = "sex", notNull = true)
    public int sex;

    @Column(name = "selected", notNull = true)
    public int selected;

    public enum Sex{
        Boy(1), Girl(0);
        private final int sex_id;
        Sex(int id){ this.sex_id = id; }
        public final int getId(){
            return sex_id;
        }
    }

    @Column(name = "from_group", onUpdate = Column.ForeignKeyAction.CASCADE,
            onDelete = Column.ForeignKeyAction.CASCADE)
    public GroupRecord from_group;

    public NameRecord(){
        super();
    }

    public NameRecord( String _name,  GroupRecord _group, Sex _sex ){
        super();
        this.name = _name;
        this.from_group = _group;
        this.sex = _sex.getId();
    }

    public static List<NameRecord> getNames(String[] groups, String s) {
        return new Select()
                .from(NameRecord.class)
                .innerJoin(GroupRecord.class)
                .on("NameRecord.from_group=GroupRecord._id")
                .where("GroupRecord.group_name IN (\'" +
                        TextUtils.join("\',\'", groups) +
                        "\') and NameRecord.sex=?", Sex.valueOf(s).getId())
                .execute();
    }

    public static List<NameRecord> getAll() {
        return new Select().all()
                .from(NameRecord.class)
                .orderBy("name ASC")
                .execute();
    }

    public static void setSelection(String[] names, int selection){
        new Update(NameRecord.class)
                .set("selected = " + String.valueOf(selection))
                .where("name in(\'" + TextUtils.join("\',\'", names) + "\')")
                .execute();
    }

    @Override
    public String toString(){
        return name;
    }

}
